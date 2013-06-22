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

package name.richardson.james.bukkit.utilities.matchers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OfflinePlayerMatcherTest extends TestCase {

	private static final String[] PLAYER_NAMES = {"grandwazir", "Sergeant_Subtle"};

	private Server server;
	private List<OfflinePlayer> players;

	@Before
	public void setUp()
	throws Exception {
		this.server = EasyMock.createNiceMock(Server.class);
		this.players = new ArrayList<OfflinePlayer>();
		for (String name : PLAYER_NAMES) {
			Player player = EasyMock.createNiceMock(Player.class);
			EasyMock.expect(player.getName()).andReturn(name).times(2);
			EasyMock.replay(player);
			players.add(player);
		}
		EasyMock.expect(server.getOfflinePlayers()).andStubReturn(players.toArray(new OfflinePlayer[players.size()]));
		EasyMock.replay(this.server);
	}

	@Test
	public void testPartialMatch()
	throws Exception {
		OfflinePlayerMatcher.setServer(server);
		OfflinePlayerMatcher matcher = new OfflinePlayerMatcher();
		List<String> matches = matcher.getMatches("gran");
		Assert.assertTrue("List does not contain 'grandwazir'", matches.contains("grandwazir"));
		Assert.assertTrue("List is not equal to 1", matches.size() == 1);
	}

	@Test
	public void testExactMatch()
	throws Exception {
		OfflinePlayerMatcher.setServer(server);
		OfflinePlayerMatcher matcher = new OfflinePlayerMatcher();
		List<String> matches = matcher.getMatches("grandwazir");
		Assert.assertTrue("List does not contain 'grandwazir'", matches.contains("grandwazir"));
		Assert.assertTrue("List is not equal to 1", matches.size() == 1);
	}

	@Test
	public void testDoNotMatchBlank()
	throws Exception {
		OfflinePlayerMatcher.setServer(server);
		OfflinePlayerMatcher matcher = new OfflinePlayerMatcher();
		List<String> matches = matcher.getMatches("");
		Assert.assertTrue("List is not equal to 0", matches.size() == 0);
	}

	@Test
	public void testCaseInsensitiveMatch()
	throws Exception {
		OfflinePlayerMatcher.setServer(server);
		OfflinePlayerMatcher matcher = new OfflinePlayerMatcher();
		List<String> matches = matcher.getMatches("SERGEANT_SUBTL");
		Assert.assertTrue("List does not contain 'Sergeant_Subtle'", matches.contains("Sergeant_Subtle"));
		Assert.assertTrue(matches.size() == 1);
	}

	@Test(expected = IllegalStateException.class)
	public void testServerMustBeSet()
	throws Exception {
		OfflinePlayerMatcher.setServer(server);
		OfflinePlayerMatcher matcher = new OfflinePlayerMatcher();
		OfflinePlayerMatcher.setServer(null);
		matcher.getMatches("");
	}

	@Test(expected = IllegalStateException.class)
	public void testServerMustBeSetBeforeConstruction()
	throws Exception {
		OfflinePlayerMatcher.setServer(null);
		new OfflinePlayerMatcherTest();
	}

}
