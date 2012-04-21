/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * Database.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.database;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExampleExpression;
import com.avaje.ebean.LikeType;

import name.richardson.james.bukkit.utilities.internals.Logger;

public abstract class Database {

  protected EbeanServer database;
  protected Logger logger = new Logger(this.getClass());

  public Database(final EbeanServer database) {
    this.database = database;
  }

  public int count(final Class<?> recordClass) {
    this.logger.debug("Attempting to get row count matching a specific class.");
    return this.database.find(recordClass).findRowCount();
  }

  public int count(final Object record) {
    this.logger.debug("Attempting to get row count using an example.");
    this.logger.debug(record.toString());
    final ExampleExpression expression = this.database.getExpressionFactory().exampleLike(record, true, LikeType.EQUAL_TO);
    return this.database.find(record.getClass()).where().add(expression).findRowCount();
  }

  public int delete(final List<? extends Object> records) {
    this.logger.debug("Deleting records from database.");
    this.logger.debug(this.toString());
    return this.database.delete(records);
  }

  public void delete(final Object record) {
    this.logger.debug("Deleting record from database.");
    this.logger.debug(this.toString());
    this.database.delete(record);
  }

  public List<? extends Object> find(final Object record) {
    this.logger.debug("Attempting to return records matching an example.");
    this.logger.debug(record.toString());
    final ExampleExpression expression = this.database.getExpressionFactory().exampleLike(record, true, LikeType.EQUAL_TO);
    return this.database.find(record.getClass()).where().add(expression).findList();
  }

  public EbeanServer getEbeanServer() {
    return this.database;
  }

  public List<? extends Object> list(final Class<?> record) {
    this.logger.debug("Attempting to return records matching the class: " + record.getName());
    this.logger.debug(record.toString());
    return this.database.find(record).findList();
  }

  public List<? extends Object> list(final Object record) {
    this.logger.debug("Attempting to return records matching an example.");
    this.logger.debug(record.toString());
    final ExampleExpression expression = this.database.getExpressionFactory().exampleLike(record, true, LikeType.EQUAL_TO);
    return this.database.find(record.getClass()).where().add(expression).findList();
  }

  public int save(final List<? extends Object> records) {
    this.logger.debug("Saving records to database.");
    return this.database.save(records);
  }

  public void save(final Object record) {
    this.logger.debug("Saving record to database.");
    this.logger.debug(record.toString());
    this.database.save(record);
  }

}
