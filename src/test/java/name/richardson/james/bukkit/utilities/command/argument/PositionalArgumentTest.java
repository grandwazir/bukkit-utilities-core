/*
 * Copyright (c) 2014 James Richardson.
 *
 * PositionalArgumentTest.java is part of BukkitUtilities.
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

import static org.junit.Assert.assertEquals;

public class PositionalArgumentTest extends AbstractArgumentTest {

	@Override
	protected String getName() {
		return "player";
	}

	@Override
	protected String getDescription() {
		return "the name of the player";
	}

	@Override
	protected Class<?> getType() {
		return String.class;
	}

	@Override
	@Test
	public void shouldParseOptionWhenNonExistantCorrectly() {
		getArgument().parseValue("test");
		assertEquals("Value has not been set correctly!", String.valueOf(Boolean.FALSE), getArgument().getString());
	}

	@Override
	@Test
	public void shouldParseOptionsWithNoArgumentsCorrectly() {
		getArgument().parseValue("");
		assertEquals("Value has not been set correctly!", String.valueOf(Boolean.FALSE), getArgument().getString());
	}

	@Override
	@Test
	public void shouldParseValueCorrectly() {
		getArgument().parseValue("test one");
		assertEquals("Value has not been set correctly!", "one", getArgument().getString());
	}

	@Before
	public void setup() {
		setArgument(new PositionalArgument(getName(), getDescription(), getType(), 1));
	}

}
