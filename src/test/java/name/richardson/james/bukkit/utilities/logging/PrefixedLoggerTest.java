/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PrefixedLoggerTest.java is part of bukkit-utilities.

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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PrefixedLoggerTest extends TestCase {

	private static final String PREFIX = "[Test] ";

	@Test
	public void testSetAndGetPrefix()
	throws Exception {
		PrefixedLogger.setPrefix(PREFIX);
		Assert.assertTrue("Prefix has not been set!", PrefixedLogger.getPrefix().contentEquals(PREFIX));
	}

	@Test
	public void testLoggerPackageName()
	throws Exception {
		final String name = this.getClass().getPackage().getName();
		Logger logger = PrefixedLogger.getLogger(this.getClass());
		Assert.assertTrue("Logger name does not equal '" + name + "'", logger.getName().contentEquals(name));
	}

	@Test
	public void testPrefixIsAdded()
	throws Exception {
		Logger logger = PrefixedLogger.getLogger(this.getClass());
		this.testSetAndGetPrefix();
		LogRecord record = new LogRecord(Level.INFO, "This is a test message");
		Handler handler = EasyMock.createNiceMock(Handler.class);
		Capture<LogRecord> captured = new Capture<LogRecord>();
		handler.publish(EasyMock.capture(captured));
		EasyMock.replay(handler);
		logger.addHandler(handler);
		logger.log(record);
		EasyMock.verify(handler);
		// Assert.assertTrue("Prefix was not added " + captured.getValue().getMessage(), captured.getValue().getMessage().startsWith(PREFIX));
	}

	@Test
	public void testDebugPrefixIsAdded()
	throws Exception {
		Logger logger = PrefixedLogger.getLogger(this.getClass());
		this.testSetAndGetPrefix();
		LogRecord record = new LogRecord(Level.INFO, "This is a test message");
		Handler handler = EasyMock.createNiceMock(Handler.class);
		Capture<LogRecord> captured = new Capture<LogRecord>();
		handler.publish(EasyMock.capture(captured));
		EasyMock.replay(handler);
		logger.addHandler(handler);
		logger.setLevel(Level.FINEST);
		logger.log(record);
		EasyMock.verify(handler);
		Assert.assertTrue("Debug prefix was not added", captured.getValue().getMessage().contains(logger.getName()));
	}

	@Test
	public void testLoggerWithResourceBundle()
	throws Exception {
		Logger logger = PrefixedLogger.getLogger(this.getClass());
		LogRecord record = new LogRecord(Level.INFO, "test-message");
		Handler handler = EasyMock.createNiceMock(Handler.class);
		Capture<LogRecord> captured = new Capture<LogRecord>();
		handler.publish(EasyMock.capture(captured));
		EasyMock.replay(handler);
		logger.addHandler(handler);
		logger.log(record);
		EasyMock.verify(handler);
		Assert.assertTrue("Message was not translated!", captured.getValue().getMessage().contains("Testing!"));
	}

	@Test
	public void testLoggingWithArguments()
	throws Exception {
		Logger logger = PrefixedLogger.getLogger(this.getClass());
		Handler handler = EasyMock.createNiceMock(Handler.class);
		Capture<LogRecord> captured = new Capture<LogRecord>();
		handler.publish(EasyMock.capture(captured));
		EasyMock.replay(handler);
		logger.addHandler(handler);
		logger.log(Level.INFO, "test-arguments", "grandwazir");
		EasyMock.verify(handler);
		Assert.assertTrue("Message was not translated!", captured.getValue().getMessage().contains("Hello grandwazir"));
	}

}
