package name.richardson.james.bukkit.utilities.metrics;

import java.io.IOException;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.internals.Logger;

public abstract class AbstractMetricsListener implements Listener {

  /** The logger for the Metrics listener. */
  protected final Logger logger = new Logger(this.getClass());

  /** The metric object used for reporting. */
  protected final Metrics metrics;

  /**
   * Instantiates a new abstract metrics listener.
   * 
   * @param plugin the plugin
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public AbstractMetricsListener(final JavaPlugin plugin) throws IOException {
    this.logger.debug("Starting metrics collection.");
    this.metrics = new Metrics(plugin);
  }

}
