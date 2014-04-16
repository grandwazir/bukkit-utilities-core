/*
 * Copyright (c) 2014 James Richardson.
 *
 * RequiredJoinedPositionalArgumentTest.java is part of BukkitUtilities.
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

import static org.mockito.Mockito.mock;

public class RequiredJoinedPositionalArgumentTest {

	private static final int POSITION = 1;

	private Argument argument;

	@Test(expected = InvalidArgumentException.class)
	public void shouldThrowExceptionWhenArgumentisNonExistant() {
		argument.parseValue("");
	}

	@Test(expected = InvalidArgumentException.class)
	public void shouldThrowExceptionWhenArgumentHasNoData() {
		argument.parseValue("one ");
	}

	@Before
	public void setup() {
		ArgumentMetadata metadata = mock(ArgumentMetadata.class);
		argument = new RequiredJoinedPositionalArgument(metadata, POSITION);
	}


}
