/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractCommandTest.java is part of bukkit-utilities.

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

import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.permissions.Permissible;

import junit.framework.Assert;
import junit.framework.Assert.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.matcher.Matcher;
import name.richardson.james.bukkit.utilities.formatters.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.formatters.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.permissions.Permissions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AbstractCommandTest extends TestCase {

	private AbstractCommand command;
	private PermissionManager permissionManager;

	@Permissions(permissions = "test")
	public class AbstractCommandTestClass extends AbstractCommand {

		public AbstractCommandTestClass(PermissionManager permissionManager) {
			super(permissionManager);
		}

		@Override
		public void execute(CommandContext commandContext) {
			return;
		}

	}

	@Test
	public void testIsAuthorisedFalse()
	throws Exception {
		Permissible permissible = mock(Permissible.class);
		assertFalse("IsAuthorised should should return false when permissible has no permissions!", command.isAuthorised(permissible));
		verify(permissible).hasPermission("test");
	}

	@Test
	public void testIsAuthorisedTrue()
	throws Exception {
		Permissible permissible = mock(Permissible.class);
		when(permissible.hasPermission("test")).thenReturn(true);
		assertTrue("IsAuthorised should should return true when permissible is authorized!", command.isAuthorised(permissible));
		verify(permissible).hasPermission("test");
	}

	@Test
	public void testGetUsage()
	throws Exception {
		assertTrue("Command usage text should be `<test>` but instead got " + command.getUsage(), command.getUsage().contentEquals("<test>"));
	}

	@Test
	public void testGetResourceBundle()
	throws Exception {
		Assert.assertEquals("Resource bundle should be the same as the Messages bundle!", command.getResourceBundle(), ResourceBundles.MESSAGES.getBundle());
	}

	@Test
	public void testGetName()
	throws Exception {
		assertTrue("Command name should be `test` but instead got " + command.getName(), command.getName().contentEquals("test"));
	}

	@Test
	public void testGetMessage()
	throws Exception {
		assertTrue("Message does not appear to have a colour code within it!", command.getMessage("test-message").contentEquals("Testing!"));
	}

	@Test
	public void testGetDescription()
	throws Exception {
		assertTrue("Command description should be `description` but instead got " + command.getDescription(), command.getDescription().contentEquals("description"));
	}

	@Test
	public void testGetColouredMessage()
	throws Exception {
		assertTrue("Message does not appear to have a colour code within it!", command.getColouredMessage(ColourScheme.Style.INFO, "test-message").contains("§"));
	}

	@Test
	public void testGetColourScheme()
	throws Exception {
	 	assertNotNull("Each command should have a colour scheme associated to it!", command.getColourScheme());
	}

	@Test
	public void testGetArgumentMatchesNoMatchers()
	throws Exception {
		CommandContext commandContext = mock(CommandContext.class);
		assertTrue("A command with no matchers should always return an empty list!", command.getArgumentMatches(commandContext).isEmpty());
	}

	@Test
	public void testGetArgumentMatchesWithMatchers()
	throws Exception {
		CommandContext commandContext = mock(CommandContext.class);
		Matcher matcher = mock(Matcher.class);
		command.addMatcher(matcher);
		when(commandContext.size()).thenReturn(1);
		when(commandContext.getString(0)).thenReturn("test");
		assertTrue("Command matcher should be consulted!", command.getArgumentMatches(commandContext).isEmpty());
		verify(matcher).matches("test");
	}

	@Before
	public void setUp()
	throws Exception {
		permissionManager = mock(PermissionManager.class);
		command = new AbstractCommandTestClass(permissionManager);
	}
}
