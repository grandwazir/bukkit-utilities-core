package name.richardson.james.bukkit.utilities.logging;

import java.util.logging.Level;

public interface Logger {

  public final static Level DEBUG_LEVEL = Level.ALL;
  
  public final static Level DEFAULT_LEVEL = Level.INFO;
  
  public void config(Object object, String message, Object... elements);
  
  public void debug(Object object, String message, Object... elements);
  
  public String getName();
 
  public String getPrefix();
  
  public void info(Object object, String message, Object... elements);
  
  public boolean isDebugging();
  
  public void setPrefix(String prefix);
  
  public void severe(Object object, String message, Object... elements);
  
  public void warning(Object object, String message, Object... elements);
  
}
