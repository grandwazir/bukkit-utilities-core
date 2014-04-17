/*
 * Copyright (c) 2014 James Richardson.
 *
 * Argument.java is part of BukkitUtilities.
 *
 * bukkit-utilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * bukkit-utilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * bukkit-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.command.argument;

import java.util.Collection;

import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;

/**
 * An interface that represents an argument that can be passed to a command. Any implementation is responsible for parsing a variable string, extracting any
 * relevant values and then making that value available for use. An {@link InvalidArgumentException} may be thrown if there was an error in execution.
 *
 * @since 8.0.0
 */
public interface Argument extends ArgumentMetadata, Suggester {

	/**
	 * Get the value of this argument.
	 *
	 * This will vary based on implementation and may return null. In case that there are multiple values this will return the first value in the list.
	 *
	 * @return value the value of the argument.
	 */
	public String getString();

	/**
	 * Get all the values supplied to this argument.
	 *
	 * Arguments are able to support list of values separated by a specific character.
	 *
	 * @return values all the parsed values
	 */
	public Collection<String> getStrings();

	/**
	 * Check to see if this argument is the last argument.
	 *
	 * This does not mean that this would be the last argument to actually parse the value, more that this argument was provided last by the user.
	 *
	 * @param arguments
	 * @return
	 */
	boolean isLastArgument(String arguments);

	public String getUsage();

	/**
	 * Parse the provided arguments for a match and, if a match is made, set a value.
	 *
	 * @param argument
	 * @throws InvalidArgumentException
	 */
	public void parseValue(String argument)
	throws InvalidArgumentException;

}
