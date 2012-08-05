package name.richardson.james.bukkit.utilities.listener;

import name.richardson.james.bukkit.utilities.logging.Logger;
import name.richardson.james.bukkit.utilities.plugin.Plugin;

public class LoggableListener extends AbstractListener {

  private final Logger logger;

  public LoggableListener(Plugin plugin) {
    super(plugin);
    this.logger = plugin.getCustomLogger();
  }
  
  public Logger getLogger() {
    return this.logger;
  }

}
