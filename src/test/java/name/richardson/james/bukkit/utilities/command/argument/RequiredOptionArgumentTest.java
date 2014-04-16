/*
 * Copyright (c) 2014 James Richardson.
 *
 * RequiredOptionArgumentTest.java is part of BukkitUtilities.
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

import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequiredOptionArgumentTest {

	private static final String ID = "p";
	private static final String NAME = "player";
	private Argument argument;

	@Test(expected = InvalidArgumentException.class)
	public void shouldThrowExceptionWhenArgumentisNonExistant() {
		argument.parseValue("");
	}

	@Test(expected = InvalidArgumentException.class)
	public void shouldThrowExceptionWhenArgumentHasNoData() {
		argument.parseValue("-p:");
	}

	@Before
	public void setUp()
	throws Exception {
		ArgumentMetadata metadata = mock(ArgumentMetadata.class);
		when(metadata.getId()).thenReturn(ID);
		when(metadata.getName()).thenReturn(NAME);
		Suggester suggester = mock(Suggester.class);
		argument = new RequiredOptionArgument(metadata, suggester);
	}

}
