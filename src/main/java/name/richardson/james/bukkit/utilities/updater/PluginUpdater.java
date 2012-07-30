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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.xml.sax.SAXException;

import name.richardson.james.bukkit.utilities.internals.Logger;
import name.richardson.james.bukkit.utilities.localisation.Localisable;
import name.richardson.james.bukkit.utilities.localisation.Localised;
import name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin;

public class PluginUpdater extends Localised implements Runnable {

  /* The logger for this class */
  private final Logger logger = new Logger(PluginUpdater.class);

  /* A reference to the downloaded Maven manifest from the remote repository */
  private MavenManifest manifest;

  /* The plugin that this updater belongs to */
  private final SkeletonPlugin plugin;

  /* The state that the updater should operate in */
  private final State state;

  public PluginUpdater(final SkeletonPlugin plugin, final State state) {
    super(plugin);
    this.plugin = plugin;
    this.state = state;
    if (plugin.isDebugging()) {
      this.logger.setDebugging(true);
    }
  }

  public void run() {

    this.logger.setPrefix("[" + this.plugin.getName() + "] ");
    this.logger.debug(this.getMessage("checking-for-new-version"));

    try {
      this.parseMavenMetaData();
    } catch (final IOException e) {
      this.logger.warning(this.getMessage("unable-to-save-file"));
      e.printStackTrace();
    } catch (final SAXException e) {
      this.logger.warning(this.getMessage("unable-to-read-metadata"));
      e.printStackTrace();
    } catch (final ParserConfigurationException e) {
      this.logger.warning(this.getMessage("unable-to-read-metadata"));
      e.printStackTrace();
    }

    if (this.isNewVersionAvailable()) {
      if (this.state == State.AUTOMATIC) {
        try {
          // create the path for the updated plugin
          final File updateFolder = this.plugin.getServer().getUpdateFolderFile();
          if (!updateFolder.exists()) {
            updateFolder.mkdirs();
          }
          final StringBuilder path = new StringBuilder();
          path.append(updateFolder.getAbsolutePath());
          path.append(File.separatorChar);
          path.append(this.plugin.getDescription().getName());
          path.append(".jar");
          // create the URL of the updated plugin
          // download the update to the update folder
          this.logger.debug("Path to save updated plugin: " + path);
          final File storage = new File(path.toString());
          storage.createNewFile();
          // normalise the plugin name as necessary
          this.normalisePluginFileName();
          this.fetchFile(this.getPluginURL(), storage);
          this.logger.info(this.getSimpleFormattedMessage("plugin-updated", this.manifest.getCurrentVersion()));
        } catch (final MalformedURLException e) {
          this.logger.warning(this.getMessage("unable-to-get-plugin"));
          e.printStackTrace();
        } catch (final IOException e) {
          this.logger.warning(this.getMessage("unable-to-save-file"));
          e.printStackTrace();
        }
      } else {
        this.logger.info(this.getSimpleFormattedMessage("newer-version-available", this.manifest.getCurrentVersion()));
      }
    }
  }

  private void fetchFile(final URL url, final File storage) throws IOException {
    this.logger.debug("Fetching resource from " + url.toString());
    final ReadableByteChannel rbc = Channels.newChannel(url.openStream());
    this.logger.debug("Saving resources to " + storage.getPath());
    final FileOutputStream fos = new FileOutputStream(storage);
    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
    rbc.close();
    fos.close();
  }

  private URL getMavenMetaDataURL() throws MalformedURLException {
    final StringBuilder path = new StringBuilder();
    path.append(this.plugin.getRepositoryURL());
    path.append("/");
    path.append(this.plugin.getGroupID().replace(".", "/"));
    path.append("/");
    path.append(this.plugin.getArtifactID());
    path.append("/maven-metadata.xml");
    return new URL(path.toString());
  }

  // This is used to search the plugin directory and then change the name of the
  // plugin
  // if necessary. The .jar should match the name of the plugin as defined in
  // plugin.yml.
  // This is necessary otherwise the updater in Bukkit will not work.
  private File getPluginFile() {
    final File plugins = this.plugin.getDataFolder().getParentFile();
    final String[] files = plugins.list(new PluginFilter(this.plugin));
    this.logger.debug(files.toString());
    return new File(this.plugin.getDataFolder().getParentFile().toString() + File.separatorChar + files[0]);
  }

  private URL getPluginURL() throws MalformedURLException {
    final String version = this.manifest.getCurrentVersion();
    final StringBuilder path = new StringBuilder();
    path.append(this.plugin.getRepositoryURL());
    path.append("/");
    path.append(this.plugin.getGroupID().replace(".", "/"));
    path.append("/");
    path.append(this.plugin.getArtifactID());
    path.append("/");
    path.append(version);
    path.append("/");
    path.append(this.plugin.getArtifactID());
    path.append("-");
    path.append(version);
    path.append(".jar");
    return new URL(path.toString());
  }

  private boolean isNewVersionAvailable() {
    final DefaultArtifactVersion current = new DefaultArtifactVersion(this.plugin.getDescription().getVersion());
    this.logger.debug("Current local version: " + current.toString());
    final DefaultArtifactVersion target = new DefaultArtifactVersion(this.manifest.getCurrentVersion());
    this.logger.debug("Latest remote version: " + target.toString());
    if (current.compareTo(target) == -1) {
      return true;
    } else {
      return false;
    }
  }

  private void normalisePluginFileName() {
    final String name = this.plugin.getName() + ".jar";
    final File plugin = this.getPluginFile();
    if (!plugin.getName().equals(name)) {
      this.logger.debug("Plugin file name is inconsistent. Renaming to " + name + ".");
      final File file = new File(plugin.getParentFile().toString() + File.separatorChar + name);
      plugin.renameTo(file);
    }
  }

  private void parseMavenMetaData() throws IOException, SAXException, ParserConfigurationException {
    final File temp = File.createTempFile(this.plugin.getClass().getSimpleName() + "-", null);
    this.fetchFile(this.getMavenMetaDataURL(), temp);
    this.manifest = new MavenManifest(temp);
  }

}
