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

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PositionalArgumentTest extends AbstractArgumentTest {

	@Override
	public void shouldParseArgumentWhenNonExistantCorrectly() {
		getArgument().parseValue("test");
		assertNull("Value should be null!", getArgument().getString());
	}

	@Override
	public void shouldParseArgumentWithMultipleParametersCorrectly() {
		getArgument().parseValue("test one two three");
		List<String> values = Arrays.asList("one","two","three");
		for (String value : values) {
			assertTrue("Collection does not contain a value which should be set (" + value + ")", getArgument().getStrings().contains(value));
		}
	}

	@Override
	public void shouldParseArgumentWithNoParametersCorrectly() {
		getArgument().parseValue("");
		assertNull("Value should be null!", getArgument().getString());
	}

	@Override
	public void shouldParseArgumentWithSingleParameterCorrectly() {
		getArgument().parseValue("test one");
		assertEquals("Value has not been set correctly!", "one", getArgument().getString());
	}

	@Before
	public void setup() {
		setArgument(new PositionalArgument(getCommandMetadata(), getSuggester(), 1));
	}

}
