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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class OptionArgumentTest extends AbstractArgumentTest {

	@Before
	public void setup() {
		this.setArgument(new OptionArgument(getName(), getDescription(), getType()));
	}

	@Override
	protected String getName() {
		return "p";
	}

	@Override
	protected String getDescription() {
		return "the name of the player to ban";
	}

	@Override
	protected Class<?> getType() {
		return String.class;
	}

	@Override
	public void shouldParseOptionWhenNonExistantCorrectly() {
		getArgument().parseValue("-f");
		assertEquals("Value has not been set correctly!", String.valueOf(Boolean.FALSE), getArgument().getValue());
	}

	@Override
	public void shouldParseOptionsWithNoArgumentsCorrectly() {
		getArgument().parseValue("-p");
		assertEquals("Value has not been set correctly!", String.valueOf(Boolean.TRUE), getArgument().getValue());
	}

	@Test
	public void shouldParseValueCorrectly() {
		getArgument().parseValue("-p:grandwazir");
		assertEquals("Value has not been set correctly!", "grandwazir", getArgument().getValue());
	}

}
