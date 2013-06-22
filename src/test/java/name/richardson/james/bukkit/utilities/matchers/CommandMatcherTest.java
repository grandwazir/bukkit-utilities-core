/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CommandMatcherTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.matchers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import name.richardson.james.bukkit.utilities.command.Command;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class CommandMatcherTest extends TestCase {

	private static final String[] COMMAND_NAMES = {"help", "check", "test"};
	private final Map<String, Command> commands = new HashMap<String, Command>();

	@Before
	public void setUp()
	throws Exception {
		this.commands.clear();
		for (String name : COMMAND_NAMES) {
			Command command = EasyMock.createNiceMock(Command.class);
			EasyMock.expect(command.getName()).andStubReturn(name);
			EasyMock.replay(command);
			this.commands.put(name, command);
		}
		CommandMatcher.setCommands(commands);
	}

	@Test
	public void testGetMatches()
	throws Exception {
		CommandMatcher matcher = new CommandMatcher(commands);
		List<String> matches = matcher.getMatches("hel");
		Assert.assertTrue("List does not contain 'help'", matches.contains("help"));
		Assert.assertTrue(matches.size() == 1);
	}

	@Test
	public void testGetBlankMatches()
	throws Exception {
		CommandMatcher matcher = new CommandMatcher(commands);
		List<String> matches = matcher.getMatches("");
		Assert.assertTrue("List does not contain 'help'", matches.contains("help"));
		Assert.assertTrue("List does not contain 'check'", matches.contains("check"));
		Assert.assertTrue("List does not contain 'test'", matches.contains("test"));
		Assert.assertTrue(matches.size() == 3);
	}

	public void testCaseInsensitiveMatch()
	throws Exception {
		CommandMatcher matcher = new CommandMatcher(commands);
		List<String> matches = matcher.getMatches("HEL");
		Assert.assertTrue("List does not contain 'help'", matches.contains("help"));
		Assert.assertTrue(matches.size() == 1);
	}

	public void testAlphabeticalOrder() {
		CommandMatcher matcher = new CommandMatcher(commands);
		List<String> matches = matcher.getMatches("");
		Assert.assertTrue("First element is not 'check'", matches.get(0).equalsIgnoreCase("check"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCommandsMustNotBeNull()
	throws Exception {
		CommandMatcher.setCommands(null);
		new CommandMatcher(null);
	}
}
