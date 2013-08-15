/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractCommandInvokerTest.java is part of bukkit-utilities.

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

import java.util.Arrays;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.command.Command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public abstract class AbstractCommandInvokerTest extends TestCase {

	private CommandInvoker commandInvoker;

	@Test
	public void addCommand_whenAddCommand_isAddedToCollection() {
		Command command = getMockCommand();
		getCommandInvoker().addCommand(command);
		Assert.assertTrue(commandInvoker.getCommands().containsValue(command));
	}

	@Test
	public void addCommands_whenAddCommand_isAddedToCollection() {
		Command command = getMockCommand();
		getCommandInvoker().addCommands(Arrays.asList(command));
		Assert.assertTrue(commandInvoker.getCommands().containsValue(command));
	}

	protected final Command getMockCommand() {
		Command command = mock(Command.class);
		when(command.getName()).thenReturn("test");
		return command;
	}

	protected CommandInvoker getCommandInvoker() {
		return commandInvoker;
	}

	protected void setCommandInvoker(CommandInvoker commandInvoker) {
		this.commandInvoker = commandInvoker;
	}

}
