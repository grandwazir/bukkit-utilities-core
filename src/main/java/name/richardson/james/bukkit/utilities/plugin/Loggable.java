package name.richardson.james.bukkit.utilities.plugin;

import name.richardson.james.bukkit.utilities.internals.Logger;

public interface Loggable {

  public Logger getLogger();
  
  public String getLoggerPrefix();
  
  public void setLoggerPrefix();
  
  public boolean isDebugging();
  
  public void setDebugging();
  
}
