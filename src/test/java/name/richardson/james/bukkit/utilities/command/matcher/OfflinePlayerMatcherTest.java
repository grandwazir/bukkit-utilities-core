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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.BukkitTestFixture;

public class OfflinePlayerMatcherTest extends TestCase {

	private OfflinePlayerMatcher matcher;

	@Test
	public void testMatches()
	throws Exception {
		Field field = matcher.getClass().getDeclaredField("SERVER");
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		Server server = BukkitTestFixture.getServer();
		OfflinePlayer[] players = BukkitTestFixture.getOfflinePlayers(52);
		EasyMock.expect(server.getOfflinePlayers()).andReturn(players).atLeastOnce();
		EasyMock.replay(server);
		field.set(server, server);
		Assert.assertTrue("Expected list size of 50, got " + matcher.matches(""), matcher.matches("").size() == 50);
		String expectedName = players[0].getName();
		String searchName = players[0].getName().substring(0, 4);
		Assert.assertTrue("List does not contain expected name! " + expectedName, matcher.matches(searchName).contains(expectedName));
	}

	@Before
	public void setUp()
	throws Exception {
		matcher = new OfflinePlayerMatcher();
	}
}
