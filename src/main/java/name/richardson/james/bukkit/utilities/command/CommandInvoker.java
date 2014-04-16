/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CommandInvoker.java is part of bukkit-utilities.

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

import java.util.Collection;
import java.util.Map;

import org.bukkit.command.TabExecutor;

/**
 * Manages and handles the execution of {@link name.richardson.james.bukkit.utilities.command.Command}s. It is responsible for creating a {@link
 * CommandContext} and executing the correct command depending on the arguments provided.
 */
public interface CommandInvoker extends TabExecutor {

	/**
	 * Add a command to this CommandInvoker allowing it to delegate arguments to it that match the supplied command's name.
	 *
	 * @param command the command to add
	 */
	public void addCommand(Command command);

	/**
	 * Add a collection of commands to this CommandInvoker.
	 *
	 * @param commands the commands to add
	 */
	public void addCommands(Collection<Command> commands);

	/**
	 * Get a unmodifiable map of all the commands assigned to this invoker.
	 *
	 * @return the map of commands with the command name as the key
	 */
	public Map<String, Command> getCommands();

}
