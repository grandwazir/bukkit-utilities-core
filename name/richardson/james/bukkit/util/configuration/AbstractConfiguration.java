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

package name.richardson.james.bukkit.util.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.bukkit.util.Plugin;

public abstract class AbstractConfiguration implements Configuration {

  protected final Logger logger = new Logger(this.getClass());

  protected org.bukkit.configuration.file.YamlConfiguration configuration;
 
  private final Plugin plugin;
  private final File file;
  private final String fileName;

  public AbstractConfiguration(final Plugin plugin, String fileName) throws IOException {
    this.plugin = plugin;
    this.fileName = fileName;
    this.file = new File(plugin.getDataFolder() + "/" + this.fileName);
    this.load();
    this.setDefaults();
  }

  @Override
  public org.bukkit.configuration.file.YamlConfiguration getDefaults() throws IOException {
    final InputStream resource = this.plugin.getResource(file.getName());
    final org.bukkit.configuration.file.YamlConfiguration defaults = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(resource);
    resource.close();
    return defaults;
  }

  @Override
  public void load() {
    logger.debug(String.format("Loading configuration: %s.", file.getName()));
    logger.debug(String.format("Using path: %s.", file.getPath()));
    this.configuration = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
  }

  @Override
  public void save() throws IOException {
    logger.debug(String.format("Saving configuration: %s.", file.getName()));
    this.configuration.save(file);
  }

  @Override
  public void setDefaults() throws IOException {
    logger.debug(String.format("Apply default configuration."));
    final org.bukkit.configuration.file.YamlConfiguration defaults = this.getDefaults();
    this.configuration.setDefaults(defaults);
    this.configuration.options().copyDefaults(true);
    this.save();
  }

}
