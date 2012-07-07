package name.richardson.james.bukkit.utilities.persistence;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExampleExpression;
import com.avaje.ebean.LikeType;

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
  
  public int count(final Class<?> recordClass) {
    this.logger.debug("Attempting to get row count for eBean " + recordClass.getSimpleName() + ".");
    if (!classes.contains(recordClass)) throw new IllegalArgumentException();
    return this.database.find(recordClass).findRowCount();
  }

  public int count(final Object record) {
    if (!classes.contains(record)) throw new IllegalArgumentException();
    this.logger.debug("Attempting to get row count for " + record.getClass().getSimpleName() + " using an example eBean.");
    this.logger.debug(record.toString());
    final ExampleExpression expression = this.database.getExpressionFactory().exampleLike(record, true, LikeType.EQUAL_TO);
    return this.database.find(record.getClass()).where().add(expression).findRowCount();
  }

  public int delete(final List<? extends Object> records) {
    this.logger.debug("Deleting " + String.valueOf(records.size()) + " record(s) from database.");
    if (records.isEmpty() || !classes.contains(records.get(0).getClass())) throw new IllegalArgumentException();
    return this.database.delete(records);
  }

  public void delete(final Object record) {
    this.logger.debug("Deleting " + record.getClass().getSimpleName() + " from database.");
    if (!classes.contains(record)) throw new IllegalArgumentException();
    this.logger.debug(this.toString());
    this.database.delete(record);
  }

  public List<? extends Object> find(final Object record) {
    if (!classes.contains(record)) throw new IllegalArgumentException();
    this.logger.debug("Attempting to return records " + record.getClass().getSimpleName() + " using an example eBean.");
    this.logger.debug(record.toString());
    final ExampleExpression expression = this.database.getExpressionFactory().exampleLike(record, true, LikeType.EQUAL_TO);
    return this.database.find(record.getClass()).where().add(expression).findList();
  }

  public EbeanServer getEbeanServer() {
    return this.database;
  }

  public List<? extends Object> list(final Class<?> record) {
    this.logger.debug("Attempting to return all records matching the class: " + record.getName());
    if (!classes.contains(record)) throw new IllegalArgumentException();
    this.logger.debug(record.toString());
    return this.database.find(record).findList();
  }

  public int save(final List<? extends Object> records) {
    this.logger.debug("Saving " + String.valueOf(records.size()) + " record(s) to the database.");
    return this.database.save(records);
  }

  public void save(final Object record) {
    this.logger.debug("Saving " + record.getClass().getSimpleName() + " to the database.");
    this.logger.debug(record.toString());
    this.database.save(record);
  }

}
