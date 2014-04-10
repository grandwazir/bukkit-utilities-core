/*
 * Copyright (c) 2014 James Richardson.
 *
 * RequiredPositionalArgumentTest.java is part of BukkitUtilities.
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

public class RequiredPositionalArgumentTest extends PositionalArgumentTest {

	@Override
	@Test(expected = InvalidArgumentException.class)
	public void shouldParseOptionWhenNonExistantCorrectly() {
		super.shouldParseOptionWhenNonExistantCorrectly();
	}

	@Override
	@Test(expected = InvalidArgumentException.class)
	public void shouldParseOptionsWithNoArgumentsCorrectly() {
		super.shouldParseOptionsWithNoArgumentsCorrectly();
	}

	@Before
	public void setup() {
		setArgument(new RequiredPositionalArgument(getName(), getDescription(), getType(), 1));
	}

}
