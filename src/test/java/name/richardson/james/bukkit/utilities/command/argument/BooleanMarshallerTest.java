/*
 * Copyright (c) 2014 James Richardson.
 *
 * BooleanMarshallerTest.java is part of BukkitUtilities.
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
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BooleanMarshallerTest {

	private Argument argument;
	private BooleanMarshaller marshaller;

	@Test
	public void shouldReturnTrueWhenArgumentIsSet() {
		when(argument.getString()).thenReturn("hello");
		assertTrue("Argument should return true when any value is set", marshaller.isSet());
	}

	@Test
	public void shouldReturnFalseWhenArgumentIsNull() {
		when(argument.getString()).thenReturn(null);
		assertFalse("Argument should return false when no value is set", marshaller.isSet());
	}

	@Before
	public void setup() {
		argument = mock(Argument.class);
		marshaller = new BooleanMarshaller(argument);
	}

}
