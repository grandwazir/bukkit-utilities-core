package name.richardson.james.bukkit.utilities.formatters.time;

import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.units.JustNow;

public class PreciseDurationTimeFormatter extends AbstractTimeFormatter {

	/**
	 * Return the human readable duration for a given number of milliseconds.
	 *
	 * @param time the number of milliseconds
	 * @return the string representing the number of milliseconds in a human readable format.
	 */
	@Override
	public String getHumanReadableDuration(long time) {
		// This is a bit of a hack so it only works with the english language
		PrettyTime formatter = new PrettyTime();
		formatter.removeUnit(JustNow.class);
		Date date = new Date(System.currentTimeMillis() + time);
		List<Duration> durationList = formatter.calculatePreciseDuration(date);
		String duration = formatter.format(durationList);
		duration = duration.replaceAll("from now", "");
		duration = duration.replaceAll("ago", "");
		return duration.trim();
	}

}
