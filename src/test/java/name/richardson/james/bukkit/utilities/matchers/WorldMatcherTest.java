package name.richardson.james.bukkit.utilities.matchers;

import junit.framework.TestCase;
import org.bukkit.Server;
import org.bukkit.World;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class WorldMatcherTest extends TestCase {

    private static final String[] WORLD_NAMES = {"world", "world_nether", "world_the_end"};

    private WorldMatcher matcher;
    private Server server;
    private List<World> worlds;

    @Before
    public void setUp() throws Exception {
        this.server = EasyMock.createNiceMock(Server.class);
        this.worlds = new ArrayList<World>();
        for (String name : WORLD_NAMES) {
            World world = EasyMock.createNiceMock(World.class);
            EasyMock.expect(world.getName()).andReturn(name);
            EasyMock.replay(world);
            worlds.add(world);
        }
        WorldMatcher.setServer(server);
        EasyMock.expect(server.getWorlds()).andReturn(worlds);
        EasyMock.replay(this.server);
        this.matcher = new WorldMatcher();
    }

    @Test
    public void testWorldPartialMatch() throws Exception {
        List<String> matches = this.matcher.getMatches("world_n");
        Assert.assertTrue("List does not contain 'world_nether'", matches.contains("world_nether"));
        Assert.assertTrue("List is not equal to 1", matches.size() == 1);
    }

    @Test
    public void testWorldExactMatch() throws Exception {
        List<String> matches = this.matcher.getMatches("world_the_end");
        Assert.assertTrue(matches.contains("world_the_end"));
        Assert.assertTrue(matches.size() == 1);
    }

    @Test
    public void testBlankMatch() throws Exception {
        List<String> matches = this.matcher.getMatches("");
        Assert.assertTrue(matches.contains("world"));
        Assert.assertTrue(matches.contains("world_nether"));
        Assert.assertTrue(matches.contains("world_the_end"));
        Assert.assertTrue(matches.size() == 3);
    }

    @Test(expected=IllegalStateException.class)
    public void testServerMustBeSet() throws Exception {
        WorldMatcher.setServer(null);
        this.matcher.getMatches("");
    }

    @Test(expected=IllegalStateException.class)
    public void testServerMustBeSetBeforeConstruction() throws Exception {
        WorldMatcher.setServer(null);
        new WorldMatcher();
    }

}
