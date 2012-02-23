/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * AbstractConfiguration.java is part of BukkitUtilities.
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

package name.richardson.james.bukkit.utilities.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.plugin.Plugin;

import name.richardson.james.bukkit.utilities.internals.Logger;

public abstract class AbstractConfiguration implements Configuration {

  protected final Logger logger = new Logger(this.getClass());

  protected org.bukkit.configuration.file.YamlConfiguration configuration;

  private final Plugin plugin;
  private final File file;
  private final String fileName;

  public AbstractConfiguration(final Plugin plugin, final String fileName) throws IOException {
    this.plugin = plugin;
    this.fileName = fileName;
    this.file = new File(plugin.getDataFolder() + "/" + this.fileName);
    this.load();
    this.setDefaults();
  }

  public org.bukkit.configuration.file.YamlConfiguration getDefaults() throws IOException {
    final InputStream resource = this.plugin.getResource(this.file.getName());
    final org.bukkit.configuration.file.YamlConfiguration defaults = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(resource);
    resource.close();
    return defaults;
  }

  public void load() {
    this.logger.debug(String.format("Loading configuration: %s.", this.file.getName()));
    this.logger.debug(String.format("Using path: %s.", this.file.getPath()));
    this.configuration = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(this.file);
  }

  public void save() throws IOException {
    this.logger.debug(String.format("Saving configuration: %s.", this.file.getName()));
    this.configuration.save(this.file);
  }

  public void setDefaults() throws IOException {
    this.logger.debug(String.format("Apply default configuration."));
    final org.bukkit.configuration.file.YamlConfiguration defaults = this.getDefaults();
    this.configuration.setDefaults(defaults);
    this.configuration.options().copyDefaults(true);
    this.save();
  }

}
