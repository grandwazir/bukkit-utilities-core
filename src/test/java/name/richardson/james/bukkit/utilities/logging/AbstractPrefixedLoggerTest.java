package name.richardson.james.bukkit.utilities.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public abstract class AbstractPrefixedLoggerTest extends TestCase {

	private AbstractPrefixedLogger logger;

	@Test
	public void GetPrefix_PrefixIsNotNull()
	throws Exception {
		assertNotNull(getLogger().getPrefix());
	}

	@Test
	public void getDebuggingPrefix_PrefixContainsLoggerName()
	throws Exception {
		assertTrue(getLogger().getDebuggingPrefix().contains(getLogger().getName()));
	}


	protected final ArgumentCaptor<LogRecord> captureLogRecord(Level level, String message) {
		Handler handler = mock(Handler.class);
		getLogger().addHandler(handler);
		ArgumentCaptor<LogRecord> record = ArgumentCaptor.forClass(LogRecord.class);
		getLogger().log(level, message);
		verify(handler).publish(record.capture());
		return record;
	}

	protected AbstractPrefixedLogger getLogger() {
		return logger;
	}

	protected void setLogger(AbstractPrefixedLogger logger) {
		this.logger = logger;
	}

}