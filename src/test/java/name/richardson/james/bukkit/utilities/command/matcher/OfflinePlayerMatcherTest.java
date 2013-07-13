/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 OfflinePlayerMatcherTest.java is part of bukkit-utilities.

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

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

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
public class OfflinePlayerMatcherTest extends TestCase {

	private OfflinePlayerMatcher matcher;

	@Mock
	private Server server;

	@Test
	public void testMatches()
	throws Exception {
		OfflinePlayer[] players = getOfflinePlayers(52);
		when(server.getOfflinePlayers()).thenReturn(players);
		matcher = new OfflinePlayerMatcher(server);
		Set<String> matches = matcher.matches("");
		Assert.assertTrue("Expected list size of 50, got " + matches.size(), matches.size() == 50);
		String expectedName = players[0].getName();
		String searchName = players[0].getName().substring(0, 4);
		matches = matcher.matches(searchName);
		Assert.assertTrue("List does not contain expected name! " + expectedName, matches.contains(expectedName));
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
