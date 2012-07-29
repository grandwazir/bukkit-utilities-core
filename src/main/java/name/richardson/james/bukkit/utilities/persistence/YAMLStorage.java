/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * YAMLStorage.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.persistence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.internals.Logger;

public class YAMLStorage {

  /* The original bukkit YAML configuration that we are wrapping around */
  protected org.bukkit.configuration.file.YamlConfiguration configuration;

  /* The logger assigned to this class */
  protected final Logger logger = new Logger(this.getClass());

  /* A handle to the storage file on disk */
  private final File file;

  private final JavaPlugin plugin;

  public YAMLStorage(final JavaPlugin plugin, final String name) throws IOException {
    this.plugin = plugin;
    this.file = new File(plugin.getDataFolder() + File.separator + name);
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
