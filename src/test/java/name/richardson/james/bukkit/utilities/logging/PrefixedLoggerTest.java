package name.richardson.james.bukkit.utilities.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class PrefixedLoggerTest extends TestCase {

	public static final String LOGGER_NAME = "PrefixedLoggerTest";
	private static final String PREFIX = "Prefix";
	private PrefixedLogger logger;

	@Test
	public void returnStaticPrefixWhenSet()
	throws Exception {
		PrefixedLogger.setPrefix(PREFIX);
		assertTrue("Prefix has not been set correctly!", PrefixedLogger.getPrefix().contains(PREFIX));
	}

	@Test
	public void returnedDebuggingPrefixContainsOwningClassName()
	throws Exception {
		assertTrue("Debugging prefix does not contain the name of the class it belongs to!", logger.getDebuggingPrefix().contains(LOGGER_NAME));
	}

	@Before
	public void setup() {
		logger = new PrefixedLogger(LOGGER_NAME, null);
		logger.toString();
	}

	@Test
	public void whenLevelIsNotAllAddNormalPrefixToMessages() {
		logger.setLevel(Level.INFO);
		PrefixedLogger.setPrefix(PREFIX);
		ArgumentCaptor<LogRecord> argument = captureLogRecord(Level.INFO, "Blah!");
		assertTrue("Prefix has not been added! " + argument.getValue().getMessage(), argument.getValue().getMessage().contains(logger.getPrefix()));
	}

	private final ArgumentCaptor<LogRecord> captureLogRecord(Level level, String message) {
		Handler handler = mock(Handler.class);
		logger.addHandler(handler);
		ArgumentCaptor<LogRecord> record = ArgumentCaptor.forClass(LogRecord.class);
		logger.log(level, message);
		verify(handler).publish(record.capture());
		return record;
	}

	@Test
	public void whenLevelSetToAllAddDebuggingPrefixToMessages() {
		logger.setLevel(Level.ALL);
		ArgumentCaptor<LogRecord> argument = captureLogRecord(Level.FINE, "Blah!");
		assertTrue("Debugging prefix has not been added!", argument.getValue().getMessage().contains(logger.getDebuggingPrefix()));
	}

}