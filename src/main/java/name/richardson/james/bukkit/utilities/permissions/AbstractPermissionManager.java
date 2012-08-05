package name.richardson.james.bukkit.utilities.permissions;

import name.richardson.james.bukkit.utilities.logging.Logger;
import name.richardson.james.bukkit.utilities.plugin.Plugin;

public abstract class AbstractPermissionManager implements PermissionManager {

  private final Logger logger;

  public AbstractPermissionManager(final Plugin plugin) {
    this.logger = plugin.getCustomLogger();
  }

  public Logger getLogger() {
    return this.logger;
  }

}
