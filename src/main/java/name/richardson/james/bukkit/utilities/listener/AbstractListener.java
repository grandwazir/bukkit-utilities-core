package name.richardson.james.bukkit.utilities.listener;

import org.bukkit.plugin.Plugin;

public class AbstractListener implements Listener {

  public AbstractListener(Plugin plugin) {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }
  
}
