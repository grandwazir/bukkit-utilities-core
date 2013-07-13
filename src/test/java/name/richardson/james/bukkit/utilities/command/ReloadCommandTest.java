/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ReloadCommandTest.java is part of bukkit-utilities.

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

import org.bukkit.command.CommandSender;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.plugin.Reloadable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA. User: james Date: 13/07/13 Time: 08:52 To change this template use File | Settings | File Templates.
 */
public class ReloadCommandTest extends TestCase {

	private ReloadCommand command;
	private CommandContext commandContext;
	private CommandSender commandSender;
	private Reloadable reloadable;

	@Test
	public void testExecuteSuccess()
	throws Exception {
		when(reloadable.reload()).thenReturn(true);
		command.execute(commandContext);
		verify(reloadable).reload();
		verify(commandSender).sendMessage("§aReload successful");
	}

	@Test
	public void testExecuteFailed()
	throws Exception {
		when(reloadable.reload()).thenReturn(false);
		command.execute(commandContext);
		verify(reloadable).reload();
		verify(commandSender).sendMessage("§cReload failed!");
	}


	@Before
	public void setUp()
	throws Exception {
		reloadable = mock(Reloadable.class);
		commandContext = mock(CommandContext.class);
		commandSender = mock(CommandSender.class);
		PermissionManager permissionManager = mock(PermissionManager.class);
		when(commandContext.getCommandSender()).thenReturn(commandSender);
		command = new ReloadCommand(permissionManager, reloadable);
	}
}
