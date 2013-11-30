/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PlayerNotifier.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.updater;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.formatters.DefaultColourFormatter;
import name.richardson.james.bukkit.utilities.listener.AbstractListener;
import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;

/**
 * The PlayerNotifier is responsible for notifying players which a specific permission that there is an update
 * available for the plugin. The players will be notified when they join the server. The permission required for players
 * to receive the notice is the name of the plugin in lowercase.
 */
public class PlayerNotifier extends AbstractListener {

	private final Localisation localisation = new ResourceBundleByClassLocalisation(PlayerNotifier.class);
	private final ColourFormatter colourFormatter = new DefaultColourFormatter();
	private final String permission;
	private final String pluginName;
	private final PluginUpdater updater;

	public PlayerNotifier(Plugin plugin, PluginManager pluginManager, PluginUpdater updater) {
		super(plugin, pluginManager);
		this.pluginName = plugin.getName();
		this.permission = pluginName.toLowerCase();
		this.updater = updater;
	}

	/**
	 * Notify the player logging of the available update if they have the required permission.
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		final boolean notify = event.getPlayer().hasPermission(this.permission);
		if (notify && updater.isNewVersionAvailable()) {
			String key = null;
			switch (updater.getState()) {
				case UPDATE:
					key = "new-version-downloaded";
					break;
				case NOTIFY:
					key = "new-version-available";
					break;
			}
			String message = colourFormatter.format(localisation.getMessage(key), ColourFormatter.FormatStyle.WARNING, pluginName, updater.getRemoteVersion());
			event.getPlayer().sendMessage(message);
		}
	}

}