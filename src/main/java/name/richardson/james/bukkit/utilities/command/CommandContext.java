/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CommandContext.java is part of bukkit-utilities.

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

/**
 * A object that represents the context that a {@link name.richardson.james.bukkit.utilities.command.Command} has been executed within. This object is
 * responsible for parsing the arguments and providing convenience methods to retrieve them. It is up to individual implementations to decide what to do when a
 * requested argument does not exist.
 */
public interface CommandContext {

	/**
	 * Get the CommandSender who called this command.
	 *
	 * @return the CommandSender
	 */
	CommandSender getCommandSender();

	boolean isConsoleCommandSender();

	String getArguments();


}
