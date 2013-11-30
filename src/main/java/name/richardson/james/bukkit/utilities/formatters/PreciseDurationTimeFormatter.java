package name.richardson.james.bukkit.utilities.formatters;

import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.units.JustNow;

/**
 * Created with IntelliJ IDEA. User: james Date: 22/08/13 Time: 17:31 To change this template use File | Settings | File Templates.
 */
public class PreciseDurationTimeFormatter extends AbstractTimeFormatter {

	/**
	 * Return the human readable duration for a given number of milliseconds.
	 *
	 * @param time the number of milliseconds
	 * @return the string representing the number of milliseconds in a human readable format.
	 */
	@Override
	public String getHumanReadableDuration(long time) {
		// This is a bit of a hack so it only works with the english langauge
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
