package name.richardson.james.bukkit.utilities.argument;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class OnlinePlayerArgumentTest extends TestCase {

    private static final String[] PLAYER_NAMES = {"grandwazir", "Sergeant_Subtle"};

    private OnlinePlayerArgument argument;
    private Server server;

    @Before
    public void setUp() throws Exception {
        server = EasyMock.createNiceMock(Server.class);
        ArrayList<Player> players = new ArrayList<Player>();
        for (String name : PLAYER_NAMES) {
            Player player = EasyMock.createNiceMock(Player.class);
            EasyMock.expect(player.getName()).andReturn(name).times(2);
            EasyMock.replay(player);
            players.add(player);
        }
        EasyMock.expect(server.getPlayer((String) EasyMock.anyObject())).andReturn(players.get(0));
        EasyMock.expect(server.getPlayer((String) EasyMock.anyObject())).andReturn(players.get(1));
        EasyMock.expect(server.getPlayer((String) EasyMock.anyObject())).andReturn(null).times(2);
        EasyMock.replay(server);
        this.argument = new OnlinePlayerArgument();
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
}
