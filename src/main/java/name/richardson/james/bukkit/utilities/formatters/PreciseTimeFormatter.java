package name.richardson.james.bukkit.utilities.formatters;

import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.units.JustNow;
import org.ocpsoft.prettytime.units.Millisecond;

/**
 * Formats time into their exact respective units without prefixes or suffixes. For example "3 hours 12 seconds".
 */
public class PreciseTimeFormatter extends AbstractTimeFormatter {

	private final PrettyTime formatter;

	public PreciseTimeFormatter() {
		formatter = new PrettyTime();
		formatter.removeUnit(JustNow.class);
		formatter.removeUnit(Millisecond.class);
	}

	/**
	 * Return the human readable duration for a given number of milliseconds.
	 *
	 * @param time the number of milliseconds
	 * @return the string representing the number of milliseconds in a human readable format.
	 */
	@Override
	public String getHumanReadableDuration(long time) {
		Date date = new Date(time);
		List<Duration> durations = formatter.calculatePreciseDuration(date);
		return formatter.format(durations);
	}

}
