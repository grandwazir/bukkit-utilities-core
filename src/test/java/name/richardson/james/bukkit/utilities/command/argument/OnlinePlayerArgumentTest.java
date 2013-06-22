package name.richardson.james.bukkit.utilities.command.argument;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.bukkit.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class OnlinePlayerArgumentTest extends TestCase {

    private Server server;

    private OnlinePlayerArgument argument;

    @Before
    public void setUp() throws Exception {
        server = ServerFixture.getServer();
        argument = new OnlinePlayerArgument();
    }

    @Test
    public void testParseValue() throws Exception {
        this.testSetServer();
        this.argument.parseValue("grandwaz");
        Assert.assertTrue("Returned value is not the correct player!", argument.getValue().getName().contentEquals("grandwazir"));
        this.argument.parseValue("Sergeant_s");
        Assert.assertTrue("Returned value is not the correct player!", argument.getValue().getName().contentEquals("Sergeant_Subtle"));
        this.argument.parseValue("noone");
        Assert.assertEquals("Returned value should be null", null, argument.getValue());
    }

    @Test
    public void testSetServer() throws Exception {
        OnlinePlayerArgument.setServer(server);
    }

    @Test
    public void testGetServer() throws Exception {
        this.testSetServer();
        Assert.assertEquals(server, OnlinePlayerArgument.getServer());
    }

    @Test(expected=InvalidArgumentException.class)
    public void testIsRequired() throws Exception {
        this.testSetRequired();
        this.testParseValue();
        this.argument.parseValue("dfsf");
    }

    @Test
    public void testSetRequired() throws Exception {
        argument.setRequired(true);
    }

    @Test
    public void testMatches() throws Exception {
        this.testSetServer();
        Assert.assertTrue("List does not contain grandwazir!", argument.getMatches("gra").contains("grandwazir"));
        Assert.assertTrue("List does not contain Sergeant_Subtle!", argument.getMatches("sergeant_S").contains("Sergeant_Subtle"));
        Assert.assertTrue("List is not complete!", argument.getMatches("").size() == 2);
    }
}
