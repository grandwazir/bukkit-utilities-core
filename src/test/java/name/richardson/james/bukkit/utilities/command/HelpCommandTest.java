/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 HelpCommandTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command;

import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA. User: james Date: 28/06/13 Time: 20:39 To change this template use File | Settings | File Templates.
 */
public class HelpCommandTest extends TestCase {

	private HelpCommand command;

	@Test
	public void testGetArgumentMatches()
	throws Exception {
		CommandSender sender = EasyMock.createMock(CommandSender.class);
		Command nestedCommand = EasyMock.createMock(Command.class);
		EasyMock.expect(nestedCommand.getName()).andReturn("test").atLeastOnce();
		EasyMock.expect(nestedCommand.isAuthorised(sender)).andReturn(true).atLeastOnce();
		EasyMock.replay(nestedCommand);
		command.addCommand(nestedCommand);
		command.execute(new CommandContext(new String[]{""}, sender));
		Set<String> matches = command.getArgumentMatches(new CommandContext(new String[]{"tes"}, sender));
		Assert.assertTrue(matches.contains("test"));
	}

	@Test
	public void testExecuteNoMatch()
	throws Exception {
		CommandSender sender = EasyMock.createMock(CommandSender.class);
		int number = 4;
		int count;
		for (count = 0; count < number; count++) {
			sender.sendMessage((String) EasyMock.anyObject());
			EasyMock.expectLastCall();
		}
		Command nestedCommand = EasyMock.createMock(Command.class);
		EasyMock.expect(nestedCommand.getName()).andReturn("test").atLeastOnce();
		EasyMock.expect(nestedCommand.getDescription()).andReturn("A test command").atLeastOnce();
		EasyMock.expect(nestedCommand.getUsage()).andReturn("<test> [test]").atLeastOnce();
		EasyMock.expect(nestedCommand.isAuthorised(sender)).andReturn(true).atLeastOnce();
		EasyMock.replay(nestedCommand);
		EasyMock.replay(sender);
		command.addCommand(nestedCommand);
		command.execute(new CommandContext(new String[]{""}, sender));
		EasyMock.verify(sender);
	}

	@Test
	public void testExecuteMatch()
	throws Exception {
		CommandSender sender = EasyMock.createMock(CommandSender.class);
		sender.sendMessage((String) EasyMock.anyObject());
		EasyMock.expectLastCall();
		sender.sendMessage((String) EasyMock.anyObject());
		EasyMock.expectLastCall();
		Command nestedCommand = EasyMock.createMock(Command.class);
		EasyMock.expect(nestedCommand.getName()).andReturn("test").atLeastOnce();
		EasyMock.expect(nestedCommand.getDescription()).andReturn("A test command").atLeastOnce();
		EasyMock.expect(nestedCommand.getUsage()).andReturn("<test> [test]").atLeastOnce();
		EasyMock.replay(nestedCommand);
		EasyMock.replay(sender);
		command.addCommand(nestedCommand);
		command.execute(new CommandContext(new String[]{"test"}, sender));
		EasyMock.verify(sender);
	}

	@Test
	public void testAddCommand()
	throws Exception {
		Command nestedCommand = EasyMock.createMock(Command.class);
		EasyMock.expect(nestedCommand.getName()).andReturn("test").atLeastOnce();
		EasyMock.replay(nestedCommand);
		command.addCommand(nestedCommand);
		EasyMock.verify(nestedCommand);
	}

	@Before
	public void setUp()
	throws Exception {
		PluginDescriptionFile pluginDescriptionFile = new PluginDescriptionFile("test", "v1.0", "mainclass");
		command = new HelpCommand("test", pluginDescriptionFile);
	}
}
