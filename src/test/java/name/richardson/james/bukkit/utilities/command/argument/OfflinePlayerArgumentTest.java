package name.richardson.james.bukkit.utilities.command.argument;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.bukkit.Server;
import org.junit.Before;
import org.junit.Test;


public class OfflinePlayerArgumentTest extends TestCase {

    private OfflinePlayerArgument argument;
    private Server server;

    @Before
    public void setUp() throws Exception {
        server = ServerFixture.getServer();
        argument = new OfflinePlayerArgument();
    }

    @Test
    public void testParseValue() throws Exception {
        this.testSetServer();
        this.argument.parseValue("grandwazir");
        Assert.assertTrue("Returned value is not the correct player!", argument.getValue().getName().contentEquals("grandwazir"));
    }

    @Test
    public void testSetServer() throws Exception {
        OfflinePlayerArgument.setServer(server);
    }

    @Test
    public void testGetServer() throws Exception {
        this.testSetServer();
        Assert.assertEquals(server, OfflinePlayerArgument.getServer());
    }

    @Test(expected=InvalidArgumentException.class)
    public void testIsRequired() throws Exception {
        this.testSetRequired();
        this.testParseValue();
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
