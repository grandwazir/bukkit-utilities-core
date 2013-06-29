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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import org.easymock.EasyMock;

public class BukkitTestFixture {

	public static Server getServer() {
		Server server = EasyMock.createMock(Server.class);
		EasyMock.expect(server.getLogger()).andReturn(Logger.getLogger("")).anyTimes();
		EasyMock.expect(server.getName()).andReturn("BukkitTestFixture").anyTimes();
		EasyMock.expect(server.getVersion()).andReturn("v1").anyTimes();
		EasyMock.expect(server.getBukkitVersion()).andReturn("v1").anyTimes();
		return server;
	}

	public static void setServer(Server server)
	throws NoSuchFieldException, IllegalAccessException {
		Field field = Bukkit.class.getDeclaredField("server");
		field.setAccessible(true);
		field.set(null, server);
	}

	private static String generateRandomWord(int wordLength) {
		Random r = new Random(); // Intialize a Random Number Generator with SysTime as the seed
		StringBuilder sb = new StringBuilder(wordLength);
		for(int i = 0; i < wordLength; i++) { // For each letter in the word
			char tmp = (char) ('a' + r.nextInt('z' - 'a')); // Generate a letter between a and z
			sb.append(tmp); // Add it to the String
		}
		return sb.toString();
	}

	public static Player[] getOnlinePlayers(int number) {
		int count;
		List<Player> players = new ArrayList<Player>();
		for (count = 0; count < number; count++) {
			Player player = EasyMock.createNiceMock(Player.class);
			EasyMock.expect(player.getName()).andReturn(generateRandomWord(8)).atLeastOnce();
			EasyMock.replay(player);
			players.add(player);
		}
		return players.toArray(new Player[players.size()]);
	}

	public static OfflinePlayer[] getOfflinePlayers(int number) {
		int count;
		List<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
		for (count = 0; count < number; count++) {
			OfflinePlayer player = EasyMock.createNiceMock(OfflinePlayer.class);
			EasyMock.expect(player.getName()).andReturn(generateRandomWord(8)).atLeastOnce();
			EasyMock.replay(player);
			players.add(player);
		}
		return players.toArray(new OfflinePlayer[players.size()]);
	}

}
