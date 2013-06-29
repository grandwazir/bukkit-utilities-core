/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 MetricsListener.java is part of BukkitUtilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.listener;

import java.io.IOException;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.mcstats.Metrics;

/**
 * A default implementation of Metrics. The listener self registers and sends basic versioning infomation to mcstats.
 */
public class MetricsListener extends AbstractListener {

	private Metrics metrics;

	public MetricsListener(final Plugin plugin, PluginManager pluginManager) {
		super(plugin, pluginManager);
		try {
			this.metrics = new Metrics(plugin);
			this.metrics.start();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}



}
