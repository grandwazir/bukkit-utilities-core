/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CommandInvokerTest.java is part of bukkit-utilities.

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
public class CommandInvokerTest extends TestCase {

	@Mock
	private Command command;
	private CommandInvoker invoker;

	@Test
	public void testOnTabComplete()
	throws Exception {
		invoker.addCommand(command);
		invoker.onTabComplete(null, null, null, new String[]{""});
		invoker.onTabComplete(null, null, null, new String[]{"test"});
		verify(command).getArgumentMatches(Matchers.<Context>anyObject());
	}

	@Test
	public void testOnCommand()
	throws Exception {
		invoker.addCommand(command);
		invoker.onCommand(null, null, null, new String[]{});
		invoker.onCommand(null, null, null, new String[]{"test"});
		verify(command).execute(Matchers.<Context>anyObject());
	}

	@Test
	public void testAddCommand()
	throws Exception {
		invoker.addCommand(command);
		assertSame("Command has not been added to the invoker!", invoker.getCommands().get("test"), command);
	}

	@Before
	public void setUp()
	throws Exception {
		when(command.getName()).thenReturn("test");
		invoker = new CommandInvoker();
	}

}
