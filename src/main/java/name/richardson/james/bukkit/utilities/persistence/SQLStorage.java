package name.richardson.james.bukkit.utilities.persistence;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.ExampleExpression;
import com.avaje.ebean.LikeType;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.internals.Logger;
import name.richardson.james.bukkit.utilities.localisation.Localisable;
import name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin;

public class SQLStorage implements Localisable {

  private static final boolean debugging = true;
  
  /* The logger assigned to this class */
  protected final Logger logger = new Logger(this.getClass());

  /* Database classes that are in use by this plugin */
  private final List<Class<?>> classes;

  /* The plugin that is using this storage */
  private final SkeletonPlugin plugin;

  /* The classloader of the plugin */
  private ClassLoader classLoader;

  /* The EbeanDatabase backend */
  private EbeanServer ebeanServer;

  /* The server configuration for initalising EBean databases */
  private final ServerConfig serverConfiguration = new ServerConfig();

  /* The bukkit server */
  private final Server server = Bukkit.getServer();

  /* The data source configuration for initalising EBean databases */
  private DataSourceConfig dataSourceConfiguration;

  /* The schema used by this storage object */
  private SQLSchema schema;

  public SQLStorage(final SkeletonPlugin plugin) {
    this.plugin = plugin;
    this.classes = plugin.getDatabaseClasses();
    this.initalise();
  }
  
  public SQLStorage(final SkeletonPlugin plugin, DataSourceConfig config) {
    this.plugin = plugin;
    this.classes = plugin.getDatabaseClasses();
    this.dataSourceConfiguration = config;
    this.initalise();
  }
  
  public SQLStorage(final SkeletonPlugin plugin, List<Class<?>> classes) {
    this.plugin = plugin;
    this.classes = classes;
    this.initalise();
  }
  
  public SQLStorage(final SkeletonPlugin plugin, List<Class<?>> classes, DataSourceConfig config) {
    this.plugin = plugin;
    this.classes = classes;
    this.dataSourceConfiguration = config;
    this.initalise();
  }

  public int count(final Class<?> recordClass) {
    this.logger.debug("Attempting to get row count for eBean " + recordClass.getSimpleName() + ".");
    if (!this.classes.contains(recordClass)) {
      throw new IllegalArgumentException();
    }
    return this.ebeanServer.find(recordClass).findRowCount();
  }

  public int count(final Object record) {
    if (!this.classes.contains(record)) {
      throw new IllegalArgumentException();
    }
    this.logger.debug("Attempting to get row count for " + record.getClass().getSimpleName() + " using an example eBean.");
    this.logger.debug(record.toString());
    final ExampleExpression expression = this.ebeanServer.getExpressionFactory().exampleLike(record, true, LikeType.EQUAL_TO);
    return this.ebeanServer.find(record.getClass()).where().add(expression).findRowCount();
  }

  public int delete(final List<? extends Object> records) {
    this.logger.debug("Deleting " + String.valueOf(records.size()) + " record(s) from database.");
    if (records.isEmpty() || !this.classes.contains(records.get(0).getClass())) {
      throw new IllegalArgumentException();
    }
    return this.ebeanServer.delete(records);
  }

  public void delete(final Object record) {
    this.logger.debug("Deleting " + record.getClass().getSimpleName() + " from database.");
    if (!this.classes.contains(record)) {
      throw new IllegalArgumentException();
    }
    this.logger.debug(this.toString());
    this.ebeanServer.delete(record);
  }

  public List<? extends Object> find(final Object record) {
    if (!this.classes.contains(record)) {
      throw new IllegalArgumentException();
    }
    this.logger.debug("Attempting to return records " + record.getClass().getSimpleName() + " using an example eBean.");
    this.logger.debug(record.toString());
    final ExampleExpression expression = this.ebeanServer.getExpressionFactory().exampleLike(record, true, LikeType.EQUAL_TO);
    return this.ebeanServer.find(record.getClass()).where().add(expression).findList();
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
    return schema;
  }

  public boolean isUsingSQLLite() {
    return this.dataSourceConfiguration.getDriver().equalsIgnoreCase("org.sqlite.JDBC");
  }

  public List<? extends Object> list(final Class<?> record) {
    this.logger.debug("Attempting to return all records matching the class: " + record.getName());
    if (!this.classes.contains(record)) {
      throw new IllegalArgumentException("That is not a valid database class");
    }
    this.logger.debug(record.toString());
    return this.ebeanServer.find(record).findList();
  }
  

  public int save(final Object[] records) {
    this.logger.debug("Saving " + String.valueOf(records.length) + " record(s) to the database.");
    return this.ebeanServer.save(Arrays.asList(records));
  }
  
  public void save(final Object record) {
    this.logger.debug("Saving " + record.getClass().getSimpleName() + " to the database.");
    this.logger.debug(record.toString());
    this.ebeanServer.save(record);
  }
  

  private void initalise() {
    if (plugin.isDebugging()) logger.setDebugging(true);
    logger.debug("Initalising database...");
    
    if (this.ebeanServer != null) {
      throw new IllegalStateException("Database is already initalised!");
    }
    
    try {
      final Method method = JavaPlugin.class.getDeclaredMethod("getClassLoader");
      method.setAccessible(true);
      this.classLoader = (ClassLoader) method.invoke(plugin);
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
      logger.warning(this.getMessage("schema-invalid"));
      logger.info(this.getMessage("create-schema"));
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

  protected void beforeDatabaseCreate() {
    logger.debug("Skipping before database create.");
  }

  protected void afterDatabaseCreate() {
    logger.debug("Skipping after database create.");
  }

  protected void beforeDatabaseDrop() {
    logger.debug("Skipping before database drop.");
  }
  
  protected void afterDatabaseDrop() {
    logger.debug("Skipping after database drop.");
  }

  private boolean isSchemaPresent() {
    logger.debug("Checking if current schema is present.");
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
    logger.debug("Loading database.");
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
    logger.debug("Configuring data source using Bukkit configuration.");
    this.dataSourceConfiguration = this.serverConfiguration.getDataSourceConfig();
    this.dataSourceConfiguration.setUrl(this.replaceDatabaseString(this.dataSourceConfiguration.getUrl()));
  }

  private void setServerConfiguration() {
    logger.debug("Establishing server configuration.");
    this.serverConfiguration.setDefaultServer(false);
    this.serverConfiguration.setRegister(false);
    logger.debug("Registering database classes.");
    this.serverConfiguration.setClasses(this.classes);
    logger.debug(this.serverConfiguration.getClasses().size() + " classes registered.");
    this.serverConfiguration.setName(this.plugin.getName());
    this.server.configureDbConfig(this.serverConfiguration);
  }
  
  public String getChoiceFormattedMessage(String key, final Object[] arguments, final String[] formats, final double[] limits) {
    key = this.getClass().getSimpleName().toLowerCase() + "." + key;
    return this.plugin.getChoiceFormattedMessage(key, arguments, formats, limits);
  }
  
  public Locale getLocale() {
    return this.plugin.getLocale();
  }

  public String getMessage(String key) {
    key = this.getClass().getSimpleName().toLowerCase() + "." + key;
    return this.plugin.getMessage(key);
  }
  
  public String getSimpleFormattedMessage(String key, final Object argument) {
    final Object[] arguments = { argument };
    return this.getSimpleFormattedMessage(key, arguments);
  }

  public String getSimpleFormattedMessage(String key, final Object[] arguments) {
    key = this.getClass().getSimpleName().toLowerCase() + "." + key;
    return this.plugin.getSimpleFormattedMessage(key, arguments);
  }


}
