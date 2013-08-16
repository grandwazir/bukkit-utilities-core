/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractPrefixedLoggerTest.java is part of BukkitUtilities.

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
import java.util.logging.Logger;

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

