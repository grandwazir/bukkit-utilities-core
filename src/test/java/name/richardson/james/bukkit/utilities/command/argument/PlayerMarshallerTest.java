/*
 * Copyright (c) 2014 James Richardson.
 *
 * PlayerMarshallerTest.java is part of BukkitUtilities.
 *
 * bukkit-utilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * bukkit-utilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * bukkit-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.command.argument;

import java.util.Arrays;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class PlayerMarshallerTest {

	private Argument argument;
	private PlayerMarshaller marshaller;
	private Server server;

	@Test
	public void shouldReturnPlayerWhenArgumentIsSet() {
		Player player = mock(Player.class);
		when(argument.getString()).thenReturn("grandwazir");
		when(server.getPlayerExact("grandwazir")).thenReturn(player);
		assertEquals("Argument should return the player when value is set", player, marshaller.getPlayer());
	}

	@Test
	public void shouldReturnPlayersWhenArgumentsAreSet() {
		Player player = mock(Player.class);
		when(argument.getStrings()).thenReturn(Arrays.asList("grandwazir", "sergeant_subtle"));
		when(server.getPlayerExact(anyString())).thenReturn(player);
		// The reason for checking for only 1 here is because the underlying collection is a set and will remove the second mocked player instance
		assertTrue("Argument should return two players in a collection", marshaller.getPlayers().size() == 1);
		verify(server, times(2)).getPlayerExact(anyString());
	}

	@Test
	public void shouldReturnNullWhenArgumentIsNull() {
		when(argument.getString()).thenReturn(null);
		assertNull("Argument should return null when no value is set", marshaller.getPlayer());
	}

	@Before
	public void setup() {
		argument = mock(Argument.class);
		server = mock(Server.class);
		marshaller = new PlayerMarshaller(argument, server);
	}
}
