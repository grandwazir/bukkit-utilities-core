package name.richardson.james.bukkit.utilities.logging;

import java.text.MessageFormat;
import java.util.logging.Level;

import org.bukkit.ChatColor;

public final class ConsoleLogger extends AbstractLogger {

  private final java.util.logging.Logger logger;

  public ConsoleLogger(Object owner) {
    this.logger = java.util.logging.Logger.getLogger(owner.getClass().getName());
    this.logger.setLevel(Logger.DEFAULT_LEVEL);
  }
  
  public ConsoleLogger(java.util.logging.Logger logger) {
    this.logger = logger;
    this.logger.setLevel(Logger.DEFAULT_LEVEL);
  }

  public void config(Object object, String message, Object... elements) {
    if (!this.logger.isLoggable(Level.CONFIG)) return;
    final String formattedMessage = MessageFormat.format(message, elements);
    this.logger.config(ChatColor.stripColor(formattedMessage));
  }

  public void debug(Object object, String message, Object... elements) {
    if (!this.logger.isLoggable(Level.ALL)) return;
    message = "<" + object.getClass().getName() + "> " + message;
    String formattedMessage = MessageFormat.format(message, elements);
    this.logger.config(ChatColor.stripColor(formattedMessage));
  }

  public void info(Object object, String message, Object... elements) {
    if (!this.logger.isLoggable(Level.INFO)) return;
    String formattedMessage = MessageFormat.format(message, elements);
    this.logger.config(ChatColor.stripColor(formattedMessage));
  }

  public boolean isDebugging() {
    return this.logger.isLoggable(Logger.DEBUG_LEVEL);
  }

  public void setDebugging(boolean debugging) {
    if (debugging) {
      this.logger.setLevel(Logger.DEBUG_LEVEL);
    } else {
      this.logger.setLevel(Logger.DEFAULT_LEVEL);
    }
  }
  
  public void severe(Object object, String message, Object... elements) {
    if (!this.logger.isLoggable(Level.SEVERE)) return;
    String formattedMessage = MessageFormat.format(message, elements);
    this.logger.config(ChatColor.stripColor(formattedMessage));
  }

  public void warning(Object object, String message, Object... elements) {
    if (!this.logger.isLoggable(Level.WARNING)) return;
    String formattedMessage = MessageFormat.format(message, elements);
    this.logger.config(ChatColor.stripColor(formattedMessage));
  }

  public String getName() {
    return this.logger.getName();
  }

  
  public void config(String message) {
    if (!this.logger.isLoggable(Level.CONFIG)) return;
    this.logger.config(message);
  }
  

  public void debug(Object object, String message) {
    if (!this.logger.isLoggable(Level.ALL)) return;
    message = "<" + object.getClass().getName() + "> " + message;
    this.logger.config(ChatColor.stripColor(message));
  }
  

  public void info(String message) {
    if (!this.logger.isLoggable(Level.INFO)) return;
    this.logger.info(ChatColor.stripColor(message));
    
  }
  

  public void severe(String message) {
    if (!this.logger.isLoggable(Level.SEVERE)) return;
    this.logger.severe(ChatColor.stripColor(message));
    
  }
  

  public void warning(String message) {
    if (!this.logger.isLoggable(Level.WARNING)) return;
    this.logger.warning(ChatColor.stripColor(message));
  }
  
}
