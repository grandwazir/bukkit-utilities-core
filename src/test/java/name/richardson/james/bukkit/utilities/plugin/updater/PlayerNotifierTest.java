package name.richardson.james.bukkit.utilities.plugin.updater;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA. User: james Date: 13/07/13 Time: 16:17 To change this template use File | Settings | File Templates.
 */
public class PlayerNotifierTest extends TestCase {

	private PlayerNotifier notifer;
	private Player player;
	private Plugin plugin;
	private PluginManager pluginManager;
	private PluginUpdater updater;

	@Test
	public void testNotifyOnPlayerJoin()
	throws Exception {
		PlayerJoinEvent event = new PlayerJoinEvent(player, "");
		when(player.hasPermission(anyString())).thenReturn(true);
		when(updater.isNewVersionAvailable()).thenReturn(true);
		when(updater.getRemoteVersion()).thenReturn("1.0.0");
		notifer.onPlayerJoin(event);
		verify(player).sendMessage("§dA new version of §btest§d (§b1.0.0§d) is available.");
	}

	@Test
	public void testOnPlayerJoin()
	throws Exception {
		when(player.hasPermission(anyString())).thenReturn(false);
		PlayerJoinEvent event = new PlayerJoinEvent(player, "");
		notifer.onPlayerJoin(event);
		verify(player, never()).sendMessage(anyString());
	}

	@Before
	public void setUp() {
		updater = mock(PluginUpdater.class);
		plugin = mock(Plugin.class);
		pluginManager = mock(PluginManager.class);
		player = mock(Player.class);
		when(plugin.getName()).thenReturn("test");
		notifer = new PlayerNotifier(plugin, pluginManager, updater);
	}
}
