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

package name.richardson.james.bukkit.utilities.command.context;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class NestedCommandContextTest extends TestCase {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String[] arguments = new String[]{"0", "1", "2", "t:123"};
	private NestedCommandContext context;
	private Player player;
	private Server server;

	@Before
	public void setUp()
	throws Exception {
		player = mock(Player.class);
		server = mock(Server.class);
		context = new NestedCommandContext(arguments, player, server);
	}

	@After
	public void tearDown() throws Exception {
		setServer(null);
	}

	@Test
	public void testGetArguments()
	throws Exception {
		Assert.assertNotNull("Arguments should not be null!", context.getArguments());
	}

	@Test
	public void testGetCommandSender()
	throws Exception {
		Assert.assertEquals("CommandSender should be returned!", context.getCommandSender(), player);
	}

	@Test
	public void testGetFlag()
	throws Exception {
		Assert.assertTrue("Flag label is valid but does not appear to be parsed!", context.getFlag("t").contentEquals("123"));
	}

	@Test
	public void testGetJoinedArguments()
	throws Exception {
		Assert.assertTrue("Arguments have not been joined correctly!", context.getJoinedArguments(1).contentEquals("2"));
	}

	@Test
	public void testGetOfflinePlayer()
	throws Exception {
		when(server.getOfflinePlayer(anyString())).thenReturn(player);
		Assert.assertEquals("Player should be returned.", player, context.getOfflinePlayer(0));
		verify(server).getOfflinePlayer("1");
	}

	@Test
	public void testGetPlayer()
	throws Exception {
		when(server.getPlayer("1")).thenReturn(player);
		Assert.assertEquals("Player should be returned.", player, context.getPlayer(0));
		Assert.assertNull("Player should not be returned.", context.getPlayer(1));
		verify(server).getPlayer("1");
		verify(server).getPlayer("2");
	}

	@Test
	public void testGetString()
	throws Exception {
		Assert.assertTrue("Argument index is valid but does not appear to be parsed!", context.getString(1).contentEquals("2"));
	}

	@Test
	public void testGetStringOutOfBounds() {
		expectedException.expect(IndexOutOfBoundsException.class);
		context.getString(99);
	}

	@Test
	public void testHas()
	throws Exception {
		Assert.assertTrue("Argument index is valid but does not appear to be parsed!", context.has(2));
		Assert.assertFalse("Argument index should not be valid!", context.has(99));
	}

	@Test
	public void testHasFlag()
	throws Exception {
		Assert.assertTrue("Command flag was provided but does not appear to be parsed!", context.hasFlag("t"));
		Assert.assertFalse("Command flag should not be exist!", context.hasFlag("f"));
	}

	@Test
	public void testIsConsoleCommandSenderWhenConsoleCommandSender()
	throws Exception {
		ConsoleCommandSender sender = mock(ConsoleCommandSender.class);
		context = new NestedCommandContext(arguments, sender, server);
		Assert.assertTrue("When a ConsoleCommandSender is the CommandSender this should return true.", context.isConsoleCommandSender());
	}

	@Test
	public void testIsConsoleCommandSenderWhenPlayer()
	throws Exception {
		Assert.assertFalse("When a Player is the CommandSender this should return false.", context.isConsoleCommandSender());
	}

	@Test
	public void testNormalConstructor() throws Exception {
		setServer(server);
		context = new NestedCommandContext(arguments, player);
	}

	@Test
	public void testSize()
	throws Exception {
		Assert.assertTrue("Arguments size is inconsistent! Expected 2 but got " + context.size(), context.size() == 2);
	}

	@Test
	public void testToString() {
		Assert.assertTrue("toString should have been overriden!", context.toString().contains(NestedCommandContext.class.getSimpleName()));
	}

	private void setServer(Server server) throws Exception {
		Field field = Bukkit.class.getDeclaredField("server");
		field.setAccessible(true);
		field.set(null, server);
	}



}
