/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * Database.java is part of DimensionDoor.
 * 
 * DimensionDoor is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * DimensionDoor is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DimensionDoor. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.util;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExampleExpression;
import com.avaje.ebean.LikeType;

public abstract class Database {

  protected EbeanServer database;
  protected Logger logger = new Logger(this.getClass());
  
  public Database(EbeanServer database) {
    this.database = database;
    this.validate();
  }
  
  public EbeanServer getEbeanServer() {
    return database;
  }
  
  public List<? extends Object> find(Object record) {
    logger.debug("Attempting to return records matching an example.");
    logger.debug(record.toString());
    final ExampleExpression expression = database.getExpressionFactory().exampleLike(record, true, LikeType.EQUAL_TO);
    return database.find(record.getClass()).where().add(expression).findList();
  }
  
  public int count(Object record) {
    logger.debug("Attempting to get row count using an example.");
    logger.debug(record.toString());
    final ExampleExpression expression = database.getExpressionFactory().exampleLike(record, true, LikeType.EQUAL_TO);
    return database.find(record.getClass()).where().add(expression).findRowCount();
  }
  
  public List<? extends Object> list(Object record) {
    logger.debug("Attempting to return records matching an example.");
    logger.debug(record.toString());
    final ExampleExpression expression = database.getExpressionFactory().exampleLike(record, true, LikeType.EQUAL_TO);
    return database.find(record.getClass()).where().add(expression).findList();
  }
  
  public List<? extends Object> list(Class<?> record) {
    logger.debug("Attempting to return records matching the class: " + record.getName());
    logger.debug(record.toString());
    return database.find(record).findList();
  }
  
  public void save(Object record) {
    logger.debug("Saving record to database.");
    logger.debug(record.toString());
    database.save(record);
  }
  
  public int save(List<? extends Object> records) {
    logger.debug("Saving records to database.");
    return database.save(records);
  }
  
  public void delete(Object record) {
    logger.debug("Deleting record from database.");
    logger.debug(this.toString());
    database.delete(record);
  }
  
  public int delete(List<? extends Object> records) {
    logger.debug("Deleting record from database.");
    logger.debug(this.toString());
    return database.delete(records);
  }
  
  public abstract void validate();
  
  public abstract void upgrade(int schema);

}
