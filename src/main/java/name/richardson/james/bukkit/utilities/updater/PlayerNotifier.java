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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.vityuk.ginger.Localization;
import com.vityuk.ginger.LocalizationBuilder;

import name.richardson.james.bukkit.utilities.listener.AbstractListener;
import name.richardson.james.bukkit.utilities.localisation.LocalisedMessages;

/**
 * The PlayerNotifier is responsible for notifying players which a specific permission that there is an update available for the plugin. The players will be
 * notified when they join the server. The permission required for players to receive the notice is the name of the plugin in lowercase.
 */
public class PlayerNotifier extends AbstractListener {

	private static final Localization LOCALISATION = new LocalizationBuilder().withResourceLocation("classpath:bukkit-utilities-core.properties").build();
	private static final LocalisedMessages MESSAGES = LOCALISATION.getLocalizable(LocalisedMessages.class);
	private final String permission;
	private final PluginUpdater updater;

	public PlayerNotifier(Plugin plugin, PluginManager pluginManager, PluginUpdater updater) {
		super(plugin, pluginManager);
		this.updater = updater;
		String pluginName = updater.getName();
		permission = pluginName.toLowerCase();
	}

	/**
	 * Notify the player logging of the available update if they have the required permission.
	 *
	 * @param event
	 */
	@SuppressWarnings("PublicMethodNotExposedInInterface")
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		boolean hasPermission = player.hasPermission(permission);
		if (hasPermission && updater.isNewVersionAvailable()) {
			Version remoteVersion = updater.getLatestRemoteVersion();
			String message = MESSAGES.updateAvailable(updater.getName(), remoteVersion.toString());
			player.sendMessage(message);
		}
	}

}