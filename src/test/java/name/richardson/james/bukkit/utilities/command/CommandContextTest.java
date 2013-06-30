/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CommandContextTest.java is part of bukkit-utilities.

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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandContextTest extends TestCase {

	private static final String[] ARGUMENTS = {"grandwazir", "this", "is", "t:1234", "a", "reason"};

	@Mock
	private Player sender;

	private Context context;
	@Mock
	private Server server;
	@Mock
	private Player player;

	@Test
	public void testSize()
	throws Exception {
		int size = context.size();
		Assert.assertTrue("Argument size is incorrect, expected 5 got " + size, size == 5);
	}

	@Test
	public void testIsConsoleCommandSender()
	throws Exception {
		Assert.assertFalse("Player supplied to context, isConsoleCommandSender should be false", context.isConsoleCommandSender());
	}

	@Test
	public void testHasFlag()
	throws Exception {
		Assert.assertTrue("A flag called `t` was supplied and should be found", context.hasFlag("t"));
		Assert.assertFalse("A flag called `f` was not supplied and should not be found", context.hasFlag("f"));
	}

	@Test
	public void testHas()
	throws Exception {
		Assert.assertTrue("Should have an argument at index 1", context.has(1));
		Assert.assertFalse("Should not have an argument at index 9", context.has(9));
	}

	@Test
	public void testGetString()
	throws Exception {
		String argument = context.getString(2);
		Assert.assertTrue("Argument at index 2 should be `is` but is actually " + argument, argument.contentEquals("is"));
	}

	@Test
	public void testGetJoinedArguments()
	throws Exception {
		String joined = context.getJoinedArguments(0);
		Assert.assertTrue("Joined arguments are not consistent", joined.contentEquals("grandwazir this is a reason"));
	}

	@Test
	public void testGetPlayer()
	throws Exception {
		when(server.getPlayer("grandwazir")).thenReturn(player);
		context.getPlayer(0);
		verify(server).getPlayer("grandwazir");
	}

	@Test
	public void testGetOfflinePlayer()
	throws Exception {
		when(server.getOfflinePlayer("grandwazir")).thenReturn(player);
		context.getOfflinePlayer(0);
		verify(server).getOfflinePlayer("grandwazir");
	}

	@Test
	public void testGetFlag()
	throws Exception {
		Assert.assertTrue(context.getFlag("t").contentEquals("1234"));
	}

	@Test
	public void testGetCommandSender()
	throws Exception {
		Assert.assertSame(sender, context.getCommandSender());
	}

	@Before
	public void setUp()
	throws Exception {
		context = new CommandContext(ARGUMENTS, sender, server);
	}

}
