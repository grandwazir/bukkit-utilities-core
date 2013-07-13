/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 OnlinePlayerMatcherTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OnlinePlayerMatcherTest extends TestCase {

	private OnlinePlayerMatcher matcher;
	@Mock
	private Server server;

	@Test
	public void testMatches()
	throws Exception {
		matcher = new OnlinePlayerMatcher(server);
		Player[] players = getOnlinePlayers(52);
		when(server.getOnlinePlayers()).thenReturn(players);
		Set<String> matches = matcher.matches("");
		Assert.assertTrue("Expected list size of 50, got " + matches.size(), matches.size() == 50);
		String expectedName = players[0].getName();
		String searchName = players[0].getName().substring(0, 4);
		Assert.assertTrue("List does not contain expected name! " + expectedName, matcher.matches(searchName).contains(expectedName));
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

}
