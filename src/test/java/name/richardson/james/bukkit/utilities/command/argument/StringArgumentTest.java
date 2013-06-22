/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 StringArgumentTest.java is part of bukkit-utilities.

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

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class StringArgumentTest extends TestCase {

	private static String VALUE = "HELLO";

	private StringArgument argument;

	@Before
	public void setUp() {
		this.argument = new StringArgument();
	}

	@Test
	public void testGetValue()
	throws Exception {
		this.testParseValue();
		Assert.assertTrue("Returned value is inconsistent", this.argument.getValue().contentEquals(VALUE));
		Assert.assertEquals("Returned value is not a String!", String.class, this.argument.getValue().getClass());
	}

	@Test
	public void testParseValue()
	throws Exception {
		this.argument.parseValue("HELLO");
	}

	@Test
	public void testSetCaseInsensitive()
	throws Exception {
		this.argument.setCaseInsensitive(true);
		this.testParseValue();
		Assert.assertTrue("Returned value is not in lower case!", this.argument.getValue().contentEquals(VALUE.toLowerCase()));
	}

	@Test(expected = InvalidArgumentException.class)
	public void testIsRequired()
	throws Exception {
		this.testSetRequired();
		Assert.assertTrue("Argument is not required!", argument.isRequired());
		this.argument.parseValue(null);
	}

	@Test
	public void testSetRequired()
	throws Exception {
		this.argument.setRequired(true);
	}
}
