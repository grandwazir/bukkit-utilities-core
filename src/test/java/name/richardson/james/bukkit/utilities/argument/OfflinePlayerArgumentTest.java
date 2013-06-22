package name.richardson.james.bukkit.utilities.argument;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;


public class OfflinePlayerArgumentTest extends TestCase {

    private OfflinePlayerArgument argument;
    private Server server;

    @Before
    public void setUp() throws Exception {
        server = EasyMock.createNiceMock(Server.class);
        OfflinePlayer offlinePlayer = EasyMock.createNiceMock(OfflinePlayer.class);
        EasyMock.expect(offlinePlayer.getName()).andReturn("grandwazir");
        EasyMock.expect(offlinePlayer.getName()).andReturn(null);
        EasyMock.replay(offlinePlayer);
        EasyMock.expect(server.getOfflinePlayer((String) EasyMock.anyObject())).andReturn(offlinePlayer);
        EasyMock.replay(server);
        this.argument = new OfflinePlayerArgument();
    }

    @Test
    public void testParseValue() throws Exception {
        this.testSetServer();
        this.argument.parseValue("grandwaz");
        Assert.assertTrue("Returned value is not the correct player!", argument.getValue().getName().contentEquals("grandwazir"));
        this.argument.parseValue("noone");
        Assert.assertEquals("Returned value should be null", null, argument.getValue());
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
        this.argument.parseValue("dfsf");
    }

    @Test
    public void testSetRequired() throws Exception {
        argument.setRequired(true);
    }
}
