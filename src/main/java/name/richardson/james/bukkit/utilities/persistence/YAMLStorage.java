package name.richardson.james.bukkit.utilities.persistence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.internals.Logger;

public class YAMLStorage {

  /* The logger assigned to this class */
  protected final Logger logger = new Logger(this.getClass());

  /* The original bukkit YAML configuration that we are wrapping around */
  protected org.bukkit.configuration.file.YamlConfiguration configuration;

  /* A handle to the storage file on disk */
  private final File file;

  private final JavaPlugin plugin;

  public YAMLStorage(JavaPlugin plugin, String name) throws IOException {
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
