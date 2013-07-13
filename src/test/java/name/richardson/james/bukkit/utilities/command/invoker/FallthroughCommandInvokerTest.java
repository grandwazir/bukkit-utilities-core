/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 FallthroughCommandInvokerTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.invoker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;

import name.richardson.james.bukkit.utilities.command.Command;
import name.richardson.james.bukkit.utilities.command.context.NestedCommandContext;
import name.richardson.james.bukkit.utilities.command.context.PassthroughCommandContext;

import static org.mockito.Mockito.*;

public class FallthroughCommandInvokerTest extends TestCase {

	private String[] arguments = {"test"};
	private FallthroughCommandInvoker commandInvoker;
	private Command defaultCommand;
	private Server server;

	@Before
	public void setUp()
	throws Exception {
		defaultCommand = mock(Command.class);
		server = mock(Server.class);
		commandInvoker = new FallthroughCommandInvoker(defaultCommand);
	}

	@After
	public void tearDown()
	throws Exception {
		setServer(null);
	}

	@Test
	public void testAddCommand()
	throws Exception {
		Command nestedCommand = mock(Command.class);
		when(nestedCommand.getName()).thenReturn("test");
		commandInvoker.addCommand(nestedCommand);
		verify(nestedCommand).getName();
	}

	@Test
	public void testAddCommands()
	throws Exception {
		Command nestedCommand = mock(Command.class);
		when(nestedCommand.getName()).thenReturn("test");
		List<Command> commandList = new ArrayList<Command>();
		commandList.add(nestedCommand);
		commandInvoker.addCommands(commandList);
		verify(nestedCommand).getName();
	}

	@Test
	public void testGetCommands()
	throws Exception {
		Assert.assertNotNull("A map of commands should be returned!", commandInvoker.getCommands());
	}

	@Test
	public void testOnCommandMatches()
	throws Exception {
		setServer(server);
		Command nestedCommand = mock(Command.class);
		CommandSender sender = mock(CommandSender.class);
		when(nestedCommand.getName()).thenReturn("test");
		commandInvoker.addCommand(nestedCommand);
		commandInvoker.onCommand(sender, null, null, arguments);
		verify(nestedCommand).execute(Matchers.<NestedCommandContext>anyObject());
	}

	@Test
	public void testOnCommandNoMatch()
	throws Exception {
		setServer(server);
		CommandSender sender = mock(CommandSender.class);
		commandInvoker.onCommand(sender, null, null, arguments);
		verify(defaultCommand).execute(Matchers.<PassthroughCommandContext>anyObject());
	}

	@Test
	public void testOnTabCompleteDefaultCommand()
	throws Exception {
		setServer(server);
		CommandSender sender = mock(CommandSender.class);
		commandInvoker.onTabComplete(sender, null, null, arguments);
		verify(defaultCommand).getArgumentMatches(Matchers.<PassthroughCommandContext>anyObject());
	}

	@Test
	public void testOnTabCompleteNestedCommand()
	throws Exception {
		setServer(server);
		Command nestedCommand = mock(Command.class);
		CommandSender sender = mock(CommandSender.class);
		when(nestedCommand.getName()).thenReturn("test");
		commandInvoker.addCommand(nestedCommand);
		commandInvoker.onTabComplete(sender, null, null, arguments);
		verify(nestedCommand).getArgumentMatches(Matchers.<NestedCommandContext>anyObject());
	}

	@Test
	public void testThatCommandsAreSorted()
	throws Exception {
		Command nestedCommand1 = mock(Command.class);
		when(nestedCommand1.getName()).thenReturn("test");
		Command nestedCommand2 = mock(Command.class);
		when(nestedCommand2.getName()).thenReturn("Frank");
		commandInvoker.addCommand(nestedCommand1);
		commandInvoker.addCommand(nestedCommand2);
		String firstCommand = (String) commandInvoker.getCommands().keySet().toArray()[0];
		Assert.assertTrue("The command list has not been sorted alphabetically!", firstCommand.contentEquals(nestedCommand2.getName()));
	}

	private void setServer(Server server)
	throws Exception {
		Field field = Bukkit.class.getDeclaredField("server");
		field.setAccessible(true);
		field.set(null, server);
	}

}
