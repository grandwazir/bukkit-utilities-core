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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.xml.sax.SAXException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localised;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.logging.Logger;

public class PluginUpdater implements Listener, Runnable, Localised {

	public enum Branch {
		DEVELOPMENT, STABLE
	}
	public enum State {
		NOTIFY, OFF
	}

	private final String artifactId;
	private final String groupId;
	private static final ResourceBundle localisation = ResourceBundle.getBundle(ResourceBundles.UTILITIES.getBundleName());
	private final Logger logger = new Logger(this, ResourceBundles.UTILITIES);
	/* A reference to the downloaded Maven manifest from the remote repository */
	private MavenManifest manifest;
	private final Permission permission;
	private final String pluginName;
	private final URL repositoryURL;
	private final String version;
	private String newVersionNotification;

	public PluginUpdater(final Updatable plugin) {
		this.permission = Bukkit.getPluginManager().getPermission(plugin.getName().toLowerCase());
		this.version = plugin.getVersion();
		this.artifactId = plugin.getArtifactID();
		this.groupId = plugin.getGroupID();
		this.repositoryURL = plugin.getRepositoryURL();
		this.pluginName = plugin.getName();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if (player.hasPermission(permission)) {
			final String message = this.getMessage("updater.new-version-available", this.pluginName,
					this.manifest.getCurrentVersion());
			player.sendMessage(message);
		}
	}

	public void run() {
		try {
			this.parseMavenMetaData();
			if (this.isNewVersionAvailable()) {
				this.newVersionNotification = this.getMessage("updater.new-version-available", this.pluginName,
						this.manifest.getCurrentVersion());
				logger.log(Level.INFO, this.newVersionNotification);
			}
		} catch (final IOException e) {
			logger.log(Level.WARNING, "updater.unable-to-read-metadata", this.repositoryURL.toString());
		} catch (final SAXException e) {
			logger.log(Level.WARNING, "updater.unable-to-read-metadata", this.repositoryURL.toString());
		} catch (final ParserConfigurationException e) {
			logger.log(Level.WARNING, "updater.unable-to-read-metadata", this.repositoryURL.toString());
		}
	}

	private void getMavenMetaData(final File storage) throws IOException {
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
			logger.log(Level.FINER, "Getting manifest: {0}", url.toString());
			rbc = Channels.newChannel(url.openStream());
			fos = new FileOutputStream(storage);
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		} finally {
			if (rbc != null) rbc.close();
			if (fos != null) fos.close();
		}
	}

	private boolean isNewVersionAvailable() {
		final DefaultArtifactVersion current = new DefaultArtifactVersion(this.version);
		final DefaultArtifactVersion target = new DefaultArtifactVersion(this.manifest.getCurrentVersion());
		Object params[] = {target.toString(), current.toString()};
		if (current.compareTo(target) == -1) {
			logger.log(Level.FINE, "New version available: {0} > {1}", params);
			return true;
		} else {
			logger.log(Level.FINE, "New version unavailable: {0} <= {1}", params);
			return false;
		}
	}

	private void parseMavenMetaData() throws IOException, SAXException, ParserConfigurationException {
		final File temp = File.createTempFile(this.artifactId, null);
		logger.log(Level.FINER, "Creating temporary manifest: {0}", temp.getAbsolutePath());
		this.getMavenMetaData(temp);
		this.manifest = new MavenManifest(temp);
	}

	public String getMessage(String key) {
    String message = localisation.getString(key);
    message = ColourFormatter.replace(message);
    return message;
	}

	public String getMessage(String key, Object... elements) {
    MessageFormat formatter = new MessageFormat(localisation.getString(key));
    formatter.setLocale(Locale.getDefault());
    String message = formatter.format(elements);
    message = ColourFormatter.replace(message);
    return message;
	}
	
}
