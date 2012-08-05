package name.richardson.james.bukkit.utilities.persistence;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.logging.Logger;
import name.richardson.james.bukkit.utilities.plugin.Plugin;

public abstract class AbstractStorage implements Storage {

  private final Localisation localisation;
  
  private final Logger logger;

  public AbstractStorage(Plugin plugin) {
    this.localisation = plugin.getLocalisation();
    this.logger = plugin.getCustomLogger();
  }
  
  public Logger getLogger() {
    return logger;
  }
  
  public Localisation getLocalisation() {
    return localisation;
  }

}
