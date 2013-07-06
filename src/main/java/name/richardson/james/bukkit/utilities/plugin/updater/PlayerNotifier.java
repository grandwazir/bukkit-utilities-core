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
package name.richardson.james.bukkit.utilities.plugin.updater;

import java.util.ResourceBundle;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import name.richardson.james.bukkit.utilities.formatters.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.listener.AbstractListener;

/**
 * The PlayerNotifier is responsible for notifying players which a specific permission that there is an update
 * available for the plugin. The players will be notified when they join the server. The permission required for players
 * to receive the notice is the name of the plugin in lowercase.
 */
public class PlayerNotifier extends AbstractListener {

	private static final String RESOURCE_BUNDLE_NAME = ResourceBundles.MESSAGES.getBundleName();
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);

	private final String permission;
	private final String pluginName;
	private final String version;

	public PlayerNotifier(String pluginName, String version) {
		super(null, null);
		this.pluginName = pluginName;
		this.permission = pluginName.toLowerCase();
		this.version = version;
	}

	/**
	 * Notify the player logging of the available update if they have the required permission.
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		final boolean notify = event.getPlayer().hasPermission(this.permission);
		if (notify) {
			//LocalisedCommandSender localisedPlayer = new LocalisedCommandSender(event.getPlayer(), LOCALISATION);
			//localisedPlayer.header("new-version-available", this.pluginName, this.version);
		}
	}

}
