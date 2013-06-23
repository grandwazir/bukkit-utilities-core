/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractListener.java is part of BukkitUtilities.

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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.utilities.logging.PrefixedLogger;

public class AbstractListener implements Listener {

	private static final Logger logger = PrefixedLogger.getLogger(AbstractListener.class);

	private final Plugin plugin;
	private final PluginManager pluginManager;

	public AbstractListener(final Plugin plugin, final PluginManager pluginManager) {
		this.pluginManager = pluginManager;
		this.plugin = plugin;
	}

	public static Logger getLogger() {
		return logger;
	}

	private void registerListener(final Plugin plugin) {
		logger.log(Level.FINEST, "Registering " + this.getClass().getSimpleName() + " for events,");
		pluginManager.registerEvents(this, plugin);
	}

}
