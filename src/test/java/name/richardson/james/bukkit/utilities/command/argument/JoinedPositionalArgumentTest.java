/*
 * Copyright (c) 2014 James Richardson.
 *
 * JoinedPositionalArgumentTest.java is part of BukkitUtilities.
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JoinedPositionalArgumentTest {

	private static final int POSITION = 1;

	private Argument argument;

	@Test
	public void shouldBeNullWhenArgumentIsNotPresent() {
		argument.parseValue("");
		assertNull("Returned value should be null when argument not present.", argument.getString());
	}

	@Test
	public void shouldReturnJoinedArgumentsWhenArgumentIsPresent() {
		argument.parseValue("This is a test argument");
		assertEquals("Returned value should equal passed data when argument is present.", "is a test argument", argument.getString());
	}

	@Before
	public void setup()
	throws Exception {
		ArgumentMetadata metadata = mock(ArgumentMetadata.class);
		argument = new JoinedPositionalArgument(metadata, POSITION);
	}




}
