package name.richardson.james.bukkit.utilities.persistence;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.avaje.ebean.EbeanServer;

import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.internals.Logger;

public abstract class SQLStorage implements Storage {

  /* The logger assigned to this class */
  protected final Logger logger = new Logger(this.getClass());
  
  /* The database object we are associated with */
  private final EbeanServer database;
  
  /* Database classes that are in use by this plugin */
  private final List<Class<?>> classes = new LinkedList<Class<?>>();
  
  public SQLStorage(JavaPlugin plugin) {
    this.database = plugin.getDatabase();
    this.load();
    this.setDefaults();
  }
  
  public List<Class<?>> getDatabaseClasses() {
    return Collections.unmodifiableList(classes);
  }
  
  public EbeanServer getDatabaseServer() {
    return database;
  }

}
