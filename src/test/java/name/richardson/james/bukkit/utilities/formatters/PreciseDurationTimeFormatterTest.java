package name.richardson.james.bukkit.utilities.formatters;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class PreciseDurationTimeFormatterTest extends AbstractTimeFormatterTest {

	@Test
	public void getHumanReadableTime_WhenTimeInFuture_GetPreciseDuration() {
		assertEquals("1 day 12 hours 30 minutes 15 seconds", getFormatter().getHumanReadableDuration(System.currentTimeMillis() + 131415000));
	}

	@Test
	public void getHumanReadableTime_WhenTimeInPast_GetPreciseDuration() {
		assertEquals("1 day 12 hours 30 minutes 15 seconds", getFormatter().getHumanReadableDuration(System.currentTimeMillis() - 131415000));
	}

	@Before
	public void setUp()
	throws Exception {
		setFormatter(new PreciseDurationTimeFormatter());
	}


}
