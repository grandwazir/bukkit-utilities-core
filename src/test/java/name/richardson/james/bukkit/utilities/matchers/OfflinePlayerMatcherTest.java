package name.richardson.james.bukkit.utilities.matchers;

import junit.framework.TestCase;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class OfflinePlayerMatcherTest extends TestCase {

    private static final String[] PLAYER_NAMES = {"grandwazir", "Sergeant_Subtle"};

    private Server server;
    private List<OfflinePlayer> players;

    @Before
    public void setUp() throws Exception {
        this.server = EasyMock.createNiceMock(Server.class);
        this.players = new ArrayList<OfflinePlayer>();
        for (String name : PLAYER_NAMES) {
            Player player = EasyMock.createNiceMock(Player.class);
            EasyMock.expect(player.getName()).andReturn(name).times(2);
            EasyMock.replay(player);
            players.add(player);
        }
        EasyMock.expect(server.getOfflinePlayers()).andStubReturn(players.toArray(new OfflinePlayer[players.size()]));
        EasyMock.replay(this.server);
    }

    @Test
    public void testPartialMatch() throws Exception {
        OfflinePlayerMatcher.setServer(server);
        OfflinePlayerMatcher matcher = new OfflinePlayerMatcher();
        List<String> matches = matcher.getMatches("gran");
        Assert.assertTrue("List does not contain 'grandwazir'", matches.contains("grandwazir"));
        Assert.assertTrue("List is not equal to 1", matches.size() == 1);
    }

    @Test
    public void testExactMatch() throws Exception {
        OfflinePlayerMatcher.setServer(server);
        OfflinePlayerMatcher matcher = new OfflinePlayerMatcher();
        List<String> matches = matcher.getMatches("grandwazir");
        Assert.assertTrue("List does not contain 'grandwazir'", matches.contains("grandwazir"));
        Assert.assertTrue("List is not equal to 1", matches.size() == 1);
    }

    @Test
    public void testDoNotMatchBlank() throws Exception {
        OfflinePlayerMatcher.setServer(server);
        OfflinePlayerMatcher matcher = new OfflinePlayerMatcher();
        List<String> matches = matcher.getMatches("");
        Assert.assertTrue("List is not equal to 0", matches.size() == 0);
    }

    @Test
    public void testCaseInsensitiveMatch() throws Exception {
        OfflinePlayerMatcher.setServer(server);
        OfflinePlayerMatcher matcher = new OfflinePlayerMatcher();
        List<String> matches = matcher.getMatches("SERGEANT_SUBTL");
        Assert.assertTrue("List does not contain 'Sergeant_Subtle'", matches.contains("Sergeant_Subtle"));
        Assert.assertTrue(matches.size() == 1);
    }

    @Test(expected=IllegalStateException.class)
    public void testServerMustBeSet() throws Exception {
        OfflinePlayerMatcher.setServer(server);
        OfflinePlayerMatcher matcher = new OfflinePlayerMatcher();
        OfflinePlayerMatcher.setServer(null);
        matcher.getMatches("");
    }

    @Test(expected=IllegalStateException.class)
    public void testServerMustBeSetBeforeConstruction() throws Exception {
        OfflinePlayerMatcher.setServer(null);
        new OfflinePlayerMatcherTest();
    }

}
