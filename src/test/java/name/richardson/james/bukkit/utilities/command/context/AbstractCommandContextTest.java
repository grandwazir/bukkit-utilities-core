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

import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public abstract class AbstractCommandContextTest extends TestCase {

	public static final String EXAMPLE_UNICODE = "tämä";
	public static final String[] ARGUMENTS = {"This", "is", "a", "test", "argument", "with", "unicode", "chars", EXAMPLE_UNICODE, "-silent", "t:12345"};
	public static final String INVALID_FLAG = "blah";
	public static final String SWITCH_FLAG_NAME = "silent";
	private CommandContext commandContext;

	@Test
	public void getCommandSenderwhenContextCreatedReturnsNotNull() {
		assertNotNull(getCommandContext().getCommandSender());
	}

	protected CommandContext getCommandContext() {
		return commandContext;
	}

	protected void setCommandContext(CommandContext commandContext) {
		this.commandContext = commandContext;
	}

	@Test
	public void getFlagWhenFlagIsInvalidReturnNull() {
		assertNull(getCommandContext().getFlag(INVALID_FLAG));
	}

	@Test
	public void getFlagwhenFlagIsSwitchReturnNull() {
		assertNull(getCommandContext().getFlag(SWITCH_FLAG_NAME));
	}

	@Test
	public void getJoinedArgumentsWhenMixedContextPassedReturnedStringOnlyIncludesArguments() {
		String joinedArguments = StringUtils.join(ARGUMENTS, " ", 0, ARGUMENTS.length - 2);
		assertEquals(joinedArguments, getCommandContext().getJoinedArguments(0));
	}

	@Test
	public void getStringWhenRequestedMethodIsOverriden() {
		assertTrue(getCommandContext().toString().contains("CommandContext"));
	}

	@Test
	public void getStringwhenIndexInvalidReturnNull() {
		assertNull(getCommandContext().getString(99));
	}

	@Test
	public void getStringwhenIndexValidReturnString() {
		assertNotNull(getCommandContext().getString(0));
	}

	@Test
	public void returnFalseWhenSwitchIsNotPresent() {
		assertFalse(getCommandContext().hasSwitch(INVALID_FLAG));
	}

	@Test
	public void returnTrueWhenSwitchIsPresent() {
		assertTrue(getCommandContext().hasSwitch(SWITCH_FLAG_NAME));
	}

	@Test
	public void sizeWhenArgumentsPassedReturnCorrectSize() {
		assertEquals(ARGUMENTS.length - 2, getCommandContext().size());
	}

	@Test
	public void whenStringContainsUnicodeDoNotStripUnicodeChars() {
		assertTrue(getCommandContext().getJoinedArguments(0).contains(EXAMPLE_UNICODE));
	}

}
