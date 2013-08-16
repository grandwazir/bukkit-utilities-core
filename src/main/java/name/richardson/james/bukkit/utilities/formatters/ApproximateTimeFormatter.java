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

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;

public class ApproximateTimeFormatter extends AbstractTimeFormatter {

	@Override
	public String getHumanReadableDuration(long time) {
		Date date = new Date(time);
		PrettyTime timeFormatter = new PrettyTime();
		return timeFormatter.format(date);
	}

}
