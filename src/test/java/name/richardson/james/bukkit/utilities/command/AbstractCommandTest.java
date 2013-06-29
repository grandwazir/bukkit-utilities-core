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

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import org.bukkit.permissions.Permissible;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.command.matcher.Matcher;
import name.richardson.james.bukkit.utilities.command.matcher.Matchers;
import name.richardson.james.bukkit.utilities.command.matcher.OnlinePlayerMatcher;
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.context.Context;
import name.richardson.james.bukkit.utilities.formatters.colours.ColourScheme;

public class AbstractCommandTest extends TestCase {

	private static final String[] ARGUMENTS = {};

	private AbstractCommandTestClass command;
	private Context context;

	@Permissions(permissions = {"test.permission"})
	@Matchers(classes = {OnlinePlayerMatcher.class})
	public class AbstractCommandTestClass extends AbstractCommand {

		@Override
		public void execute(Context context) {
			return;
		}

	}

	@Test
	public void testIsAuthorised()
	throws Exception {
		Permissible permissible = EasyMock.createMock(Permissible.class);
		EasyMock.expect(permissible.hasPermission("test.permission")).andReturn(true).times(1);
		EasyMock.expect(permissible.hasPermission("test.permission")).andReturn(false).times(1);
		EasyMock.replay(permissible);
		Assert.assertTrue(command.isAuthorised(permissible));
		Assert.assertFalse(command.isAuthorised(permissible));
		EasyMock.verify(permissible);
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
		Field field = command.getClass().getSuperclass().getDeclaredField("matchers");
		field.setAccessible(true);
		List<Matcher> matchers = (List<Matcher>) field.get(command);
		Matcher matcher = EasyMock.createMock(Matcher.class);
		EasyMock.expect(matcher.matches("test")).andReturn(Collections.<String>emptySet()).times(1);
		matchers.set(0, matcher);
		EasyMock.replay(matcher);
		Assert.assertSame(Collections.emptySet(), command.getArgumentMatches(context));
		context = new CommandContext(new String[]{"test"}, null);
		Assert.assertSame(Collections.emptySet(), command.getArgumentMatches(context));
		EasyMock.verify(matcher);

	}

	@Before
	public void setUp()
	throws Exception {
		command = new AbstractCommandTestClass();
		context = new CommandContext(ARGUMENTS, null);
	}
}
