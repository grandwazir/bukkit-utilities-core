/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 LocalisedTimeFormatter.java is part of BukkitUtilities.

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

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class LocalisedTimeFormatter implements TimeFormatter {

	private final PeriodFormatter inputFormatter;

	public LocalisedTimeFormatter() {
		inputFormatter = new PeriodFormatterBuilder()
		.printZeroNever()
		.appendDays()
		.appendSuffix("d", "d")
		.printZeroNever()
		.appendHours()
		.appendSuffix("h", "h")
		.printZeroNever()
		.appendMinutes()
		.appendSuffix("m", "m")
		.printZeroNever()
		.appendSeconds()
		.appendSuffix("s", "s")
		.toFormatter();
	}

	public long getDurationInMilliseconds(String timeString) {
		return inputFormatter.parsePeriod(timeString).normalizedStandard(PeriodType.millis()).getMillis();
	}

	public String getHumanReadableDuration(long time) {
		Duration duration = new Duration(time);
		Period period = duration.toPeriod();
		return PeriodFormat.wordBased().print(period.normalizedStandard(PeriodType.yearMonthDayTime()));
	}

}
