package name.richardson.james.bukkit.utilities.plugin;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.logging.Logger;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.updater.Updatable;

public interface Plugin extends org.bukkit.plugin.Plugin, Updatable {

  public Localisation getLocalisation();
  
  public Logger getCustomLogger();
 
  public PermissionManager getPermissionManager();
  
  
}
