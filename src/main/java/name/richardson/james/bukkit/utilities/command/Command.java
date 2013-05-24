/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
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
import org.bukkit.command.TabCompleter;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.logging.LocalisedLogger;

public interface Command extends CommandExecutor, TabCompleter {

  /**
   * Execute a command.
   * 
   * @param sender The CommandSender using this command.
   * @throws CommandArgumentException if any of the arguments are invalid.
   * @throws CommandPermissionException if the player does not have permission
   *           to use the command.
   * @throws CommandUsageException if the command is being used in an
   *           inappropriate way.
   */
  public void execute(CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException;

  /**
   * Gets the description.
   * 
   * @return a brief description of what the command does.
   */
  public String getDescription();

  public Localisation getLocalisation();

  public LocalisedLogger getLogger();

  /**
   * Gets the name of this command.
   * 
   * @return the name of the command.
   */
  public String getName();

  /**
   * Gets the usage.
   * 
   * @return a brief string detailing how to use the command.
   */
  public String getUsage();

  /**
   * Parse and validate the given command arguments.
   * 
   * @param arguments the arguments provided by the CommandManager
   * @param sender the sender
   * @throws CommandArgumentException if any of the arguments are invalid or
   *           missing.
   */
  public void parseArguments(String[] arguments, CommandSender sender) throws CommandArgumentException;

  /**
   * Check if a player has permission to use this command.
   * 
   * @param sender the CommandSender attempting to use the command.
   * @return true if they are allowed to use the command, false otherwise.
   */
  public boolean testPermission(CommandSender sender);

}
