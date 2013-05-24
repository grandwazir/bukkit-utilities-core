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

import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.xml.sax.SAXException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.logging.LocalisedLogger;
import name.richardson.james.bukkit.utilities.logging.LocalisedLogger;
import name.richardson.james.bukkit.utilities.permissions.BukkitPermissionManager;

public class PluginUpdater implements Listener, Runnable {

	public enum Branch {
		DEVELOPMENT, STABLE
	}
	public enum State {
		NOTIFY, OFF
	}

	private final String artifactId;
	private final String groupId;
	private final Localisation localisation;
	private final LocalisedLogger logger = new LocalisedLogger(this.getClass().getName());
	/* A reference to the downloaded Maven manifest from the remote repository */
	private MavenManifest manifest;
	private final Permission permission;
	private final BukkitPermissionManager permissions = new BukkitPermissionManager();
	private final String pluginName;
	private final URL repositoryURL;
	private final String version;

	public PluginUpdater(final Updatable plugin, final Localisation localisation) {
		this.permission = this.permissions.getPermission(plugin.getName().toLowerCase());
		this.version = plugin.getVersion();
		this.artifactId = plugin.getArtifactID();
		this.groupId = plugin.getGroupID();
		this.repositoryURL = plugin.getRepositoryURL();
		this.localisation = localisation;
		this.pluginName = plugin.getName();
		this.logger.setPrefix("[" + pluginName + "] ");
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		final boolean permitted = this.permissions.hasPermission(player, this.permission);
		if (permitted) {
			final String message = this.localisation.getMessage(this, "new-version-available", this.pluginName,
					this.manifest.getCurrentVersion());
			player.sendMessage(message);
		}
	}

	public void run() {
		try {
			this.parseMavenMetaData();
			if (this.isNewVersionAvailable()) {
				this.logger.info(this.localisation.getMessage(this, "new-version-available", this.pluginName,
						this.manifest.getCurrentVersion()));
			}
		} catch (final IOException e) {
			this.logger.warning(this.localisation.getMessage(this, "unable-to-read-metadata", this.artifactId));
		} catch (final SAXException e) {
			this.logger.warning(this.localisation.getMessage(this, "unable-to-read-metadata", this.artifactId));
		} catch (final ParserConfigurationException e) {
			this.logger.warning(this.localisation.getMessage(this, "unable-to-read-metadata", this.artifactId));
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
			this.logger.debug(this.localisation.getMessage(this, "fetching-resource", url.toString()));
			rbc = Channels.newChannel(url.openStream());
			this.logger.debug(this.localisation.getMessage(this, "saving-resource", url.toString()));
			fos = new FileOutputStream(storage);
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		} finally {
			rbc.close();
			fos.close();
		}
	}

	private boolean isNewVersionAvailable() {
		final DefaultArtifactVersion current = new DefaultArtifactVersion(this.version);
		this.logger.debug(this.localisation.getMessage(this, "local-version", current.toString()));
		final DefaultArtifactVersion target = new DefaultArtifactVersion(this.manifest.getCurrentVersion());
		this.logger.debug(this.localisation.getMessage(this, "remote-version", target.toString()));
		if (current.compareTo(target) == -1) {
			return true;
		} else {
			return false;
		}
	}

	private void parseMavenMetaData() throws IOException, SAXException, ParserConfigurationException {
		final File temp = File.createTempFile(this.artifactId, null);
		this.getMavenMetaData(temp);
		this.manifest = new MavenManifest(temp);
	}
}
