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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Matchers;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class HelpCommandTest extends AbstractCommandTest {

	private static final String COMMAND_LABEL = "test";
	private static final String PLUGIN_NAME = "BukkitUtilities";
	private static final String PLUGIN_VERSION = "v0.0.1-SNAPSHOT";
	private static final String COMMAND_NAME = "test";
	private static final String COMMAND_USAGE = "<test> [test] <test>";
	private static final String COMMAND_DESCRIPTION = "A description.";
	private static final String VALID_ARGUMENT = COMMAND_NAME;
	private static final String INVALID_ARGUMENT = "frank";
	private static final String PARTIAL_COMMAND_NAME = "tes";

	private CommandContext commandContext;
	private CommandSender commandSender;
	private Command mockCommand;

	@Test
	public void commandShouldAlwaysBeAccessible() {
		assertTrue(getCommand().isAuthorised(null));
	}

	@Test
	public void getArgumentMatchForPartialCommandName() {
		when(commandContext.hasArgument(0)).thenReturn(true);
		when(commandContext.getArgument(0)).thenReturn(PARTIAL_COMMAND_NAME);
		when(commandContext.size()).thenReturn(1);
		Set<String> matches = getCommand().getArgumentMatches(commandContext);
		assertEquals(new HashSet<String>(Arrays.asList(COMMAND_NAME)), matches);
	}

	@Test
	public void respondWithCommandDescriptionIfArgumentValid() {
		when(commandContext.hasArgument(0)).thenReturn(true);
		when(commandContext.getArgument(0)).thenReturn(VALID_ARGUMENT);
		getCommand().execute(commandContext);
		verify(commandSender, times(2)).sendMessage(anyString());
	}

	@Test
	public void respondWithCommandListIfArgumentInvalid() {
		when(commandContext.hasArgument(0)).thenReturn(true);
		when(commandContext.getArgument(0)).thenReturn(INVALID_ARGUMENT);
		getCommand().execute(commandContext);
		verify(commandSender, times(4)).sendMessage(anyString());
	}

	@Test
	public void respondWithCommandListIfNoArguments() {
		getCommand().execute(commandContext);
		verify(commandSender, times(4)).sendMessage(anyString());
	}

	@Test
	public void respondWithCommandListIfRequestedCommandIsInaccessible() {
		when(mockCommand.isAuthorised(Matchers.<Permissible>any())).thenReturn(false);
		when(commandContext.getArgument(0)).thenReturn(VALID_ARGUMENT);
		getCommand().execute(commandContext);
		verify(commandSender, times(3)).sendMessage(anyString());
	}

	@Test
	public void respondWithCommandListOnlyIncludingAccessibleCommandsIfNoArguments() {
		when(mockCommand.isAuthorised(Matchers.<Permissible>any())).thenReturn(false);
		getCommand().execute(commandContext);
		verify(commandSender, times(3)).sendMessage(anyString());
	}

	@Before
	public void setUp()
	throws Exception {
		mockCommand = mock(Command.class);
		commandContext = getMockCommandContext();
		commandSender = commandContext.getCommandSender();
		when(mockCommand.getName()).thenReturn(COMMAND_NAME);
		when(mockCommand.getUsage()).thenReturn(COMMAND_USAGE);
		when(mockCommand.getDescription()).thenReturn(COMMAND_DESCRIPTION);
		when(mockCommand.isAuthorised(Matchers.<Permissible>any())).thenReturn(true);
		PluginDescriptionFile pluginDescriptionFile = new PluginDescriptionFile(PLUGIN_NAME, PLUGIN_VERSION, null);
		setCommand(new HelpCommand(pluginDescriptionFile, COMMAND_LABEL, new HashSet<Command>(Arrays.asList(mockCommand))));
	}

}
