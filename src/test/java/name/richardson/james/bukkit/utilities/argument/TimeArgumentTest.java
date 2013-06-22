package name.richardson.james.bukkit.utilities.argument;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;


public class TimeArgumentTest extends TestCase {

    private TimeArgument argument;

    @Before
    public void setUp() throws Exception {
        argument = new TimeArgument();
    }

    @Test
    public void testParseValue() throws Exception {
        argument.parseValue("1d");
        Assert.assertTrue(argument.getValue() == 86400000);
        argument.parseValue("16h");
        Assert.assertTrue(argument.getValue() == 57600000);
        argument.parseValue("wibble");
        Assert.assertTrue(argument.getValue() == 0);
    }

    @Test
    public void testIsRequired() throws Exception {
        testSetRequired();
        argument.parseValue("");
    }

    @Test
    public void testSetRequired() throws Exception {
        argument.setRequired(true);
    }

    @Test
    public void testGetMatches() throws Exception {
        Assert.assertEquals(argument.getMatches(""), Collections.<String>emptySet());
    }
}
