/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * Logger.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.logging;

import java.util.logging.Level;


public interface Logger {

  /** The Constant DEBUG_LEVEL. */
  public final static Level DEBUG_LEVEL = Level.ALL;

  /** The Constant DEFAULT_LEVEL. */
  public final static Level DEFAULT_LEVEL = Level.INFO;

  /**
   * Record a configuration message to the log.
   *
   * @param message the message
   */
  public void config(String message);

  /**
   * Record a debug message to the log with the SimpleName of the object
   * provided as a prefix.
   *
   * @param object the object
   * @param message the message
   */
  public void debug(Object object, String message);

  /**
   * Record a debug message to the log.
   *
   * @param message the message
   */
  public void debug(String message);

  /**
   * Gets the prefix that is applied at the start of all messages issued from
   * this logger.
   * 
   * @return the prefix
   */
  public String getPrefix();

  /**
   * Record a informative message to the log.
   * 
   * @param message
   */
  public void info(String message);

  /**
   * Checks if we are logging debug messages.
   * 
   * @return true, if is debugging
   */
  public boolean isDebugging();

  /**
   * Sets if we should record debugging messages.
   *
   * @param value the new debugging
   */
  public void setDebugging(boolean value);

  /**
   * Sets the prefix that is applied at the start of all messages issued from
   * this logger.
   * 
   * @param prefix
   */
  public void setPrefix(String prefix);

  /**
   * Record a error to the log.
   *
   * @param message
   */
  public void severe(String message);

  /**
   * Record a warning to the log.
   * 
   * @param message
   */
  public void warning(String message);

}
