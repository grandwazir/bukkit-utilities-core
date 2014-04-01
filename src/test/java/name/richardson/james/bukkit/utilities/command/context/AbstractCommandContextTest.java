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
	public static final String SWITCH_VALUE = "12345";
	public static final String[] ARGUMENTS = {"This", "is", "a", "test", "argument", "with", "unicode", "chars", EXAMPLE_UNICODE, "-silent", "t:" + SWITCH_VALUE};
	public static final String INVALID_SWITCH = "blah";
	public static final String VALID_SWITCH = "silent";

	private CommandContext commandContext;

	@Test
	public final void commandSenderIsNotNull() {
		assertNotNull("A CommandSender must be assigned to the CommandContext!", getCommandContext().getCommandSender());
	}

	protected CommandContext getCommandContext() {
		return commandContext;
	}

	protected void setCommandContext(CommandContext commandContext) {
		this.commandContext = commandContext;
	}

	@Test
	public abstract void joinAllNormalArgumentsCorrectly();

	@Test
	public abstract void ifArgumentIsValidReturnArgument();

	@Test
	public void joinedArgumentsContainsUnicode() {
		String value = getCommandContext().getJoinedArguments(0);
		assertTrue("Joined arguments have not been parsed correctly!", value.contains(EXAMPLE_UNICODE));
	}

	@Test(expected = InvalidArgumentException.class)
	public final void ifArgumentIsInvalidExpectException() {
		getCommandContext().getArgument(Integer.MAX_VALUE);
	}

	@Test(expected = InvalidArgumentException.class)
	public final void ifSwitchIsInvalidExpectException() {
		getCommandContext().getSwitch(INVALID_SWITCH);
	}

	@Test
	public void returnTrueWhenSwitchIsPresent() {
		assertTrue(getCommandContext().hasSwitch(VALID_SWITCH));
	}

	@Test
	public void returnValueFromSwitchWhenPresent() {
		String value = getCommandContext().getSwitch(VALID_SWITCH);
		assertEquals("Returned value is not the same as switch value!", SWITCH_VALUE, value);
	}

	@Test
	public void toStringIsOverridden() {
		assertTrue("toString() has not been overridden!", commandContext.toString().contains("CommandContext"));
	}

}
