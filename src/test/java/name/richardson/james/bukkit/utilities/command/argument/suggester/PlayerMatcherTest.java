/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PlayerMatcherTest.java is part of BukkitUtilities.

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

package name.richardson.james.bukkit.utilities.command.argument.suggester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import junit.framework.TestCase;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class PlayerMatcherTest extends TestCase {

	private Suggester suggester;
	private Server server;

	@Test
	public void matches_WhenMatchesAvailable_ReturnSet() {
		setPlayers();
		assertFalse(getSuggester().suggestValue("").isEmpty());
	}

	@Test
	public void matches_WhenMatchesReturned_MaxSetSizeShouldBe50() {
		setPlayers();
		assertTrue(getSuggester().suggestValue("").size() <= 50);
	}

	@Test
	public void matches_WhenArgumentPassed_ReturnOnlyValuesThatStartWithValue() {
		Player[] players = getPlayers();
		String playerName = players[0].getName().substring(0,4);
		for(String matchedName : getSuggester().suggestValue(playerName)) {
			assertTrue(matchedName.toLowerCase().startsWith(playerName.toLowerCase()));
		}
	}

	protected Server getServer() {
		return server;
	}

	protected abstract Player[] setPlayers();

	@Test
	public void matches_WhenNoMatchAvailable_ReturnEmptySet() {
		assertEquals(getSuggester().suggestValue(""), Collections.EMPTY_SET);
	}

	protected Player[] getPlayers() {
		List<Player> playerList = new ArrayList<Player>();
		do {
			Player player = mock(Player.class);
			when(player.getName()).thenReturn(RandomStringUtils.randomAlphanumeric(8));
			playerList.add(player);
		} while (playerList.size() != 55);
		return playerList.toArray(new Player[55]);
	}

	protected Suggester getSuggester() {
		return suggester;
	}

	protected void setSuggester(Suggester suggester) {
		this.suggester = suggester;
	}

	protected void setServer(Server server) {
		this.server = server;
	}

	@Test
	public void checkToStringOverriden() {
		assertTrue("toString has not been overridden", suggester.toString().contains("PlayerMatcher"));
	}

	@Before
	public void setUp() {
		setServer(mock(Server.class, RETURNS_SMART_NULLS));
	}
}
