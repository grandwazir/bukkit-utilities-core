/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * SQLStorage.java is part of BukkitUtilities.
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

import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.LogLevel;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.internals.Logger;
import name.richardson.james.bukkit.utilities.localisation.Localised;
import name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin;

public class SQLStorage extends Localised {

  /* The logger assigned to this class */
  protected final Logger logger = new Logger(this.getClass());

  /* Database classes that are in use by this plugin */
  private final List<Class<?>> classes;

  /* The classloader of the plugin */
  private ClassLoader classLoader;

  /* The data source configuration for initalising EBean databases */
  private DataSourceConfig dataSourceConfiguration;

  /* The EbeanDatabase backend */
  private EbeanServer ebeanServer;

  /* The inital log level */
  private Level logLevel;

  /* The plugin that is using this storage */
  private final SkeletonPlugin plugin;

  /* The schema used by this storage object */
  private SQLSchema schema;

  /* The bukkit server */
  private final Server server = Bukkit.getServer();

  /* The server configuration for initalising EBean databases */
  private final ServerConfig serverConfiguration = new ServerConfig();

  /**
   * Instantiates a new sQL storage.
   * 
   * @param plugin the plugin
   */
  public SQLStorage(final SkeletonPlugin plugin) {
    super(plugin);

    this.plugin = plugin;
    this.logger.setPrefix(plugin.getLoggerPrefix());
    this.classes = plugin.getDatabaseClasses();
    this.initalise();
  }

  /**
   * Instantiates a new SQLStorage.
   * 
   * @param plugin the plugin
   * @param config the config
   */
  public SQLStorage(final SkeletonPlugin plugin, final DataSourceConfig config) {
    super(plugin);
    this.plugin = plugin;
    this.logger.setPrefix(plugin.getLoggerPrefix());
    this.classes = plugin.getDatabaseClasses();
    this.dataSourceConfiguration = config;
    this.initalise();
  }

  /**
   * Instantiates a new SQLStorage.
   * 
   * @param plugin the plugin
   * @param classes the classes
   */
  public SQLStorage(final SkeletonPlugin plugin, final List<Class<?>> classes) {
    super(plugin);
    this.plugin = plugin;
    this.logger.setPrefix(plugin.getLoggerPrefix());
    this.classes = classes;
    this.initalise();
  }

  /**
   * Instantiates a new SQLStorage.
   * 
   * @param plugin the plugin
   * @param classes the classes
   * @param config the config
   */
  public SQLStorage(final SkeletonPlugin plugin, final List<Class<?>> classes, final DataSourceConfig config) {
    super(plugin);
    this.plugin = plugin;
    this.logger.setPrefix(plugin.getLoggerPrefix());
    this.classes = classes;
    this.dataSourceConfiguration = config;
    this.initalise();
  }

  /**
   * Gets the database platform.
   * 
   * @return the database platform
   */
  public String getDatabasePlatform() {
    return this.dataSourceConfiguration.getDriver();
  }

  /**
   * Gets the database server.
   * 
   * @return the database server
   */
  public EbeanServer getDatabaseServer() {
    return this.ebeanServer;
  }

  /**
   * Gets the ebean server.
   * 
   * @return the ebean server
   */
  public EbeanServer getEbeanServer() {
    return this.ebeanServer;
  }

  /**
   * Gets the schema.
   * 
   * @return the schema
   */
  public SQLSchema getSchema() {
    return this.schema;
  }

  /**
   * Checks if is using SQLLite.
   * 
   * @return true, if is using SQLLite
   */
  public boolean isUsingSQLLite() {
    return this.dataSourceConfiguration.getDriver().equalsIgnoreCase("org.sqlite.JDBC");
  }

  /**
   * After database create.
   */
  protected void afterDatabaseCreate() {
    this.logger.debug("Skipping after database create.");
  }

  /**
   * After database drop.
   */
  protected void afterDatabaseDrop() {
    this.logger.debug("Skipping after database drop.");
  }

  /**
   * Before database create.
   */
  protected void beforeDatabaseCreate() {
    this.logger.debug("Skipping before database create.");
  }

  /**
   * Before database drop.
   */
  protected void beforeDatabaseDrop() {
    this.logger.debug("Skipping before database drop.");
  }

  /**
   * Initalise the database.
   */
  private void initalise() {
    this.logLevel = java.util.logging.Logger.getLogger("").getLevel();
    if (this.plugin.isDebugging()) {
      this.logger.setDebugging(true);
    }
    this.logger.debug("Initalising database...");

    if (this.ebeanServer != null) {
      throw new IllegalStateException("Database is already initalised!");
    }

    try {
      final Method method = JavaPlugin.class.getDeclaredMethod("getClassLoader");
      method.setAccessible(true);
      this.classLoader = (ClassLoader) method.invoke(this.plugin);
    } catch (final Exception exception) {
      throw new RuntimeException("Failed to retrieve the ClassLoader of the plugin using Reflection", exception);
    }

    this.setServerConfiguration();
    this.setDataSourceConfiguration();
    this.loadDatabase();
    this.installSchema();

  }

  /**
   * Install the database schema.
   */
  private void installSchema() {
    if (!this.isSchemaPresent()) {
      this.logger.warning(this.getMessage("schema-invalid"));
      this.logger.info(this.getMessage("create-schema"));

      try {
        if (!this.plugin.isDebugging()) {
          java.util.logging.Logger.getLogger("").setLevel(Level.OFF);
        }
        SQLSchema schema = new SQLSchema(this.plugin, this.ebeanServer, this.isUsingSQLLite());
        this.beforeDatabaseDrop();
        schema.runDropDDL();
        if (this.isUsingSQLLite()) {
          schema = new SQLSchema(this.plugin, this.ebeanServer, this.isUsingSQLLite());
          this.loadDatabase();
        }
        this.afterDatabaseDrop();
        this.beforeDatabaseCreate();
        schema.runGenerateDDL();
        this.afterDatabaseCreate();
      } catch (final Exception exception) {
        throw new RuntimeException("Failed to create database schema! " + exception);
      } finally {
        java.util.logging.Logger.getLogger("").setLevel(this.logLevel);
      }
    }
  }

  /**
   * Checks if is database schema present and correct.
   * 
   * @return true, if is schema present
   */
  private boolean isSchemaPresent() {
    this.logger.debug("Checking if current schema is present.");
    for (final Class<?> ebean : this.classes) {
      try {
        this.ebeanServer.find(ebean).findRowCount();
      } catch (final Exception exception) {
        return false;
      }
    }
    return true;
  }

  /**
   * Load the database.
   */
  private void loadDatabase() {
    this.logger.debug("Loading database.");
    ClassLoader currentClassLoader = null;

    try {
      java.util.logging.Logger.getLogger("").setLevel(Level.OFF);
      currentClassLoader = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.classLoader);
      this.ebeanServer = EbeanServerFactory.create(this.serverConfiguration);
    } finally {
      java.util.logging.Logger.getLogger("").setLevel(this.logLevel);
      if (currentClassLoader != null) {
        Thread.currentThread().setContextClassLoader(currentClassLoader);
      }
    }
  }

  /**
   * Replace tokens in database string.
   * 
   * @param url the url
   * @return the string
   */
  private String replaceDatabaseString(String url) {
    url = url.replaceAll("\\{DIR\\}", this.plugin.getDataFolder().getPath().replaceAll("\\\\", "/") + "/");
    url = url.replaceAll("\\{NAME\\}", this.plugin.getDescription().getName().replaceAll("[^\\w_-]", ""));
    return url;
  }

  /**
   * Sets the data source configuration.
   */
  private void setDataSourceConfiguration() {
    this.logger.debug("Configuring data source using Bukkit configuration.");
    this.dataSourceConfiguration = this.serverConfiguration.getDataSourceConfig();
    this.dataSourceConfiguration.setUrl(this.replaceDatabaseString(this.dataSourceConfiguration.getUrl()));
  }

  /**
   * Sets the server configuration.
   */
  private void setServerConfiguration() {
    this.logger.debug("Establishing server configuration.");
    this.server.configureDbConfig(this.serverConfiguration);
    this.serverConfiguration.setDefaultServer(false);
    this.serverConfiguration.setRegister(false);
    this.serverConfiguration.setLoggingLevel(LogLevel.NONE);
    if (this.logger.isDebugging()) {
      this.serverConfiguration.setLoggingLevel(LogLevel.SQL);
      this.serverConfiguration.setLoggingToJavaLogger(true);
    }
    this.logger.debug("Registering database classes.");
    this.serverConfiguration.setClasses(this.classes);
    this.serverConfiguration.setName(this.plugin.getName());
  }

}
