package name.richardson.james.bukkit.utilities.persistence;

import java.lang.reflect.Method;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
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

  /* The plugin that is using this storage */
  private final SkeletonPlugin plugin;

  /* The schema used by this storage object */
  private SQLSchema schema;

  /* The bukkit server */
  private final Server server = Bukkit.getServer();

  /* The server configuration for initalising EBean databases */
  private final ServerConfig serverConfiguration = new ServerConfig();

  public SQLStorage(final SkeletonPlugin plugin) {
    super(plugin);
    this.plugin = plugin;
    this.classes = plugin.getDatabaseClasses();
    this.initalise();
  }

  public SQLStorage(final SkeletonPlugin plugin, final DataSourceConfig config) {
    super(plugin);
    this.plugin = plugin;
    this.classes = plugin.getDatabaseClasses();
    this.dataSourceConfiguration = config;
    this.initalise();
  }

  public SQLStorage(final SkeletonPlugin plugin, final List<Class<?>> classes) {
    super(plugin);
    this.plugin = plugin;
    this.classes = classes;
    this.initalise();
  }

  public SQLStorage(final SkeletonPlugin plugin, final List<Class<?>> classes, final DataSourceConfig config) {
    super(plugin);
    this.plugin = plugin;
    this.classes = classes;
    this.dataSourceConfiguration = config;
    this.initalise();
  }

  public String getDatabasePlatform() {
    return this.dataSourceConfiguration.getDriver();
  }

  public EbeanServer getDatabaseServer() {
    return this.ebeanServer;
  }

  public EbeanServer getEbeanServer() {
    return this.ebeanServer;
  }

  public SQLSchema getSchema() {
    return this.schema;
  }

  public boolean isUsingSQLLite() {
    return this.dataSourceConfiguration.getDriver().equalsIgnoreCase("org.sqlite.JDBC");
  }

  protected void afterDatabaseCreate() {
    this.logger.debug("Skipping after database create.");
  }

  protected void afterDatabaseDrop() {
    this.logger.debug("Skipping after database drop.");
  }

  protected void beforeDatabaseCreate() {
    this.logger.debug("Skipping before database create.");
  }

  protected void beforeDatabaseDrop() {
    this.logger.debug("Skipping before database drop.");
  }

  private void initalise() {
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

  private void installSchema() {
    if (!this.isSchemaPresent()) {
      this.logger.warning(this.getMessage("schema-invalid"));
      this.logger.info(this.getMessage("create-schema"));
      try {
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
      }
    }
  }

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

  private void loadDatabase() {
    this.logger.debug("Loading database.");
    ClassLoader currentClassLoader = null;

    try {
      currentClassLoader = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.classLoader);
      this.ebeanServer = EbeanServerFactory.create(this.serverConfiguration);
    } finally {
      if (currentClassLoader != null) {
        Thread.currentThread().setContextClassLoader(currentClassLoader);
      }
    }
  }

  private String replaceDatabaseString(String url) {
    url = url.replaceAll("\\{DIR\\}", this.plugin.getDataFolder().getPath().replaceAll("\\\\", "/") + "/");
    url = url.replaceAll("\\{NAME\\}", this.plugin.getDescription().getName().replaceAll("[^\\w_-]", ""));
    return url;
  }

  private void setDataSourceConfiguration() {
    this.logger.debug("Configuring data source using Bukkit configuration.");
    this.dataSourceConfiguration = this.serverConfiguration.getDataSourceConfig();
    this.dataSourceConfiguration.setUrl(this.replaceDatabaseString(this.dataSourceConfiguration.getUrl()));
  }

  private void setServerConfiguration() {
    this.logger.debug("Establishing server configuration.");
    this.serverConfiguration.setDefaultServer(false);
    this.serverConfiguration.setRegister(false);
    this.logger.debug("Registering database classes.");
    this.serverConfiguration.setClasses(this.classes);
    this.logger.debug(this.serverConfiguration.getClasses().size() + " classes registered.");
    this.serverConfiguration.setName(this.plugin.getName());
    this.server.configureDbConfig(this.serverConfiguration);
  }

}
