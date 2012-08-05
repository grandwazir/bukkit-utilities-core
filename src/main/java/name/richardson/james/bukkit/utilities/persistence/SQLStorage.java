package name.richardson.james.bukkit.utilities.persistence;

import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;

import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.configuration.DatabaseConfiguration;
import name.richardson.james.bukkit.utilities.plugin.Plugin;

public class SQLStorage extends AbstractStorage {

  private EbeanServer ebeanserver;
  
  private final ServerConfig serverConfig;
  
  private final DataSourceConfig datasourceConfig;

  private ClassLoader classLoader;

  private final List<Class<?>> classes;
  
  private boolean rebuild;
  
  private DdlGenerator generator;

  public SQLStorage(Plugin plugin, DatabaseConfiguration configuration, List<Class<?>> classes) {
    super(plugin); 
    this.classes = classes;
    this.serverConfig = configuration.getServerConfig(); 
    this.datasourceConfig = configuration.getDataSourceConfig();
    this.setClassLoader(plugin);
  }
  
  protected void initalise() {
    if (this.ebeanserver != null) this.getLogger().warning(SQLStorage.class, "already-initalised");
    this.load();
    if (!this.validate() || this.rebuild) {
      final SpiEbeanServer server = (SpiEbeanServer) ebeanserver;
      this.generator = server.getDdlGenerator();
      this.drop();
      this.create();
    }
  }
  
  private void setClassLoader(Plugin plugin) {
    try {
      final Method method = JavaPlugin.class.getDeclaredMethod("getClassLoader");
      method.setAccessible(true);
      this.classLoader = (ClassLoader) method.invoke(plugin);
    } catch (final Exception exception) {
      throw new RuntimeException("Failed to retrieve the ClassLoader of the plugin using Reflection", exception);
    }
  }
  
  protected void load() {
    this.getLogger().warning(SQLStorage.class, "loading-database");
    Level level = java.util.logging.Logger.getLogger("").getLevel();
    ClassLoader currentClassLoader = null;
    try {
      java.util.logging.Logger.getLogger("").setLevel(Level.OFF);
      currentClassLoader = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.classLoader);
      this.ebeanserver = EbeanServerFactory.create(this.serverConfig);
    } finally {
      java.util.logging.Logger.getLogger("").setLevel(level);
      if (currentClassLoader != null) {
        Thread.currentThread().setContextClassLoader(currentClassLoader);
      }
    }
  }
  
  protected boolean validate() {
    this.getLogger().debug(SQLStorage.class, "validing-database");
    for (final Class<?> ebean : this.classes) {
      try {
        this.ebeanserver.find(ebean).findRowCount();
      } catch (final Exception exception) {
        this.getLogger().debug(SQLStorage.class, "validation-failed", exception.getLocalizedMessage());
        return false;
      }
    }
    this.getLogger().debug(SQLStorage.class, "validation-passed");
    return true;
  }
  
  protected void drop() {
    this.beforeDatabaseDrop();
    this.generator.runScript(true, this.generator.generateDropDdl());
    if (this.datasourceConfig.getDriver().contains("sqlite")) {
      this.load();
    }
    this.beforeDatabaseCreate();
  }
  
  private void beforeDatabaseCreate() {
    // TODO Auto-generated method stub
    
  }

  private void beforeDatabaseDrop() {
    // TODO Auto-generated method stub
    
  }

  protected void create() {
    this.beforeDatabaseCreate();
    String script = this.generator.generateCreateDdl();
    script = this.disableKeyConstraints(script);
    this.generator.runScript(false, script);
    this.afterDatabaseCreate();
  }
  
  private void afterDatabaseCreate() {
    // TODO Auto-generated method stub
    
  }

  //* this fixes a bug where using SQLlite you can not set foreign key constraits with an alter table statement. Without this fix using foreign keys will cause an exception. See http://www.sqlite.org/foreignkeys.html#fk_schemacommands for details. 
  private String disableKeyConstraints(String script) {
    // disable keys at the start and renable at the end
    script = this.serverConfig.getDatabasePlatform().getDbDdlSyntax().getDisableReferentialIntegrity() + "\n" + script;
    script = this.serverConfig.getDatabasePlatform().getDbDdlSyntax().getEnableReferentialIntegrity() + "\n" + script; 
    System.out.print(script);
    return script;
  }

  public EbeanServer getEbeanServer() {
    return this.ebeanserver;
  }
  
  
  public void save(Object... objects) {
    // TODO Auto-generated method stub
    
  }
  
}
