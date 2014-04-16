/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 Suggester.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.argument.suggester;

import java.util.Set;

/**
 * A Suggester attempts to match a String against a known list of values and returns possible matches.
 */
public interface Suggester {

	/**
	 * The maximum matches that will be returned. This is used to prevent flooding a player's chat with results.
	 */
	public static final int MAX_MATCHES = 50;

	/**
	 * Return all the possible matches.
	 *
	 * @param argument
	 * @return the set of possible matches.
	 */
	public Set<String> suggestValue(String argument);

}
