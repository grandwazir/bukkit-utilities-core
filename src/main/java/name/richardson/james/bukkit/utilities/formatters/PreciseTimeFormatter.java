package name.richardson.james.bukkit.utilities.formatters;

import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;

/**
 * Formats time into their exact respective units without prefixes or suffixes. For example "3 hours 12 seconds".
 */
public class PreciseTimeFormatter extends AbstractTimeFormatter {

	/**
	 * Return the human readable duration for a given number of milliseconds.
	 *
	 * @param time the number of milliseconds
	 * @return the string representing the number of milliseconds in a human readable format.
	 */
	@Override
	public String getHumanReadableDuration(long time) {
		if (time < System.currentTimeMillis()) throw new IllegalArgumentException("Dates in the past are not formatted correctly.");
		PrettyTime formatter = new PrettyTime();
		// formatter.removeUnit(Millisecond.class);
		Date date = new Date(time);
		List<Duration> duration = formatter.calculatePreciseDuration(date);
		return formatter.format(duration);
	}

}
