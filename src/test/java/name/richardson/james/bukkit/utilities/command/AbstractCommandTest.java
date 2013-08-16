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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.bukkit.permissions.Permissible;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.matcher.Matcher;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class AbstractCommandTest extends CommandTestCase {

	private TestCommand command;

	public class TestCommand extends AbstractCommand {

		@Override
		public void execute(CommandContext commandContext) {
			return;
		}

		@Override
		public boolean isAuthorised(Permissible permissible) {
			return false;
		}

	}

	@Test
	public void addMatcher_whenAdding_IsAddedToCollection() {
		Matcher dummyMatcher = mock(Matcher.class);
		command.addMatcher(dummyMatcher);
		Assert.assertTrue(command.getMatchers().contains(dummyMatcher));
	}

	@Test
	public void getArgumentMatches_whenNoArguments_ReturnEmptySet() {
		CommandContext commandContext = getCommandContext();
		Assert.assertEquals(command.getArgumentMatches(commandContext), Collections.EMPTY_SET);
	}

	@Test
	public void getArgumentMatches_whenNoMatcher_ReturnEmptySet() {
		CommandContext commandContext = getCommandContext();
		configureCommandContext(commandContext);
		Assert.assertEquals(command.getArgumentMatches(commandContext), Collections.EMPTY_SET);
	}

	@Test
	public void getArgumentMatches_whenMatcherFound_ReturnSet() {
		CommandContext commandContext = getCommandContext();
		configureCommandContext(commandContext);
		Matcher matcher = getMockMatcher();
		command.addMatcher(matcher);
		Assert.assertEquals(command.getArgumentMatches(commandContext), new HashSet<String>(Arrays.asList("1", "2", "3")));
	}


	private void configureCommandContext(CommandContext commandContext) {
		when(commandContext.size()).thenReturn(1);
		when(commandContext.has(anyInt())).thenReturn(true);
		when(commandContext.getString(anyInt())).thenReturn("test");
	}

	private Matcher getMockMatcher() {
		Matcher matcher = mock(Matcher.class);
		when(matcher.matches(anyString())).thenReturn(new HashSet<String>(Arrays.asList("1", "2", "3")));
		return matcher;
	}

	@Before
	public void setUp()
	throws Exception {
		command = new TestCommand();
	}
}
