/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ConsoleLogger.java is part of BukkitUtilities.
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

import java.util.logging.Handler;
import java.util.logging.Level;

public final class ConsoleLogger extends AbstractLogger {

  /** The logger. */
  private final java.util.logging.Logger logger;

  /**
   * Instantiates a new console logger.
   *
   * @param logger
   */
  public ConsoleLogger(final java.util.logging.Logger logger) {
    this.logger = logger;
    this.logger.setLevel(Logger.DEFAULT_LEVEL);
  }

  /**
   * Instantiates a new console logger with a name matching the SimpleName of the object provided.
   *
   * @param owner
   */
  public ConsoleLogger(final Object owner) {
    this.logger = java.util.logging.Logger.getLogger(owner.getClass().getName());
    this.logger.setLevel(Logger.DEFAULT_LEVEL);
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.logging.Logger#config(java.lang.String)
   */
  public void config(String message) {
    if (this.logger.isLoggable(Level.CONFIG)) {
      this.logger.config(message);
    }
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.logging.Logger#debug(java.lang.Object, java.lang.String)
   */
  public void debug(Object object, String message) {
    if (this.logger.isLoggable(Level.ALL)) {
      this.logger.fine(this.getDebugPrefix(object) + message);
    }
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.logging.Logger#debug(java.lang.String)
   */
  public void debug(String message) {
    if (this.logger.isLoggable(Level.ALL)) {
      this.logger.fine(message);
    }
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return this.logger.getName();
  }

  
  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.logging.Logger#info(java.lang.String)
   */
  public void info(String message) {
    if (this.logger.isLoggable(Level.INFO)) {
      this.logger.info(message);
    }
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.logging.Logger#isDebugging()
   */
  public boolean isDebugging() {
    return this.logger.isLoggable(Logger.DEBUG_LEVEL);
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.logging.Logger#setDebugging(boolean)
   */
  public void setDebugging(final boolean debugging) {
    if (debugging) {
      this.logger.setLevel(Logger.DEBUG_LEVEL);
      for (final Handler handler : this.logger.getParent().getHandlers()) {
        handler.setLevel(Level.ALL);
      }
    } else {
      this.logger.setLevel(Logger.DEFAULT_LEVEL);
    }
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.logging.Logger#severe(java.lang.String)
   */
  public void severe(String message) {
    if (this.logger.isLoggable(Level.SEVERE)) {
      this.logger.severe(message);
    }
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.logging.Logger#warning(java.lang.String)
   */
  public void warning(String message) {
    if (this.logger.isLoggable(Level.WARNING)) {
      this.logger.warning(message);
    }
  }

  /**
   * Gets the additional prefix for debug messages.
   *
   * @param object the object
   * @return the debug prefix
   */
  private String getDebugPrefix(final Object object) {
    if (object instanceof Class) {
      final Class<?> c = (Class<?>) object;
      return "<" + c.getSimpleName() + "> ";
    } else {
      return "<" + object.getClass().getSimpleName() + "> ";
    }
  }

}
