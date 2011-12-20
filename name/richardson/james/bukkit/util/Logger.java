/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
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

package name.richardson.james.bukkit.util;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;

public final class Logger {

  private static final Level DEBUG_LEVEL = Level.FINE;
  private static final java.util.logging.Logger parentLogger = java.util.logging.Logger.getLogger("Minecraft");
  private static final Set<Logger> registeredLoggers = new HashSet<Logger>();

  private static boolean debugging = false;

  private String prefix = "";
  private final java.util.logging.Logger logger;

  /**
   * Create a new logger with the specified name.
   * 
   * @param className
   * The name of the logger, should be the class it belongs to.
   */
  public Logger(final Class<?> parentClass) {
    this.logger = java.util.logging.Logger.getLogger(parentClass.getName());
    this.logger.setParent(Logger.parentLogger);
    Logger.registeredLoggers.add(this);
    if (Logger.debugging) {
      this.setDebugging(true);
    }
  }

  /**
   * Enable debugging for all loggers.
   * 
   * This basically sets all parentHandlers to a lower log level to ensure
   * that messages are correctly logged. All newly created and existing loggers
   * will also have debugging enabled.
   */
  public static void enableDebugging() {
    Logger.debugging = true;
    for (final Handler handler : Logger.parentLogger.getHandlers()) {
      handler.setLevel(Logger.DEBUG_LEVEL);
    }
    for (final Logger logger : Logger.registeredLoggers) {
      logger.setDebugging(true);
    }
  }

  /**
   * Get the prefix that goes in front of all the messages for all loggers.
   * 
   * @return The prefix used.
   */
  public String getPrefix() {
    return new String(this.prefix);
  }

  /**
   * Set the prefix that goes in front of all the messages for all loggers.
   * 
   * @param prefix
   * The prefix to use for all loggers.
   */
  public void setPrefix(final String prefix) {
    this.prefix = prefix;
  }

  /**
   * Log a configuration message with this logger.
   * 
   * @param message
   * The string that you wish to log.
   */
  public void config(final String message) {
    this.logger.config(prefix + "<" + this.logger.getName() + "> " + message);
  }

  /**
   * Log a debug message with this logger.
   * 
   * @param message
   * The string that you wish to log.
   */
  public void debug(final String message) {
    this.logger.fine(prefix + "<" + this.logger.getName() + "> " + message);
  }

  /**
   * Log a general message with this logger.
   * 
   * @param message
   * The string that you wish to log.
   */
  public void info(final String message) {
    this.logger.info(prefix + message);
  }

  /**
   * Check to see if the logger is logging debug messages.
   * 
   * @return isDebugging true if it is logging debug messages, false otherwise.
   */
  public boolean isDebugging() {
    return this.logger.isLoggable(Logger.DEBUG_LEVEL);
  }

  /**
   * Set if a logger should be logging debug messages or not.
   * 
   * @param setDebugging
   * true if it is should log messages, false otherwise.
   */
  public void setDebugging(final Boolean value) {
    this.logger.setLevel(Logger.DEBUG_LEVEL);
  }

  /**
   * Log a severe (fatal) message with this logger.
   * 
   * @param message
   * The string that you wish to log.
   */
  public void severe(final String message) {
    this.logger.severe(prefix + message);
  }

  /**
   * Log a warning message with this logger.
   * 
   * @param message
   * The string that you wish to log.
   */
  public void warning(final String message) {
    this.logger.warning(prefix + message);
  }

}
