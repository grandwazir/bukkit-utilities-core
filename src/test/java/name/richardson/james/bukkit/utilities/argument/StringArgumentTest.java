package name.richardson.james.bukkit.utilities.argument;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class StringArgumentTest extends TestCase {

    private static String VALUE = "HELLO";

    private StringArgument argument;

    @Before
    public void setUp() {
        this.argument = new StringArgument();
    }

    @Test
    public void testGetValue() throws Exception {
        this.testParseValue();
        Assert.assertTrue("Returned value is inconsistent", this.argument.getValue().contentEquals(VALUE));
        Assert.assertEquals("Returned value is not a String!", String.class, this.argument.getValue().getClass());
    }

    @Test
    public void testParseValue() throws Exception {
        this.argument.parseValue("HELLO");
    }

    @Test
    public void testSetCaseInsensitive() throws Exception {
        this.argument.setCaseInsensitive(true);
        this.testParseValue();
        Assert.assertTrue("Returned value is not in lower case!", this.argument.getValue().contentEquals(VALUE.toLowerCase()));
    }

    @Test(expected = InvalidArgumentException.class)
    public void testIsRequired() throws Exception {
        this.testSetRequired();
        Assert.assertTrue("Argument is not required!", argument.isRequired());
        this.argument.parseValue(null);
    }

    @Test
    public void testSetRequired() throws Exception {
        this.argument.setRequired(true);
    }
}
