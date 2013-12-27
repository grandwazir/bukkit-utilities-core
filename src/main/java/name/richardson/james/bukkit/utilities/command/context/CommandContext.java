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

package name.richardson.james.bukkit.utilities.command.context;

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

	/**
	 * Get the contents of the flag.
	 *
	 * @param label the flag label to look up.
	 * @return the contents of the flag or null if the flag is a switch or does not exist.
	 */
	String getFlag(String label);

	/**
	 * Join all the arguments from a specified index onwards into one String.
	 *
	 * @param initialIndex the index to start at at
	 * @return a String containing all the arguments seperated by ' '.
	 */
	String getJoinedArguments(int initialIndex);

	/**
	 * Get the argument at the specified index.
	 *
	 * @param index the argument number to fetch.
	 * @return the argument specified or null if there is nothing at that index.
	 */
	String getString(int index);

	/**
	 * Check to see if the context contains an argument.
	 *
	 * @param index the argument number to check
	 * @return true if the argument exists, false otherwise.
	 */
	boolean hasArgument(int index);

	/**
	 * Check to see if the context has a certain switch
	 *
	 * @param label the name of the switch to check
	 * @return true if the switch exists, false otherwise.
	 */
	boolean hasSwitch(String switchName);

	/**
	 * Get the total number of arguments contained within this context. The total does not include the CommandSender or any optional flags.
	 *
	 * @return total number of arguments.
	 */
	int size();

}
