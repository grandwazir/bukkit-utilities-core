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

import org.bukkit.ChatColor;

import name.richardson.james.bukkit.utilities.localisation.Localisation;

public final class ConsoleLogger extends AbstractLogger {

  private final Localisation localisation;

  private final java.util.logging.Logger logger;

  public ConsoleLogger(final java.util.logging.Logger logger, final Localisation localisation) {
    this.logger = logger;
    this.logger.setLevel(Logger.DEFAULT_LEVEL);
    this.localisation = localisation;
  }

  public ConsoleLogger(final Object owner, final Localisation localisation) {
    this.logger = java.util.logging.Logger.getLogger(owner.getClass().getName());
    this.logger.setLevel(Logger.DEFAULT_LEVEL);
    this.localisation = localisation;
  }

  public void config(final Object object, final String message, final Object... elements) {
    if (!this.logger.isLoggable(Level.CONFIG)) {
      return;
    }
    final String formattedMessage = this.localisation.getMessage(object, message, elements);
    this.logger.config(ChatColor.stripColor(formattedMessage));
  }

  public void debug(final Object object, final String message, final Object... elements) {
    if (!this.logger.isLoggable(Level.ALL)) {
      return;
    }
    final String formattedMessage = this.getDebugPrefix(object) + this.localisation.getMessage(object, message, elements);
    this.logger.fine((ChatColor.stripColor(formattedMessage)));
  }

  public String getName() {
    return this.logger.getName();
  }

  public void info(final Object object, final String message, final Object... elements) {
    if (!this.logger.isLoggable(Level.INFO)) {
      return;
    }
    final String formattedMessage = this.localisation.getMessage(object, message, elements);
    this.logger.info(ChatColor.stripColor(formattedMessage));
  }

  public boolean isDebugging() {
    return this.logger.isLoggable(Logger.DEBUG_LEVEL);
  }

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

  public void severe(final Object object, final String message, final Object... elements) {
    if (!this.logger.isLoggable(Level.SEVERE)) {
      return;
    }
    final String formattedMessage = this.localisation.getMessage(object, message, elements);
    this.logger.severe(ChatColor.stripColor(formattedMessage));
  }

  public void warning(final Object object, final String message, final Object... elements) {
    if (!this.logger.isLoggable(Level.WARNING)) {
      return;
    }
    final String formattedMessage = this.localisation.getMessage(object, message, elements);
    this.logger.warning(ChatColor.stripColor(formattedMessage));
  }

  private String getDebugPrefix(final Object object) {
    if (object instanceof Class) {
      final Class<?> c = (Class<?>) object;
      return "<" + c.getSimpleName() + "> ";
    } else {
      return "<" + object.getClass().getSimpleName() + "> ";
    }
  }

}
