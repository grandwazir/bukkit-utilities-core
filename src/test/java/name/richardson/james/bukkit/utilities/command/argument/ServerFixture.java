/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ServerFixture.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.argument;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import static org.easymock.EasyMock.*;

public class ServerFixture {

	public static String[] PLAYER_NAMES = {"grandwazir", "Sergeant_Subtle"};

	public static Server getServer() {
		Server server = createNiceMock(Server.class);
		Player[] players = getPlayers();
		OfflinePlayer[] offlinePlayers = getOfflinePlayers();
		expect(server.getOnlinePlayers()).andReturn(players).atLeastOnce();
		expect(server.getOfflinePlayers()).andReturn(offlinePlayers).atLeastOnce();
		expect(server.getPlayer((String) anyObject())).andReturn(players[0]);
		expect(server.getPlayer((String) anyObject())).andReturn(players[1]);
		expect(server.getPlayer((String) anyObject())).andReturn(null);
		expect(server.getOfflinePlayer((String) anyObject())).andReturn(players[0]);
		replay(server);
		return server;
	}

	public static Player[] getPlayers() {
		List<Player> players = new ArrayList<Player>(PLAYER_NAMES.length);
		for (String playerName : PLAYER_NAMES) {
			Player player = createNiceMock(Player.class);
			expect(player.getName()).andReturn(playerName).atLeastOnce();
			replay(player);
			players.add(player);
		}
		return players.toArray(new Player[players.size()]);
	}

	public static OfflinePlayer[] getOfflinePlayers() {
		List<OfflinePlayer> players = new ArrayList<OfflinePlayer>(PLAYER_NAMES.length);
		for (String playerName : PLAYER_NAMES) {
			OfflinePlayer player = createNiceMock(OfflinePlayer.class);
			expect(player.getName()).andReturn(playerName).atLeastOnce();
			replay(player);
			players.add(player);
		}
		return players.toArray(new OfflinePlayer[players.size()]);
	}

}
