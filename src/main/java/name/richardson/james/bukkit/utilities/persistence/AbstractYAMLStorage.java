/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * AbstractYAMLStorage.java is part of BukkitUtilities.
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

import org.bukkit.configuration.file.YamlConfiguration;

import name.richardson.james.bukkit.utilities.plugin.Plugin;

public abstract class AbstractYAMLStorage extends AbstractStorage {

  private YamlConfiguration configuration;

  private final File file;

  public AbstractYAMLStorage(final Plugin plugin, final String fileName) throws IOException {
    super(plugin);
    this.file = new File(plugin.getDataFolder() + File.separator + fileName);
    this.load();
    this.setDefaults(plugin.getResource(fileName));
  }

  public void save(final Object... objects) {
    return;
  }

  protected YamlConfiguration getConfiguration() {
    return this.configuration;
  }

  protected void load() {
    this.getLogger().config(String.format("Loading configuration: %s", this.getClass().getSimpleName()));
    this.getLogger().config(String.format("Using path: %s", this.file.getAbsolutePath()));
    this.configuration = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(this.file);
  }

  protected void save() {
    try {
      this.getLogger().config(String.format("Saving configuration: %s", this.file.getName()));
      this.configuration.save(this.file);
    } catch (final IOException e) {
      this.getLogger().severe(this.getLocalisation().getMessage(this, "unable-to-save"));
      e.printStackTrace();
    }
  }

  protected void setDefaults(final InputStream resource) throws IOException {
    final org.bukkit.configuration.file.YamlConfiguration defaults = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(resource);
    resource.close();
    this.getLogger().config("Saving default configuration");
    this.configuration.setDefaults(defaults);
    this.configuration.options().copyDefaults(true);
  }

}
