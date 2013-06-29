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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.BukkitTestFixture;
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.context.Context;

public class CommandContextTest extends TestCase {

	private static final String[] ARGUMENTS = {"grandwazir", "this", "is", "t:1234", "a", "reason"};

	private CommandSender sender;
	private Context context;
	private Server server;

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
		Player player = EasyMock.createMock(Player.class);
		EasyMock.expect(server.getPlayer("grandwazir")).andReturn(player).times(1);
		EasyMock.expect(server.getPlayer("this")).andReturn(null).atLeastOnce();
		EasyMock.replay(server);
		Field field = context.getClass().getDeclaredField("SERVER");
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, server);
		context.getPlayer(0);
		context.getPlayer(1);
		EasyMock.verify(server);
	}

	@Test
	public void testGetOfflinePlayer()
	throws Exception {
		OfflinePlayer player = EasyMock.createMock(OfflinePlayer.class);
		EasyMock.expect(server.getOfflinePlayer("grandwazir")).andReturn(player).times(1);
		EasyMock.expect(server.getOfflinePlayer("this")).andReturn(null).atLeastOnce();
		EasyMock.replay(server);
		Field field = context.getClass().getDeclaredField("SERVER");
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, server);
		context.getOfflinePlayer(0);
		context.getOfflinePlayer(1);
		EasyMock.verify(server);
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
		server = BukkitTestFixture.getServer();
		BukkitTestFixture.setServer(server);
		sender = EasyMock.createMock(Player.class);
		context = new CommandContext(ARGUMENTS, sender);
	}

	@After
	public void tearDown()
	throws NoSuchFieldException, IllegalAccessException {
		BukkitTestFixture.setServer(null);
	}

}
