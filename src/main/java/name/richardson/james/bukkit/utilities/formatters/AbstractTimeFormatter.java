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

import org.ocpsoft.prettytime.i18n.Resources_de;
import org.ocpsoft.prettytime.i18n.Resources_en;
import org.ocpsoft.prettytime.i18n.Resources_fr;
import org.ocpsoft.prettytime.i18n.Resources_ru;

/**
 * Provides a final implementation of {@code getDurationInMilliseconds}. This class should be used when implementing other TimeFormatters which return different
 * style human readable durations.
 */
public abstract class AbstractTimeFormatter implements TimeFormatter {

	/**
	 * This is a dummy method to force Maven to include the resources required by PrettyTime.
	 * It serves no other purpose.
	 */
	private static final void getResources() {
		new Resources_en();
		new Resources_fr();
		new Resources_de();
		new Resources_ru();
	}

	private final Matcher inputFormatter = Pattern.compile("(\\d+)(\\w)").matcher("");

	/**
	 * Return the time in milliseconds represented by the String.
	 * <p/>
	 * This method expects a String to be in the following format {@code xdxhxmxs} where x are units of time. d are days, h are hours, m are minutes and s are
	 * seconds. Any units which are not understood by the parser will be ignored.
	 *
	 * @param timeString the string to parse.
	 * @return the number of milliseconds represented by the String.
	 */
	public final long getDurationInMilliseconds(String timeString) {
		long duration = 0;
		getInputFormatter().reset(timeString.toLowerCase(Locale.ENGLISH));
		while (getInputFormatter().find()) {
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

	/**
	 * The regular expression matcher used to parse the input for {@code getDurationInMilliseconds}.
	 *
	 * @return the matcher
	 */
	protected final Matcher getInputFormatter() {
		return inputFormatter;
	}

}
