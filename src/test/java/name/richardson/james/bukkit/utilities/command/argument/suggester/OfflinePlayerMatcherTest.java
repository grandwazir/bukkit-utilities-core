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

package name.richardson.james.bukkit.utilities.command.argument.suggester;

import org.bukkit.entity.Player;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class OfflinePlayerMatcherTest extends PlayerMatcherTest {

	@Before
	public void setUp() {
		super.setUp();
		setSuggester(new OfflinePlayerSuggester(getServer()));
	}

	protected Player[] setPlayers() {
		Player[] players = getPlayers();
		when(getServer().getOfflinePlayers()).thenReturn(players);
		return players;
	}

	@Test
	public void checkToStringOverriden() {
		assertTrue("toString has not been overridden", getSuggester().toString().contains("OfflinePlayerSuggester"));
	}

}
