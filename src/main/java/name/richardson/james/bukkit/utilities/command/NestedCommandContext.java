/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 NestedCommandContext.java is part of bukkit-utilities.

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

import org.apache.commons.lang.ArrayUtils;

import name.richardson.james.bukkit.utilities.command.AbstractCommandContext;

/**
 * A NestedCommandContext assumes that the first argument passed is the name of the command to be executed and removes it from the list before parsing the
 * remaining arguments.
 */
public class NestedCommandContext extends AbstractCommandContext {

	/**
	 * Constructs a NestedCommandContext from the provided arguments and CommandSender.
	 *
	 * @param arguments provided arguments
	 * @param sender the CommandSender executing the command
	 */
	public NestedCommandContext(String[] arguments, CommandSender sender) {
		super((String[]) ArrayUtils.remove(arguments, 0), sender);
	}

}
