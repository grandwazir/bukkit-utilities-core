package name.richardson.james.bukkit.utilities.persistence;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.LogLevel;
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
    this.serverConfig.setName(plugin.getName());
    this.datasourceConfig = configuration.getDataSourceConfig();
    this.setClassLoader(plugin);
  }
  
  public void initalise() {
    if (this.ebeanserver != null) this.getLogger().warning(SQLStorage.class, "already-initalised");
    this.load();
    if (!this.validate() || this.rebuild) {
      final SpiEbeanServer server = (SpiEbeanServer) ebeanserver;
      this.generator = server.getDdlGenerator();
      this.drop();
      this.create();
      this.getLogger().info(SQLStorage.class, "rebuilt");
    }
  }
  
  public List<Class<?>> getClasses() {
    return this.classes;
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
  
  private void load() {
    this.getLogger().debug(SQLStorage.class, "loading-database");
    Level level = java.util.logging.Logger.getLogger("").getLevel();
    ClassLoader currentClassLoader = null;
    try {
      this.serverConfig.setClasses(classes);
      if (this.getLogger().isDebugging()) {
        this.serverConfig.setLoggingToJavaLogger(true);
        this.serverConfig.setLoggingLevel(LogLevel.SQL);
      }
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
  
  private boolean validate() {
    this.getLogger().debug(SQLStorage.class, "validating-database");
    for (final Class<?> ebean : this.classes) {
      try {
        this.ebeanserver.find(ebean).findRowCount();
      } catch (final Exception exception) {
        this.getLogger().warning(SQLStorage.class, "validation-failed", exception.getLocalizedMessage());
        return false;
      }
    }
    this.getLogger().debug(SQLStorage.class, "validation-passed");
    return true;
  }
  
  private void drop() {
    this.getLogger().debug(SQLStorage.class, "dropping-database");
    this.beforeDatabaseDrop();
    Level level = java.util.logging.Logger.getLogger("").getLevel();
    try {
      java.util.logging.Logger.getLogger("").setLevel(Level.OFF);
      this.generator.runScript(true, this.generator.generateDropDdl());
    } finally {
      java.util.logging.Logger.getLogger("").setLevel(level);
    }
  }
  
  protected void beforeDatabaseCreate() {
    // TODO Auto-generated method stub
    
  }

  protected void beforeDatabaseDrop() {
    // TODO Auto-generated method stub
    
  }

  protected void create() {
    this.getLogger().debug(SQLStorage.class, "creating-database");
    this.beforeDatabaseCreate();
    // reload the database this allows for removing classes
    String script = this.generator.generateCreateDdl();
    Level level = java.util.logging.Logger.getLogger("").getLevel();
    if (this.datasourceConfig.getDriver().contains("sqlite")) script = this.fixScript(script);
    try {
      java.util.logging.Logger.getLogger("").setLevel(Level.OFF);
      this.load();
      this.generator.runScript(false, script);
    } finally {
      java.util.logging.Logger.getLogger("").setLevel(level);
    }
    this.afterDatabaseCreate();
  }
  
  protected void afterDatabaseCreate() {
    // TODO Auto-generated method stub
  }

  private String fixScript(String script) {
    this.getLogger().debug(SQLStorage.class, "fixing-script");
    // Create a BufferedReader out of the potentially invalid script
    final BufferedReader scriptReader = new BufferedReader(new StringReader(script));
    // Create an array to store all the lines
    final List<String> scriptLines = new ArrayList<String>();
    // Create some additional variables for keeping track of tables
    final HashMap<String, Integer> foundTables = new HashMap<String, Integer>();
    // The name of the table we are currently reviewing
    String currentTable = null;
    int tableOffset = 0;

    try {
      // Loop through all lines
      String currentLine;
      while ((currentLine = scriptReader.readLine()) != null) {

        // Trim the current line to remove trailing spaces
        currentLine = currentLine.trim();
        // Add the current line to the rest of the lines
        scriptLines.add(currentLine.trim());

        // Check if the current line is of any use
        if (currentLine.startsWith("create table")) {
          // Found a table, so get its name and remember the line it has been
          // encountered on
          currentTable = currentLine.split(" ", 4)[2];
          foundTables.put(currentLine.split(" ", 3)[2], scriptLines.size() - 1);
        } else if (currentLine.startsWith(";") && (currentTable != null) && !currentTable.equals("")) {
          // Found the end of a table definition, so update the entry
          final int index = scriptLines.size() - 1;
          foundTables.put(currentTable, index);
          // Remove the last ")" from the previous line
          String previousLine = scriptLines.get(index - 1);
          previousLine = previousLine.substring(0, previousLine.length() - 1);
          scriptLines.set(index - 1, previousLine);
          // Change ";" to ");" on the current line
          scriptLines.set(index, ");");
          // Reset the table-tracker
          currentTable = null;
          // Found a potentially unsupported action
        } else if (currentLine.startsWith("alter table")) {
          final String[] alterTableLine = currentLine.split(" ", 4);

          if (alterTableLine[3].startsWith("add constraint")) {
            // Found an unsupported action: ALTER TABLE using ADD CONSTRAINT
            final String[] addConstraintLine = alterTableLine[3].split(" ", 4);

            // Check if this line can be fixed somehow
            if (addConstraintLine[3].startsWith("foreign key")) {
              // Calculate the index of last line of the current table
              final int tableLastLine = foundTables.get(alterTableLine[2]) + tableOffset;
              // Add a "," to the previous line
              scriptLines.set(tableLastLine - 1, scriptLines.get(tableLastLine - 1) + ",");
              // Add the constraint as a new line - Remove the ";" on the end
              final String constraintLine = String.format("%s %s %s", addConstraintLine[1], addConstraintLine[2], addConstraintLine[3]);
              scriptLines.add(tableLastLine, constraintLine.substring(0, constraintLine.length() - 1));
              // Remove this line and raise the table offset because a line has
              // been inserted.
              scriptLines.remove(scriptLines.size() - 1);
              tableOffset++;
            } else {
              // Exception: This line cannot be fixed but is known the be
              // unsupported by SQLite.
              throw new RuntimeException("Unsupported action encountered: ALTER TABLE using ADD CONSTRAINT with " + addConstraintLine[3]);
            }
          }
        }
      }
    } catch (final Exception exception) {
      throw new RuntimeException("Failed to valid the CreateDDL script!");
    }

    // Turn all the lines back into a single string
    String newScript = "";
    for (final String newLine : scriptLines) {
      newScript += newLine + "\n";
    }

    // Print the new script
    // System.out.println(newScript);

    // Return the fixed script
    return newScript;
  }

  public EbeanServer getEbeanServer() {
    return this.ebeanserver;
  }
  
  
  public void save(Object... objects) {
    // TODO Auto-generated method stub
    
  }
  
}
