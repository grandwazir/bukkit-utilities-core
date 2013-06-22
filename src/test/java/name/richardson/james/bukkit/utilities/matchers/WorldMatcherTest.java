/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 WorldMatcherTest.java is part of bukkit-utilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.matchers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.World;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class WorldMatcherTest extends TestCase {

	private static final String[] WORLD_NAMES = {"world", "world_nether", "world_the_end"};

	private WorldMatcher matcher;
	private Server server;
	private List<World> worlds;

	@Before
	public void setUp()
	throws Exception {
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
	public void testWorldPartialMatch()
	throws Exception {
		List<String> matches = this.matcher.getMatches("world_n");
		Assert.assertTrue("List does not contain 'world_nether'", matches.contains("world_nether"));
		Assert.assertTrue("List is not equal to 1", matches.size() == 1);
	}

	@Test
	public void testWorldExactMatch()
	throws Exception {
		List<String> matches = this.matcher.getMatches("world_the_end");
		Assert.assertTrue(matches.contains("world_the_end"));
		Assert.assertTrue(matches.size() == 1);
	}

	@Test
	public void testBlankMatch()
	throws Exception {
		List<String> matches = this.matcher.getMatches("");
		Assert.assertTrue(matches.contains("world"));
		Assert.assertTrue(matches.contains("world_nether"));
		Assert.assertTrue(matches.contains("world_the_end"));
		Assert.assertTrue(matches.size() == 3);
	}

	@Test(expected = IllegalStateException.class)
	public void testServerMustBeSet()
	throws Exception {
		WorldMatcher.setServer(null);
		this.matcher.getMatches("");
	}

	@Test(expected = IllegalStateException.class)
	public void testServerMustBeSetBeforeConstruction()
	throws Exception {
		WorldMatcher.setServer(null);
		new WorldMatcher();
	}

}
