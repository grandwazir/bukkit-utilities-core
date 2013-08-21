/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PreciseTimeFormatter.java is part of BukkitUtilities.

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

import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * Formats milliseconds into approximate time durations to create Twitter or FaceBook like durations, for example 1 day ago. This should be used when the exact
 * time of an event occurred is not important.
 */
public class ApproximateTimeFormatter extends AbstractTimeFormatter {

	private final PrettyTime timeFormatter;

	public ApproximateTimeFormatter() {
		timeFormatter = new PrettyTime();
	}

	/**
	 * Returns a human readable style time in the style of FaceBook or Twitter.
	 * <p/>
	 * Times in the future are returned are suffixed with "from now", while times in the past use "ago". This does return approximations so for example 1 day and
	 * 16 hours will return as "2 days".
	 *
	 * @param time the number of milliseconds
	 * @return the time as a human readable duration.
	 */
	@Override
	public String getHumanReadableDuration(long time) {
		Date date = new Date(time);
		return timeFormatter.format(date);
	}

}
