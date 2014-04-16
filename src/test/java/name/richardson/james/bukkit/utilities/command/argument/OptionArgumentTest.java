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

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OptionArgumentTest extends AbstractArgumentTest {

	@Before
	public void setup() {
		this.setArgument(new OptionArgument(getCommandMetadata(), getSuggester()));
	}

	@Override
	@Test(expected = InvalidArgumentException.class)
	public void shouldParseArgumentWhenNonExistantCorrectly() {
		getArgument().parseValue("-f");
	}

	@Override
	@Test(expected = InvalidArgumentException.class)
	public void shouldParseArgumentWithNoParametersCorrectly() {
		getArgument().parseValue("-p");
	}

	@Override
	public void shouldParseArgumentWithSingleParameterCorrectly() {
		final String playerName = "grandwazir";
		getArgument().parseValue("-p:" + playerName);
		assertEquals("Value has not been set correctly!", playerName, getArgument().getString());
	}

	@Override
	public void shouldParseArgumentWithMultipleParametersCorrectly() {
		final String playerNames = "grandwazir,frank,joe";
		final String[] values = StringUtils.split(playerNames, ",");
		getArgument().parseValue("-p:" + playerNames);
		for (String value : values) {
			assertTrue("Collection does not contain a value which should be set!", getArgument().getStrings().contains(value));
		}
	}

}
