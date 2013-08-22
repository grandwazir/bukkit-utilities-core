package name.richardson.james.bukkit.utilities.formatters;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class PreciseTimeFormatterTest extends AbstractTimeFormatterTest {

	@Test
	public void getHumanReadableTime_WhenTimeInFuture_GetPreciseRemainingTime() {
		assertEquals("1 day 12 hours 30 minutes 15 seconds from now", getFormatter().getHumanReadableDuration(System.currentTimeMillis() + 131415000));
	}

	@Test
	public void getHumanReadableTime_WhenTimeInPast_GetPreciseElapsedTime() {
		assertEquals("1 day 12 hours 30 minutes 15 seconds ago", getFormatter().getHumanReadableDuration(System.currentTimeMillis() - 131415000));
	}

	@Before
	public void setUp()
	throws Exception {
		setFormatter(new PreciseTimeFormatter());
	}


}
