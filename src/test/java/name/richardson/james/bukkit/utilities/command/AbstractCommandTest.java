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

import org.bukkit.permissions.Permissible;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import name.richardson.james.bukkit.utilities.command.matcher.Matcher;
import name.richardson.james.bukkit.utilities.command.matcher.OnlinePlayerMatcher;
import name.richardson.james.bukkit.utilities.formatters.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.permissions.Permissions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractCommandTest extends TestCase {

	private AbstractCommandTestClass command;

	@Mock
	private Permissible permissible;

	@Permissions(permissions = {"test.permission"})
	public class AbstractCommandTestClass extends AbstractCommand {

		@Override
		public void execute(Context context) {
			return;
		}

	}

	@Test
	public void testIsAuthorisedTrue()
	throws Exception {
		when(permissible.hasPermission(org.mockito.Matchers.<String>anyObject())).thenReturn(true);
		Assert.assertTrue(command.isAuthorised(permissible));
	}

	@Test
	public void testIsAuthorisedFalse()
	throws Exception {
		if (permissible == null) System.out.print("is null!");
		when(permissible.hasPermission(org.mockito.Matchers.<String>anyObject())).thenReturn(false);
		Assert.assertFalse(command.isAuthorised(permissible));
	}

	@Test
	public void testGetUsage()
	throws Exception {
		Assert.assertTrue(command.getUsage().contentEquals("<test>"));
	}

	@Test
	public void testGetResourceBundle()
	throws Exception {
		Assert.assertNotNull(command.getResourceBundle());
	}

	@Test
	public void testGetName()
	throws Exception {
		Assert.assertTrue(command.getName().contentEquals("name"));
	}

	@Test
	public void testGetMessage()
	throws Exception {
		Assert.assertTrue(command.getMessage("one-gremlin").contentEquals("is one gremlin"));
	}

	@Test
	public void testGetDescription()
	throws Exception {
		Assert.assertTrue(command.getDescription().contentEquals("description"));
	}

	@Test
	public void testGetColouredMessage()
	throws Exception {
		Assert.assertTrue(command.getColouredMessage(ColourScheme.Style.INFO, "one-gremlin").contains("§"));
	}

	@Test
	public void testGetColourScheme()
	throws Exception {
		Assert.assertNotNull(command.getColourScheme());
	}

	@Test
	public void testGetArgumentMatches()
	throws Exception {
		Matcher matcher = mock(OnlinePlayerMatcher.class);
		command.addMatcher(matcher);
		CommandContext context = new CommandContext(new String[]{"test"}, null);
		Assert.assertTrue(command.getArgumentMatches(context).isEmpty());
		context = new CommandContext(new String[]{"test", "test"}, null);
		Assert.assertTrue(command.getArgumentMatches(context).isEmpty());
		verify(matcher).matches("test");
	}

	@Before
	public void setUp()
	throws Exception {
		command = new AbstractCommandTestClass();
	}
}
