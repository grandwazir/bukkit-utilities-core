package name.richardson.james.bukkit.utilities.updater;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import name.richardson.james.bukkit.utilities.listener.AbstractListener;
import name.richardson.james.bukkit.utilities.localisation.LocalisedCommandSender;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 16/06/13
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
public class PlayerNotifier extends AbstractListener {

	private final String permission;
	private final String pluginName;
	private final String version;

	public PlayerNotifier(Plugin plugin, String version) {
		super(plugin);
		this.pluginName = plugin.getName();
		this.permission = plugin.getName().toLowerCase();
		this.version = version;
	}

	public PlayerNotifier(String pluginName, String version) {
		super(pluginName);
		this.pluginName = pluginName;
		this.permission = pluginName.toLowerCase();
		this.version = version;
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		final boolean notify = event.getPlayer().hasPermission(this.permission);
		if (notify) {
			LocalisedCommandSender localisedPlayer = new LocalisedCommandSender(event.getPlayer(),
				ResourceBundles.UTILITIES);
			localisedPlayer.send("notice.new-version-available", this.pluginName, this.version);
		}
	}

}
