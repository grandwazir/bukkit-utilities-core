/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PermissivePrefixedLoggerTest.java is part of BukkitUtilities.

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
import org.mockito.Matchers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class PermissivePrefixedLoggerTest extends AbstractPrefixedLoggerTest {


	@Test
	public void log_WhenPassedStringKey_LogTranslatedMessage() {
		ArgumentCaptor<LogRecord> argument = captureLogRecord(Level.INFO, "test-message");
		assertTrue(argument.getValue().getMessage().contains("Test log message"));
	}

	@Test
	public void log_WhenPassedInvalidStringKey_LogMessageAnyway() {
		ArgumentCaptor<LogRecord> argument = captureLogRecord(Level.INFO, "Blah!");
		assertTrue(argument.getValue().getMessage().contains("Blah"));
	}

	@Test
	public void log_WhenLogging_AddPrefix() {
		ArgumentCaptor<LogRecord> argument = captureLogRecord(Level.INFO, "Blah!");
		assertTrue(argument.getValue().getMessage().contains(getLogger().getPrefix()));
	}

	@Test
	public void log_WhenDebugging_AddDebuggingPrefix() {
		getLogger().setLevel(Level.ALL);
		ArgumentCaptor<LogRecord> argument = captureLogRecord(Level.FINE, "Blah!");
		assertTrue(argument.getValue().getMessage().contains(getLogger().getDebuggingPrefix()));
	}

	@Before
	public void setUp()
	throws Exception {
		this.setLogger((AbstractPrefixedLogger) PluginLoggerFactory.getLogger(PermissivePrefixedLoggerTest.class));
	}

}
