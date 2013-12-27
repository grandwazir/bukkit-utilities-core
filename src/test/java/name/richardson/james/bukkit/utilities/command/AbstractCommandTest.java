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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.matcher.Matcher;
import name.richardson.james.bukkit.utilities.command.matcher.MatcherInvoker;
import name.richardson.james.bukkit.utilities.command.matcher.MatcherInvokerTest;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public abstract class AbstractCommandTest extends TestCase {

	private Command command;

	public static CommandContext getMockCommandContext() {
		CommandContext commandContext = mock(CommandContext.class);
		CommandSender commandSender = mock(CommandSender.class);
		when(commandContext.getCommandSender()).thenReturn(commandSender);
		return commandContext;
	}

	@Test
	public void checkCommandNameIsNotNull() {
		assertNotNull(command.getName());
	}

	@Test
	public void checkCommandDescriptionIsNotNull() {
		assertNotNull(command.getDescription());
	}

	@Test
	public void checkCommandUsageIsNotNull() {
		assertNotNull(command.getUsage());
	}



	public final Command getCommand() {
		return command;
	}

	public final void setCommand(Command command) {
		this.command = command;
	}





}
