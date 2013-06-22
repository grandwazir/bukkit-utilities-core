/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CommandArgumentTest.java is part of bukkit-utilities.

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

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class CommandArgumentTest extends TestCase {

	private CommandArgument argument;

	@Before
	public void setUp()
	throws Exception {
		argument = new CommandArgument();
	}

	@Test(expected = InvalidArgumentException.class)
	public void testGetValue()
	throws Exception {
		this.testSetCommands();
		argument.parseValue("check");
		Assert.assertTrue(argument.getValue().equals("check"));
		argument.setRequired(true);
		argument.parseValue("che");
		argument.getValue();
	}

	@Test
	public void testSetCommands()
	throws Exception {
		Set<String> commands = new TreeSet<String>(Arrays.asList("check", "ban", "fred"));
		CommandArgument.setCommands(commands);
	}

	@Test
	public void testGetMatches()
	throws Exception {
		this.testSetCommands();
		junit.framework.Assert.assertTrue("List does not contain check!", argument.getMatches("check").contains("check"));
		junit.framework.Assert.assertTrue("List does not contain ban!", argument.getMatches("BaN").contains("ban"));
		junit.framework.Assert.assertTrue("List is not complete!", argument.getMatches("").size() == 3);
	}

}
