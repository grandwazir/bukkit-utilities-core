/*******************************************************************************
 Copyright (c) 2013 James Richardson.
 
 DefaultCommandInvokerTest.java is part of bukkit-utilities.
 
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

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCommandInvokerTest extends TestCase {

	@Mock
	private Command command;

	private DefaultCommandInvoker invoker;

	@Mock
	private Command nestedCommand;

	@Test
	public void testFallThroughToDefaultCommand()
	throws Exception {
		invoker.onCommand(null, null, null, new String[]{"test"});
		verify(command).execute(Matchers.<Context>anyObject());
		invoker.onTabComplete(null, null, null, new String[]{"blah"});
		verify(command).getArgumentMatches(Matchers.<Context>anyObject());
	}

	@Test
	public void testPassthroughToNestedCommand()
	throws Exception {
		invoker.onCommand(null, null, null, new String[]{"nested"});
		verify(nestedCommand).execute(Matchers.<Context>anyObject());
		invoker.onTabComplete(null, null, null, new String[]{"nested"});
		verify(nestedCommand).getArgumentMatches(Matchers.<Context>anyObject());
	}

	@Before
	public void setUp()
	throws Exception {
		when(command.getName()).thenReturn("normal");
		when(nestedCommand.getName()).thenReturn("nested");
		invoker = new DefaultCommandInvoker(command);
		invoker.addCommand(nestedCommand);
	}
}
