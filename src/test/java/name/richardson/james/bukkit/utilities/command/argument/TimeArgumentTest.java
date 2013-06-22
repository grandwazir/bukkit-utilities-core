/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TimeArgumentTest.java is part of bukkit-utilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.command.argument;

import java.util.Collections;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class TimeArgumentTest extends TestCase {

	private TimeArgument argument;

	@Before
	public void setUp()
	throws Exception {
		argument = new TimeArgument();
	}

	@Test
	public void testParseValue()
	throws Exception {
		argument.parseValue("1d");
		Assert.assertTrue(argument.getValue() == 86400000);
		argument.parseValue("16h");
		Assert.assertTrue(argument.getValue() == 57600000);
		argument.parseValue("wibble");
		Assert.assertTrue(argument.getValue() == 0);
	}

	@Test
	public void testIsRequired()
	throws Exception {
		testSetRequired();
		argument.parseValue("");
	}

	@Test
	public void testSetRequired()
	throws Exception {
		argument.setRequired(true);
	}

	@Test
	public void testGetMatches()
	throws Exception {
		Assert.assertEquals(argument.getMatches(""), Collections.<String>emptySet());
	}
}
