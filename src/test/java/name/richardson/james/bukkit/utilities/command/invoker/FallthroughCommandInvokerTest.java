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
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.context.NestedCommandContext;
import name.richardson.james.bukkit.utilities.command.context.PassthroughCommandContext;

import static org.mockito.Mockito.*;

public class FallthroughCommandInvokerTest extends AbstractCommandInvokerTest {

	private Command defaultCommand;

	@Test
	public void onCommandWhenCommandIsNotInMapFallThroughToDefaultCommand() {
		String[] arguments = {""};
		execute(arguments);
		verify(defaultCommand).execute(Matchers.<PassthroughCommandContext>anyObject());
	}

	@Test
	public void onCommandWhenCommandIsInMapExecuteCommand() {
		Command mappedCommand = setMappedCommand();
		String[] arguments = {mappedCommand.getName()};
		execute(arguments);
		verify(mappedCommand).execute(Matchers.<NestedCommandContext>anyObject());
	}

	@Test
	public void onTabCompleteWhenCommandIsNotInMapFallThroughToDefaultCommand() {
		String[] arguments = {""};
		tabComplete(arguments);
		verify(defaultCommand).getArgumentMatches(Matchers.<PassthroughCommandContext>anyObject());
	}


	@Test
	public void onTabCompleteWhenCommandIsInMapMatchCommand() {
		Command mappedCommand = setMappedCommand();
		String[] arguments = {mappedCommand.getName()};
		tabComplete(arguments);
		verify(mappedCommand).getArgumentMatches(Matchers.<NestedCommandContext>anyObject());
	}

	private Command setMappedCommand() {
		Command mappedCommand = getMockCommand();
		getCommandInvoker().addCommand(mappedCommand);
		return mappedCommand;
	}

	protected void execute(String[] arguments) {
		CommandSender commandSender = mock(CommandSender.class);
		getCommandInvoker().onCommand(commandSender, null, null, arguments);
	}

	protected void tabComplete(String[] arguments) {
		CommandSender commandSender = mock(CommandSender.class);
		getCommandInvoker().onTabComplete(commandSender, null, null, arguments);
	}

	@Before
	public void setUp()
	throws Exception {
		defaultCommand = getMockCommand();
		setCommandInvoker(new FallthroughCommandInvoker(defaultCommand));
	}

}
