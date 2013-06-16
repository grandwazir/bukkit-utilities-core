/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 *
 * PluginUpdater.java is part of BukkitUtilities.
 *
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.updater;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.richardson.james.bukkit.utilities.logging.PluginLogger;

public class PluginUpdater implements Runnable {

	public enum Branch {
		DEVELOPMENT, STABLE
	}

	public enum State {
		NOTIFY, OFF
	}

	private final String artifactId;
	private final String groupId;
	private final Logger logger = PluginLogger.getLogger(PluginUpdater.class);
	private final String pluginName;
	private final URL repositoryURL;
	private final String version;
	/* A reference to the downloaded Maven manifest from the remote repository */
	private MavenManifest manifest;

	public PluginUpdater(final Updatable plugin) {
		this.version = plugin.getVersion();
		this.artifactId = plugin.getArtifactID();
		this.groupId = plugin.getGroupID();
		this.repositoryURL = plugin.getRepositoryURL();
		this.pluginName = plugin.getName();
	}

	private String getLocalVersion() {
		return this.version;
	}

	private String getRemoteVersion() {
		return this.manifest.getCurrentVersion();
	}

	public void run() {
		try {
			this.parseMavenMetaData();
			if (this.isNewVersionAvailable()) {
				Object[] arguments =  {this.pluginName, this.getRemoteVersion()};
				this.logger.log(Level.INFO, "notice.updater.new-version-available", arguments);
				new PlayerNotifier(this.pluginName, this.version);
			} else {
				Object[] arguments =  {this.pluginName, this.getRemoteVersion()};
				this.logger.log(Level.FINE, "New version unavailable: {0} <= {1}", arguments);
			}
		} catch (final IOException e) {
			this.logger.log(Level.WARNING, "warning.updater.unable-to-read-metadata", this.repositoryURL.toString());
		} catch (final SAXException e) {
			this.logger.log(Level.WARNING, "warning.updater.unable-to-read-metadata", this.repositoryURL.toString());
		} catch (final ParserConfigurationException e) {
			this.logger.log(Level.WARNING, "warning.updater.unable-to-read-metadata", this.repositoryURL.toString());
		}
	}

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

	private boolean isNewVersionAvailable() {
		if (this.manifest != null) {
			final DefaultArtifactVersion current = new DefaultArtifactVersion(this.getLocalVersion());
			final DefaultArtifactVersion target = new DefaultArtifactVersion(this.getRemoteVersion());
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

	private void parseMavenMetaData()
	throws IOException, SAXException, ParserConfigurationException {
		final File temp = File.createTempFile(this.artifactId, null);
		this.logger.log(Level.FINER, "Creating temporary manifest: {0}", temp.getAbsolutePath());
		this.getMavenMetaData(temp);
		this.manifest = new MavenManifest(temp);
	}

}