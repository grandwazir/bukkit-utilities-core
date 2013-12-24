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

import java.util.*;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.PluginDescriptionFile;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class HelpCommandTest extends CommandTestCase {

	private static final String COMMAND_LABEL = "test";
	private static final String PLUGIN_NAME = "BukkitUtilities";
	private static final String PLUGIN_VERSION = "v0.0.1-SNAPSHOT";
	private static final String COMMAND_NAME = "test";
	private static final String COMMAND_USAGE = "<test> [test] <test>";
	private static final String COMMAND_DESCRIPTION = "A description.";
	private static final String VALID_ARGUMENT = COMMAND_NAME;
	private static final String INVALID_ARGUMENT = "frank";

	private HelpCommand command;
	private Command mockCommand;

	@Test
	public void respondWithCommandListIfNoArguments() {
		CommandContext commandContext = getCommandContext();
		CommandSender commandSender = commandContext.getCommandSender();
		command.execute(commandContext);
		verify(commandSender, times(4)).sendMessage(anyString());
	}

	@Test
	public void respondWithCommandDescriptionIfArgumentValid() {
		CommandContext commandContext = getCommandContext();
		CommandSender commandSender = commandContext.getCommandSender();
		when(commandContext.has(1)).thenReturn(true);
		when(commandContext.getString(1)).thenReturn(VALID_ARGUMENT);
		command.execute(commandContext);
		verify(commandSender, times(2)).sendMessage(anyString());
	}

	@Test
	public void respondWithCommandListIfArgumentInvalid() {
		CommandContext commandContext = getCommandContext();
		CommandSender commandSender = commandContext.getCommandSender();
		when(commandContext.has(1)).thenReturn(true);
		when(commandContext.getString(1)).thenReturn(INVALID_ARGUMENT);
		command.execute(commandContext);
		verify(commandSender, times(4)).sendMessage(anyString());
	}

	@Test
	public void respondWithCommandListOnlyIncludingAccessibleCommandsIfNoArguments() {
		CommandContext commandContext = getCommandContext();
		CommandSender commandSender = commandContext.getCommandSender();
		when(mockCommand.isAuthorised(Matchers.<Permissible>any())).thenReturn(false);
		command.execute(commandContext);
		verify(commandSender, times(3)).sendMessage(anyString());
	}

	@Test
	public void respondWithCommandListIfRequestedCommandIsInaccessible() {
		CommandContext commandContext = getCommandContext();
		CommandSender commandSender = commandContext.getCommandSender();
		when(mockCommand.isAuthorised(Matchers.<Permissible>any())).thenReturn(false);
		when(commandContext.getString(1)).thenReturn(VALID_ARGUMENT);
		command.execute(commandContext);
		verify(commandSender, times(3)).sendMessage(anyString());
	}

	@Test
	public void commandShouldAlwaysBeAccessible() {
		assertTrue(command.isAuthorised(null));
	}

	@Before
	public void setUp()
	throws Exception {
		mockCommand = mock(Command.class);
		when(mockCommand.getName()).thenReturn(COMMAND_NAME);
		when(mockCommand.getUsage()).thenReturn(COMMAND_USAGE);
		when(mockCommand.getDescription()).thenReturn(COMMAND_DESCRIPTION);
		when(mockCommand.isAuthorised(Matchers.<Permissible>any())).thenReturn(true);
		command = new HelpCommand(getPluginDescriptionFile(), COMMAND_LABEL, new HashSet<Command>(Arrays.asList(mockCommand)));
	}

	public static PluginDescriptionFile getPluginDescriptionFile() {
		return new PluginDescriptionFile(PLUGIN_NAME, PLUGIN_VERSION, null);
	}

}
