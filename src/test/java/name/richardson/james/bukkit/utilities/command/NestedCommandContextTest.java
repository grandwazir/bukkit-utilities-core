/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 NestedCommandContextTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.BukkitTestFixture;
import name.richardson.james.bukkit.utilities.command.context.Context;
import name.richardson.james.bukkit.utilities.command.context.NestedCommandContext;

public class NestedCommandContextTest extends TestCase {

	private static final String[] ARGUMENTS = {"ban", "this", "is", "t:1234", "a", "reason"};

	private Context context;
	private Player sender;
	private Server server;

	@Test
	public void testHas()
	throws Exception {
		Assert.assertFalse(context.size() == 6);
	}

	@Test
	public void testGetString()
	throws Exception {
		Assert.assertTrue("Expected `this` got " + context.getString(0), context.getString(0).contentEquals("this"));
	}

	@Before
	public void setUp()
	throws Exception {
		server = BukkitTestFixture.getServer();
		BukkitTestFixture.setServer(server);
		sender = EasyMock.createMock(Player.class);
		context = new NestedCommandContext(ARGUMENTS, sender);
	}
}
