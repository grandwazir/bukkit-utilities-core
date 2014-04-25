/*
 * Copyright (c) 2013 James Richardson.
 *
 * DefaultMessageFormatterTest.java is part of BukkitUtilities.
 *
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.formatters;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DefaultMessageFormatterTest extends TestCase {

	private static final String TEST_MESSAGE = "Hello!";
	private static final String TEST_MESSAGE_WITH_ARGUMENTS = "Hello {0}!";

	private DefaultMessageFormatter formatter;

	@Test
	public void getFormattedHeaderMessage() {
		assertMessageContainsOnlyOneColourCodeAtStart(formatter.asHeaderMessage(TEST_MESSAGE));
	}

	@Test
	public void getFormattedHeaderMessageWithArguments() {
		assertMessageWrapsArgumentsWithColourCodes(formatter.asHeaderMessage(TEST_MESSAGE_WITH_ARGUMENTS));
	}

	@Test
	public void getFormattedInfoMessage() {
		assertMessageContainsOnlyOneColourCodeAtStart(formatter.asInfoMessage(TEST_MESSAGE));
	}

	@Test
	public void getFormattedInfoMessageWithArguments() {
		assertMessageWrapsArgumentsWithColourCodes(formatter.asInfoMessage(TEST_MESSAGE_WITH_ARGUMENTS));
	}

	@Test
	public void getFormattedWarningMessage() {
		assertMessageContainsOnlyOneColourCodeAtStart(formatter.asWarningMessage(TEST_MESSAGE));
	}

	@Test
	public void getFormattedWarningMessageWithArguments() {
		assertMessageWrapsArgumentsWithColourCodes(formatter.asWarningMessage(TEST_MESSAGE_WITH_ARGUMENTS));
	}

	@Test
	public void getFormattedErrorMessage() {
		assertMessageContainsOnlyOneColourCodeAtStart(formatter.asErrorMessage(TEST_MESSAGE));
	}

	@Test
	public void getFormattedErrorMessageWithArguments() {
		assertMessageWrapsArgumentsWithColourCodes(formatter.asErrorMessage(TEST_MESSAGE_WITH_ARGUMENTS));
	}


	private void assertMessageContainsOnlyOneColourCodeAtStart(String message) {
		assertTrue(message.startsWith("§"));
		message = message.replaceFirst("§", "");
		assertFalse(message.contains("§"));
	}

	private void assertMessageWrapsArgumentsWithColourCodes(String message) {
	 	String argumentSubString = message.substring(8,15);
		assertNotSame(argumentSubString.substring(0,2), argumentSubString.substring(4));
	}

	@Before
	public void setup() {
		formatter = new DefaultMessageFormatter();
	}

}
