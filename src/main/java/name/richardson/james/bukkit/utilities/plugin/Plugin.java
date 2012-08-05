package name.richardson.james.bukkit.utilities.plugin;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.logging.Logger;

public interface Plugin extends org.bukkit.plugin.Plugin {

  public Localisation getLocalisation();
  
  public Logger getCustomLogger();
  
}
