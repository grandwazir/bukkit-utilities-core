/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * Command.java is part of BukkitUtilities.
 * 
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.utilities.permissions.PermissionsHolder;

public interface Command extends CommandExecutor, PermissionsHolder {

  /**
   * Execute a command.
   * 
   * @param sender The CommandSender using this command.
   * @param arguments Any arguments provided. This should not include any command labels such as command prefixes.
   * @throws CommandArgumentException if any of the arguments are invalid.
   * @throws CommandPermissionException if the player does not have permission to use the command.
   * @throws CommandUsageException if the command is being used in an inappropriate way.
   */
  void execute(CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException;

  /**
   * Gets the description.
   * 
   * @return the description
   */
  String getDescription();

  /**
   * Gets the name of this command.
   * 
   * @return the name
   */
  String getName();

  /**
   * Gets the usage.
   * 
   * @return the usage
   */
  String getUsage();

  /**
   * Parse the command arguments.
   * 
   * @param arguments the arguments provided by the CommandManager
   * @throws CommandArgumentException if any of the arguments are invalid.
   */
  void parseArguments(String[] arguments, CommandSender sender) throws CommandArgumentException;

}
