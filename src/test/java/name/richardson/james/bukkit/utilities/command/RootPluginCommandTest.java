/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 RootPluginCommandTest.java is part of bukkit-utilities.

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

import java.util.HashMap;

import org.bukkit.command.CommandSender;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 24/06/13
 * Time: 04:05
 * To change this template use File | Settings | File Templates.
 */
public class RootPluginCommandTest extends TestCase {

	private RootCommand command;
	private CommandSender commandSender;
	private Command mockCommand;
	private HelpCommand mockHelpCommand;

	@Test
	public void testExecuteCommand()
	throws Exception {
		EasyMock.expect(mockCommand.onCommand((CommandSender) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(true).times(1);
		EasyMock.replay(mockCommand);
		command.onCommand(commandSender, "test");
		EasyMock.verify(mockCommand);
	}

	public void testExecuteHelpCommand()
	throws Exception {
		command.onCommand(commandSender, "f");
	}

	@Before
	public void setUp()
	throws Exception {
		commandSender = EasyMock.createMock(CommandSender.class);
		mockCommand = EasyMock.createMock(Command.class);
		mockHelpCommand = EasyMock.createNiceMock(HelpCommand.class);
		HashMap<String, Command> map = new HashMap<String, Command>();
		map.put("test", mockCommand);
		command = new RootPluginCommand(map, mockHelpCommand);
	}
}
