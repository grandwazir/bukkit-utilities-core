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

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractTimeFormatter implements TimeFormatter {

	private final Matcher inputFormatter = Pattern.compile("(\\d+)(\\w)").matcher("");

	public final long getDurationInMilliseconds(String timeString) {
		long duration = 0;
		getInputFormatter().reset(timeString.toLowerCase(Locale.ENGLISH));
		while(getInputFormatter().find()) {
			int value = Integer.parseInt(getInputFormatter().group(1));
			String unit = getInputFormatter().group(2);
			if (unit.equalsIgnoreCase("d")) {
				duration = duration + TimeUnit.MILLISECONDS.convert(value, TimeUnit.DAYS);
			} else if (unit.equalsIgnoreCase("h")) {
				duration = duration + TimeUnit.MILLISECONDS.convert(value, TimeUnit.HOURS);
			} else if (unit.equalsIgnoreCase("m")) {
				duration = duration + TimeUnit.MILLISECONDS.convert(value, TimeUnit.MINUTES);
			} else if (unit.equalsIgnoreCase("s")) {
				duration = duration + TimeUnit.MILLISECONDS.convert(value, TimeUnit.SECONDS);
			}
		}
		return duration;
	}

	protected final Matcher getInputFormatter() {
		return inputFormatter;
	}

}
