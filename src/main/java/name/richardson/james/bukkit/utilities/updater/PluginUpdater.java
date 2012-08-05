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
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.xml.sax.SAXException;

import name.richardson.james.bukkit.utilities.logging.Logger;
import name.richardson.james.bukkit.utilities.plugin.Plugin;

public class PluginUpdater implements Runnable, Listener {

  public enum State {
    NOTIFY,
    OFF
  }
  
  public enum Branch {
    DEVELOPMENT,
    STABLE
  }
  
  /* A reference to the downloaded Maven manifest from the remote repository */
  private MavenManifest manifest;

  /* The state that the updater should operate in */
  private final State state;

  /* The branch of the maven repo to test against */
  private final Branch branch;
  
  /* Random delay to wait for before updating */
  final long delay = new Random().nextInt(20) * 20;

  private final Logger logger;

  private final String artifactId;

  private final String groupId;

  private final URL repositoryURL;

  private final String version;

  private Plugin plugin;
  
  public PluginUpdater(Plugin plugin, final State state, final Branch branch) {
    this.branch = branch;
    this.state = state;
    this.logger = plugin.getCustomLogger();
    this.version = plugin.getDescription().getVersion();
    this.artifactId = plugin.getArtifactID();
    this.groupId = plugin.getGroupID();
    this.repositoryURL = plugin.getRepositoryURL();
    this.plugin = plugin;
    Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, this, delay);
  }

  public void run() {
    try {
      this.parseMavenMetaData();
      if (this.isNewVersionAvailable()) {
        switch (this.state) {
        case NOTIFY:
          this.logger.info(this, "new-version-available", this.manifest.getCurrentVersion()); 
        }
      }
    } catch (final IOException e) {
      this.logger.warning(this, "unable-to-read-metadata", this.artifactId);
    } catch (final SAXException e) {
      this.logger.warning(this, "unable-to-read-metadata", this.artifactId);
    } catch (final ParserConfigurationException e) {
      this.logger.warning(this, "unable-to-read-metadata", this.artifactId);
    }
  }
  
  public void onPlayerJoin(PlayerJoinEvent event) {
    final Player player = event.getPlayer();
    if (this.plugin.getPermissionManager().hasPlayerPermission(player, plugin.getRootPermission())) {
      final String message = this.plugin.getLocalisation().getMessage(this, "new-version-available", this.plugin.getName(), this.manifest.getCurrentVersion());
      player.sendMessage(message);
    }
  }

  private void getMavenMetaData(File storage) throws IOException {
    final StringBuilder path = new StringBuilder();
    path.append(this.repositoryURL);
    path.append("/");
    path.append(this.groupId.replace(".", "/"));
    path.append("/");
    path.append(this.artifactId);
    path.append("/maven-metadata.xml");
    URL url = new URL(path.toString());
    // get the file 
    ReadableByteChannel rbc = null;
    FileOutputStream fos = null;
    try {
      this.logger.debug(this, "fetching-resource", url.toString()); 
      rbc = Channels.newChannel(url.openStream());
      this.logger.debug(this, "saving-resource", url.toString()); 
      fos = new FileOutputStream(storage);
      fos.getChannel().transferFrom(rbc, 0, 1 << 24);
    } finally {
      rbc.close();
      fos.close();
    }
  }


  
  private boolean isNewVersionAvailable() {
    final DefaultArtifactVersion current = new DefaultArtifactVersion(this.version);
    this.logger.debug(this, "local-version", current.toString()); 
    final DefaultArtifactVersion target = new DefaultArtifactVersion(this.manifest.getCurrentVersion());
    this.logger.debug(this, "remote-version", target.toString()); 
    if (current.compareTo(target) == -1) {
      return true;
    } else {
      return false;
    }
  }
 


  private void parseMavenMetaData() throws IOException, SAXException, ParserConfigurationException {
    final File temp = File.createTempFile(artifactId, null);
    this.getMavenMetaData(temp);
    this.manifest = new MavenManifest(temp);
  }

}
