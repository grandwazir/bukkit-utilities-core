package name.richardson.james.bukkit.utilities.updater;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class PlayerNotifierTest {

	private PlayerJoinEvent event;
	private PlayerNotifier listener;
	@Mock
	private Player player;
	@Mock
	private Plugin plugin;
	@Mock
	private PluginManager pluginManager;
	@Mock
	private PluginUpdater pluginUpdater;
	@Mock
	private Version version;

	@Before
	public void setUp()
	throws Exception {
		MockitoAnnotations.initMocks(this);
		when(pluginUpdater.getName()).thenReturn("BanHammer");
		when(pluginUpdater.isNewVersionAvailable()).thenReturn(true);
		when(pluginUpdater.getLatestRemoteVersion()).thenReturn(version);
		event = new PlayerJoinEvent(player, null);
		listener = new PlayerNotifier(plugin, pluginManager, pluginUpdater);
	}

	@Test
	public void shouldNotifyPlayerOfAvailableUpdate() {
		when(player.hasPermission("banhammer")).thenReturn(true);
		listener.onPlayerJoin(event);
		verify(player).sendMessage(anyString());
	}

	@Test
	public void shouldNotNotifyPlayerOfAvailableUpdate() {
		listener.onPlayerJoin(event);
		verify(player, never()).sendMessage(anyString());
	}

}
