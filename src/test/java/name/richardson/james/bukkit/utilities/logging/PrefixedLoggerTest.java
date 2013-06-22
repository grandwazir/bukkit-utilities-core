package name.richardson.james.bukkit.utilities.logging;

import com.avaje.ebean.validation.AssertTrue;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class PrefixedLoggerTest extends TestCase {

    private static final String PREFIX = "[Test] ";

    @Test
    public void testSetAndGetPrefix() throws Exception {
        PrefixedLogger.setPrefix(PREFIX);
        Assert.assertTrue("Prefix has not been set!", PrefixedLogger.getPrefix().contentEquals(PREFIX));
    }

    @Test
    public void testLoggerPackageName() throws Exception {
        final String name = this.getClass().getPackage().getName();
        Logger logger = PrefixedLogger.getLogger(this);
        Assert.assertTrue("Logger name does not equal '" + name + "'", logger.getName().contentEquals(name));
    }

    @Test
    public void testPrefixIsAdded() throws Exception {
        Logger logger = PrefixedLogger.getLogger(this);
        this.testSetAndGetPrefix();
        LogRecord record = new LogRecord(Level.INFO, "This is a test message");
        Handler handler = EasyMock.createNiceMock(Handler.class);
        Capture<LogRecord> captured = new Capture<LogRecord>();
        handler.publish(EasyMock.capture(captured));
        EasyMock.replay(handler);
        logger.addHandler(handler);
        logger.log(record);
        EasyMock.verify(handler);
        Assert.assertTrue("Prefix was not added", captured.getValue().getMessage().startsWith(PREFIX));
    }

    @Test
    public void testDebugPrefixIsAdded() throws Exception {
        Logger logger = PrefixedLogger.getLogger(this);
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
    public void testLoggerWithResourceBundle() throws Exception {
        Logger logger = PrefixedLogger.getLogger(this);
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
    public void testLoggingWithArguments() throws Exception {
        Logger logger = PrefixedLogger.getLogger(this);
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
