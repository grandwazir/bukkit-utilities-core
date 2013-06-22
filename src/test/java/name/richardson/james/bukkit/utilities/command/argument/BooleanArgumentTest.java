package name.richardson.james.bukkit.utilities.command.argument;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;


public class BooleanArgumentTest extends TestCase {

    private BooleanArgument argument;

    @Before
    public void setUp() {
        argument = new BooleanArgument();    
    }
   
    @Test
    public void testGetValue() throws Exception {

    }

    @Test
    public void testParseValue() throws Exception {
        this.argument.parseValue("true");
        Assert.assertTrue("Returned value is not true", argument.getValue());
        this.argument.parseValue("false");
        Assert.assertFalse("Returned value is not false", argument.getValue());
    }

    @Test
    public void testIsRequired() throws Exception {
        this.testSetRequired();
        Assert.assertTrue(argument.isRequired());
    }

    @Test
    public void testSetRequired() throws Exception {
        argument.setRequired(true);
    }

    @Test
    public void testGetMatches() throws Exception {
        Assert.assertTrue("List does not contain true!", argument.getMatches("tr").contains("true"));
        Assert.assertTrue("List does not contain false!", argument.getMatches("faLSe").contains("false"));
        Assert.assertTrue("List is not complete!", argument.getMatches("").size() == 2);
    }
}
