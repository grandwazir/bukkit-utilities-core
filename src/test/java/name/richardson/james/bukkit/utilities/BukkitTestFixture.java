/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 BukkitTestFixture.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import org.apache.commons.lang.RandomStringUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BukkitTestFixture {

	public static Server getServer() {
		return null;
	}

	public static void setServer(Server server) {
		return;
	}

	public static Player[] getOnlinePlayers(int number) {
		int count;
		List<Player> players = new ArrayList<Player>();
		for (count = 0; count < number; count++) {
			Player player = mock(Player.class);
			when(player.getName()).thenReturn(RandomStringUtils.randomAlphanumeric(8));
			players.add(player);
		}
		return players.toArray(new Player[players.size()]);
	}

	public static OfflinePlayer[] getOfflinePlayers(int number) {
		int count;
		List<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
		for (count = 0; count < number; count++) {
			OfflinePlayer player = mock(OfflinePlayer.class);
			when(player.getName()).thenReturn(RandomStringUtils.randomAlphanumeric(8));
			players.add(player);
		}
		return players.toArray(new OfflinePlayer[players.size()]);
	}

}
