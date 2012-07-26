package name.richardson.james.bukkit.utilities.metrics;

import java.io.IOException;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractMetricsListener implements Listener {

  protected Metrics metrics;

  public AbstractMetricsListener(final JavaPlugin plugin) throws IOException {
    this.metrics = new Metrics(plugin);
    this.setupCustomMetrics();
    this.metrics.start();
  }

  protected void setupCustomMetrics() {
    // For downstream plugins to override.
  }

}
