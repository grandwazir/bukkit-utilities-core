/*
 * Copyright (c) 2014 James Richardson.
 *
 * SwitchArgumentTest.java is part of BukkitUtilities.
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

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SwitchArgumentTest {

	private static final String ID = "s";
	private static final String NAME = "silent";

	private Argument argument;

	@Test
	public void shouldBeTheLastArgument() {
		assertTrue("Argument is the last one supplied and so should be true.", argument.isLastArgument("-" + ID));
	}

	@Test
	public void shouldNotBeTheLastArgument() {
		assertFalse("Argument is not the last one supplied and so should be false.", argument.isLastArgument("-" + ID + " frank"));
	}

	@Test
	public void shouldNeverSuggestValues() {
		assertTrue("This argument should never suggest values as auto completion makes no sense for this implementation.", argument.suggestValue("").isEmpty());
	}

	@Test
	public void shouldBeNullWhenSwitchIsNotPresent() {
		argument.parseValue("");
		assertNull("Returned value should be null when argument not present.", argument.getString());
	}

	@Test
	public void shouldNotBeNullWhenSwitchIsPresentUsingID() {
		argument.parseValue("-" + ID);
		assertNotNull("Returned value should not be null when argument is present.", argument.getString());
	}

	@Test
	public void shouldNotBeNullWhenSwitchIsPresentUsingName() {
		argument.parseValue("-" + NAME);
		assertNotNull("Returned value should not be null when argument is present.", argument.getString());
	}

	@Before
	public void setup() {
		ArgumentMetadata metadata = mock(ArgumentMetadata.class);
		when(metadata.getId()).thenReturn(ID);
		when(metadata.getName()).thenReturn(NAME);
		argument = new SwitchArgument(metadata);
	}

}
