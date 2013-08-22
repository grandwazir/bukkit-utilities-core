package name.richardson.james.bukkit.utilities.formatters;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class PreciseTimeFormatterTest extends AbstractTimeFormatterTest {

	@Test
	public void getHumanReadableTime_WhenTimeInFuture_GetPreciseRemainingTime() {
		assertEquals("1 day 12 hours 30 minutes moments from now", getFormatter().getHumanReadableDuration(System.currentTimeMillis() + 131415000));
	}

	@Before
	public void setUp()
	throws Exception {
		setFormatter(new PreciseTimeFormatter());
	}


}
