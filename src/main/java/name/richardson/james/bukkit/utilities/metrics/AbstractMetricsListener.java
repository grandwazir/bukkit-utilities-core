/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * AbstractMetricsListener.java is part of BukkitUtilities.
 * 
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
