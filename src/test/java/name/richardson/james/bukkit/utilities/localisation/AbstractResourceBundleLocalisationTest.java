/*
 * Copyright (c) 2013 James Richardson.
 *
 * ResourceBundleLocalisationTest.java is part of BukkitUtilities.
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

package name.richardson.james.bukkit.utilities.localisation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public abstract class AbstractResourceBundleLocalisationTest {

	private static final String TEST_MESSAGE_KEY = "test-message";
	private static final String TEST_MESSAGE_WITH_ARGUMENTS_KEY = "test-message-with-arguments";
	private static final String TEST_MESSAGE_ARGUMENT = "James";
	private static final String TEST_MESSAGE_WITH_ARGUMENTS = "Hello James!";
	private static final String TEST_MESSAGE = "Hello!";

	public AbstractResourceBundleLocalisation getLocalisation() {
		return localisation;
	}

	public void setLocalisation(AbstractResourceBundleLocalisation localisation) {
		this.localisation = localisation;
	}

	private AbstractResourceBundleLocalisation localisation;

	@Test
	public final void getFormattedErrorMessage() {
		assertFormattedMessage(localisation.formatAsErrorMessage(TEST_MESSAGE_KEY));
	}

	@Test
	public final void getFormattedErrorMessageWithArguments() {
		assertFormattedMessageWithArguments(localisation.formatAsErrorMessage(TEST_MESSAGE_WITH_ARGUMENTS_KEY, TEST_MESSAGE_ARGUMENT));
	}

	@Test
	public final void getFormattedHeaderMessage() {
		assertFormattedMessage(localisation.formatAsHeaderMessage(TEST_MESSAGE_KEY));
	}

	@Test
	public final void getFormattedHeaderMessageWithArguments() {
		assertFormattedMessageWithArguments(localisation.formatAsHeaderMessage(TEST_MESSAGE_WITH_ARGUMENTS_KEY, TEST_MESSAGE_ARGUMENT));
	}

	@Test
	public final void getFormattedInfoMessage() {
		assertFormattedMessage(localisation.formatAsInfoMessage(TEST_MESSAGE_KEY));
	}

	@Test
	public final void getFormattedInfoMessageWithArguments() {
		assertFormattedMessageWithArguments(localisation.formatAsInfoMessage(TEST_MESSAGE_WITH_ARGUMENTS_KEY, TEST_MESSAGE_ARGUMENT));
	}

	@Test
	public final void getFormattedWarningMessage() {
		assertFormattedMessage(localisation.formatAsWarningMessage(TEST_MESSAGE_KEY));
	}

	@Test
	public final void getFormattedWarningMessageWithArguments() {
		assertFormattedMessageWithArguments(localisation.formatAsWarningMessage(TEST_MESSAGE_WITH_ARGUMENTS_KEY, TEST_MESSAGE_ARGUMENT));
	}

	@Test
	public final void getLocalisedMessage()
	throws Exception {
		assertEquals(givenLocalisedMessage(), TEST_MESSAGE);
	}

	@Test
	public final void getLocalisedMessageWithArguments()
	throws Exception {
		assertEquals(givenLocalisedMessageWithArguments(), TEST_MESSAGE_WITH_ARGUMENTS);
	}

	private final void assertFormattedMessage(String formattedMessage) {
		assertEquals(formattedMessage.replaceAll("§.", ""), TEST_MESSAGE);
		assertTrue(formattedMessage.contains("§"));
	}

	private final void assertFormattedMessageWithArguments(String formattedMessage) {
		assertEquals(formattedMessage.replaceAll("§.", ""), TEST_MESSAGE_WITH_ARGUMENTS);
		assertTrue(formattedMessage.contains("§"));
	}

	private final String givenLocalisedMessage() {
		return localisation.getMessage(TEST_MESSAGE_KEY);
	}

	private final String givenLocalisedMessageWithArguments() {
		return localisation.getMessage(TEST_MESSAGE_WITH_ARGUMENTS_KEY, TEST_MESSAGE_ARGUMENT);
	}
}
