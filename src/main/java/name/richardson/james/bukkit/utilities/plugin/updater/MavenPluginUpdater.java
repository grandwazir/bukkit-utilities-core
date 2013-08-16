/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 MavenPluginUpdater.java is part of BukkitUtilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.plugin.updater;

import name.richardson.james.bukkit.utilities.logging.AbstractPrefixedLogger;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;

/**
 * The MavenPluginUpdater implementation of {@link PluginUpdater} checks the maven repository attached to a plugin to
 * see if there is an update available. It is responsible for retrieving the manifest,
 * deciding if a new version exists and if it does sets up a {@link PlayerNotifier} to tell players about it.
 */
public class MavenPluginUpdater extends AbstractPluginUpdater {

	private static final Logger logger = AbstractPrefixedLogger.getLogger(MavenPluginUpdater.class);
	private final String artifactId;
	private final String groupId;
	private MavenManifest manifest;
	private URL repositoryURL;

	public MavenPluginUpdater(String artifactId, String groupId, PluginDescriptionFile pluginDescriptionFile, final PluginUpdater.Branch branch, final PluginUpdater.State state) {
		super(pluginDescriptionFile, branch, state);
		this.artifactId = artifactId;
		this.groupId = groupId;
		try {
			switch (getBranch()) {
				case DEVELOPMENT:
					this.repositoryURL = new URL("http://repository.james.richardson.name/snapshots");
				default:
					this.repositoryURL = new URL("http://repository.james.richardson.name/releases");
			}
		} catch (final MalformedURLException e) {
			throw new IllegalStateException("Repository URL could not be set!");
		}
	}

	@Override
	public String getRemoteVersion() {
		return this.manifest.getCurrentVersion();
	}

	@Override
	public boolean isNewVersionAvailable() {
		if (this.manifest != null) {
			final DefaultArtifactVersion current = new DefaultArtifactVersion(getLocalVersion());
			final DefaultArtifactVersion target = new DefaultArtifactVersion(getRemoteVersion());
			final Object params[] = {target.toString(), current.toString()};
			if (current.compareTo(target) == -1) {
				this.logger.log(Level.FINE, "New version available: {0} > {1}", params);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void run() {
		if (this.getState() == State.UPDATE) {
			this.logger.log(Level.WARNING, "policy-restriction");
		} else {
			try {
				this.parseMavenMetaData();
				if (this.isNewVersionAvailable()) {
					Object[] arguments = {getName(), this.getRemoteVersion()};
					this.logger.log(Level.INFO, "new-version-available", arguments);
				} else {
					Object[] arguments = {this.getLocalVersion(), this.getRemoteVersion()};
					this.logger.log(Level.FINE, "New version unavailable: {0} <= {1}", arguments);
				}
			} catch (final IOException e) {
				this.logger.log(Level.WARNING, "unable-to-read-metadata", this.repositoryURL.toString());
			} catch (final SAXException e) {
				this.logger.log(Level.WARNING, "unable-to-read-metadata", this.repositoryURL.toString());
			} catch (final ParserConfigurationException e) {
				this.logger.log(Level.WARNING, "unable-to-read-metadata", this.repositoryURL.toString());
			}
		}
	}

	/**
	 * Builds a URL to the manifest file and then attempts to download it to the File supplied,
	 *
	 * @param storage The location to save the manifest to
	 * @throws IOException
	 */
	private void getMavenMetaData(final File storage)
	throws IOException {
		final StringBuilder path = new StringBuilder();
		path.append(this.repositoryURL);
		path.append("/");
		path.append(this.groupId.replace(".", "/"));
		path.append("/");
		path.append(this.artifactId);
		path.append("/maven-metadata.xml");
		final URL url = new URL(path.toString());
		// get the file
		ReadableByteChannel rbc = null;
		FileOutputStream fos = null;
		try {
			this.logger.log(Level.FINER, "Getting manifest: {0}", url.toString());
			rbc = Channels.newChannel(url.openStream());
			fos = new FileOutputStream(storage);
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		} finally {
			if (rbc != null) {
				rbc.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	/**
	 * Parse the downloaded file into a MavenManifest.
	 *
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private void parseMavenMetaData()
	throws IOException, SAXException, ParserConfigurationException {
		final File temp = File.createTempFile(this.artifactId, null);
		this.logger.log(Level.FINER, "Creating temporary manifest: {0}", temp.getAbsolutePath());
		this.getMavenMetaData(temp);
		this.manifest = new MavenManifest(temp);
	}

	private class MavenManifest {

		private final List<String> versionList = new LinkedList<String>();

		public MavenManifest(final File file) throws SAXException, IOException, ParserConfigurationException {
			final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			final Document doc = docBuilder.parse(file);
			// normalize text representation
			doc.getDocumentElement().normalize();
			final NodeList root = doc.getChildNodes();
			// navigate down to get the versioning node
			final Node meta = this.getNode("metadata", root);
			final Node versioning = this.getNode("versioning", meta.getChildNodes());
			final Node versions = this.getNode("versions", versioning.getChildNodes());
			// get a list of versions
			final NodeList nodes = versions.getChildNodes();
			this.setVersionList(nodes);
			file.deleteOnExit();
		}

		public String getCurrentVersion() {
			return this.versionList.get(0);
		}

		private void setVersionList(final NodeList nodes) {
			this.versionList.clear();
			int i = 0;
			while (i <= (nodes.getLength() - 1)) {
				final Node node = nodes.item(i);
				if (node.getNodeName().equalsIgnoreCase("version")) {
					this.versionList.add(0, node.getChildNodes().item(0).getNodeValue());
				}
				i++;
			}
		}

		private Node getNode(final String tagName, final NodeList nodes) {
			for (int x = 0; x < nodes.getLength(); x++) {
				final Node node = nodes.item(x);
				if (node.getNodeName().equalsIgnoreCase(tagName)) {
					return node;
				}
			}
			return null;
		}
	}

}