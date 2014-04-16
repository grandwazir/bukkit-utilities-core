/*
 * Copyright (c) 2014 James Richardson.
 *
 * RequiredPlayerMarshallerTest.java is part of BukkitUtilities.
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

import org.bukkit.Server;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class RequiredPlayerMarshallerTest {

	private Argument argument;
	private RequiredPlayerMarshaller marshaller;
	private Server server;

	@Test(expected = InvalidArgumentException.class)
	public void shouldThrowExceptionWhenArgumentIsNull() {
		marshaller.getPlayer();
	}

	@Test(expected = InvalidArgumentException.class)
	public void shouldThrowExceptionWhenCollectionIsEmpty() {
		marshaller.getPlayers();
	}

	@Before
	public void setup() {
		argument = mock(Argument.class);
		server = mock(Server.class);
		marshaller = new RequiredPlayerMarshaller(argument, server);
	}

}
