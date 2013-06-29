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

package name.richardson.james.bukkit.utilities.command.invoker;

import java.util.Collections;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.command.Command;
import name.richardson.james.bukkit.utilities.command.context.Context;

/**
 * Created with IntelliJ IDEA. User: james Date: 28/06/13 Time: 19:42 To change this template use File | Settings | File Templates.
 */
public class DefaultCommandInvokerTest extends TestCase {

	private Command command;
	private DefaultCommandInvoker invoker;
	private Command nestedCommand;

	@Test
	public void testOnTabComplete()
	throws Exception {
		EasyMock.expect(command.getArgumentMatches((Context) EasyMock.anyObject())).andReturn(Collections.<String>emptySet()).times(1);
		EasyMock.expect(nestedCommand.getArgumentMatches((Context) EasyMock.anyObject())).andReturn(Collections.<String>emptySet()).times(1);
		EasyMock.expect(nestedCommand.getName()).andReturn("nested").times(1);
		EasyMock.replay(nestedCommand);
		invoker.addCommand(nestedCommand);
		EasyMock.replay(command);
		invoker.onTabComplete(null, null, null, new String[]{"nested"});
		invoker.onTabComplete(null, null, null, new String[]{"test", "blah"});
		EasyMock.verify(command);
	}

	@Test
	public void testOnCommand()
	throws Exception {
		command.execute((Context) EasyMock.anyObject());
		EasyMock.expectLastCall();
		EasyMock.replay(command);
		invoker.onCommand(null, null, null, new String[]{"test"});
		EasyMock.verify(command);
		EasyMock.expect(nestedCommand.getName()).andReturn("nested").times(1);
		nestedCommand.execute((Context) EasyMock.anyObject());
		EasyMock.expectLastCall();
		EasyMock.replay(nestedCommand);
		invoker.addCommand(nestedCommand);
		invoker.onCommand(null, null, null, new String[]{"nested", "hello"});
	}

	@Before
	public void setUp()
	throws Exception {
		command = EasyMock.createMock(Command.class);
		nestedCommand = EasyMock.createMock(Command.class);
		invoker = new DefaultCommandInvoker(command);
	}
}
