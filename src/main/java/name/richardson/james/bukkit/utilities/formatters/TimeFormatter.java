/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TimeFormatter.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.formatters;

/**
 * Converts times represented within Strings to milliseconds and back again.
 */
public interface TimeFormatter {

	/**
	 * Return the milliseconds represented by the String provided.
	 *
	 * @param timeString the string to parse.
	 * @return the number of milliseconds it represents.
	 */
	public long getDurationInMilliseconds(String timeString);

	/**
	 * Return the human readable duration for a given number of milliseconds.
	 *
	 * @param time the number of milliseconds
	 * @return the string representing the number of milliseconds in a human readable format.
	 */
	public String getHumanReadableDuration(long time);

}
