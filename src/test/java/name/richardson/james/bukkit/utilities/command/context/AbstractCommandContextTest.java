/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PassthroughCommandContextTest.java is part of bukkit-utilities.

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

import org.bukkit.command.CommandSender;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public abstract class AbstractCommandContextTest extends TestCase {

	private final String[] ARGUMENTS = {"This", "is", "a", "test", "argument", "-silent", "t:12345"};
	private final CommandSender commandSender = mock(CommandSender.class);
	private CommandContext commandContext;

	@Test
	public void getCommandSender_whenContextCreated_ReturnsNotNull() {
		assertNotNull(getCommandContext().getCommandSender());
	}

	@Test
	public void getFlag_WhenFlagIsInvalid_ReturnNull() {
		assertNull(getCommandContext().getFlag("blah"));
	}

	@Test
	public void getFlag_whenFlagIsSwitch_ReturnNull() {
		assertNull(getCommandContext().getFlag("silent"));
	}

	@Test
	public void getString_WhenRequested_MethodIsOverriden() {
		assertTrue(getCommandContext().toString().contains("CommandContext"));
	}

	@Test
	public void getJoinedArguments_WhenMixedContextPassed_ReturnedStringOnlyIncludesArguments() {
		assertEquals("This is a test argument", getCommandContext().getJoinedArguments(0));
	}

	@Test
	public void getString_whenIndexInvalid_ReturnNull() {
		assertNull(getCommandContext().getString(99));
	}

	@Test
	public void getString_whenIndexValid_ReturnString() {
		assertNotNull(getCommandContext().getString(0));
	}

	@Test
	public void hasFlag_WhenFlagIsInvalid_ReturnFalse() {
		assertFalse(getCommandContext().hasFlag("blah"));
	}

	@Test
	public void hasFlag_WhenFlagIsSwitch_ReturnTrue() {
		assertTrue(getCommandContext().hasFlag("silent"));
	}

	@Test
	public void size_WhenArgumentsPassed_ReturnCorrectSize() {
		assertEquals(5, getCommandContext().size());
	}

	protected String[] getArguments() {
		return ARGUMENTS;
	}

	protected CommandContext getCommandContext() {
		return commandContext;
	}

	protected void setCommandContext(CommandContext commandContext) {
		this.commandContext = commandContext;
	}

	protected CommandSender getCommandSender() {
		return commandSender;
	}

}
