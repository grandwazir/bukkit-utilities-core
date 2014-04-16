/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractCommandTest.java is part of bukkit-utilities.

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

import org.bukkit.command.CommandSender;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public abstract class AbstractCommandTest extends TestCase {

	private Command command;

	public final static CommandContext getMockCommandContext() {
		CommandContext commandContext = mock(CommandContext.class);
		CommandSender commandSender = mock(CommandSender.class);
		when(commandContext.getCommandSender()).thenReturn(commandSender);
		return commandContext;
	}

	@Test
	public void commandDescriptionIsNotNull() {
		assertNotNull("A localised command description has not been set!", command.getDescription());
	}

	@Test
	public void commandNameIsNotNull() {
		assertNotNull("A localised command name has not been set!", command.getName());
	}

	@Test
	public void commandUsageIsNotNull() {
		assertNotNull("A localised command usage string has not been set!", command.getDescription());
	}

	@Test
	public void toStringIsOverridden() {
		assertTrue("toString() has not been overridden!", command.toString().contains("AbstractCommand"));
	}

	protected final Command getCommand() {
		return command;
	}

	protected final void setCommand(Command command) {
		this.command = command;
	}

}
