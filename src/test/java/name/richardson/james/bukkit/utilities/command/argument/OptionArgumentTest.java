/*
 * Copyright (c) 2014 James Richardson.
 *
 * OptionArgumentTest.java is part of BukkitUtilities.
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

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OptionArgumentTest {

	private static final String ID = "p";
	private static final String NAME = "player";

	private Argument argument;

	@Test
	public void shouldBeTheLastArgument() {
		assertTrue("Argument is the last one supplied and so should be true.", argument.isLastArgument("-" + ID + ":"));
	}

	@Test
	public void shouldNotBeTheLastArgument() {
		assertFalse("Argument is not the last one supplied and so should be false.", argument.isLastArgument("-" + ID + ":test frank"));
	}

	@Test
	public void shouldBeNullWhenOptionIsNotPresent() {
		argument.parseValue("");
		assertNull("Returned value should be null when argument not present.", argument.getString());
	}

	@Test
	public void shouldBeNullWhenOptionIsPresentButWithoutData() {
		argument.parseValue("-" + ID +":");
		assertNull("Returned value should be null when argument is present but without data.", argument.getString());
	}

	@Test
	public void shouldReturnAllDataWhenOptionIsPresentWithData() {
		final String name = "frank";
		argument.parseValue("-" + NAME + ":" + name + "," + name);
		for (String value : argument.getStrings()) {
			assertEquals("Returned value should equal passed data when argument is present.", name, value);
		}
	}

	@Before
	public void setUp()
	throws Exception {
		ArgumentMetadata metadata = mock(ArgumentMetadata.class);
		when(metadata.getId()).thenReturn(ID);
		when(metadata.getName()).thenReturn(NAME);
		Suggester suggester = mock(Suggester.class);
		argument = new OptionArgument(metadata, suggester);
	}
}
