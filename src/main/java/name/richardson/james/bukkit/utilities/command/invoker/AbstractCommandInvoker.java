/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractCommandInvoker.java is part of bukkit-utilities.

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

import java.util.*;

import org.bukkit.command.CommandSender;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.command.Command;
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.context.PassthroughCommandContext;

public abstract class AbstractCommandInvoker implements CommandInvoker {

	private final Map<String, Command> commandMap = new TreeMap<String, Command>(String.CASE_INSENSITIVE_ORDER);

	/**
	 * Add a command to this CommandInvoker allowing it to delgate arguments to it that match the command's name.
	 *
	 * @param command
	 */
	@Override
	public final void addCommand(Command command) {
		Validate.notNull(command);
		commandMap.put(command.getName(), command);
	}

	/**
	 * Add a collection of commands to this CommandInvoker.
	 *
	 * @param commands
	 */
	@Override
	public final void addCommands(Collection<Command> commands) {
		Validate.notNull(commands);
		for (Command command : commands) {
			commandMap.put(command.getName(), command);
		}
	}


	/**
	 * Get a unmodifiable map of all the commands assigned to this invoker.
	 *
	 * @return the commands
	 */
	@Override
	public final Map<String, Command> getCommands() {
		return Collections.unmodifiableMap(commandMap);
	}

}
