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

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;

@RunWith(JUnit4.class)
public class ResourceBundleLocalisationTest {

	private ResourceBundleLocalisation localisation;

	@Test
	public final void getLocalisedMessageWithArguments()
	throws Exception {
		Assert.assertEquals(localisation.getMessage("test-message-with-arguments", "James"), "Hello James!");
	}

	@Test
	public final void getLocalisedMessage()
	throws Exception {
		Assert.assertEquals(localisation.getMessage("test-message"), "Hello!");
	}

	@Test
	public final void getColouredLocalisedMessageWithArguments()
	throws Exception {
		Assert.assertEquals(localisation.getMessage("test-message-with-arguments", ColourFormatter.FormatStyle.INFO, "James"), "§aHello §bJames§a!");
	}

	@Test
	public final void getColouredLocalisedMessage()
	throws Exception {
		Assert.assertEquals(localisation.getMessage("test-message", ColourFormatter.FormatStyle.INFO), "§aHello!");
	}

	@Before
	public void setUp()
	throws Exception {
		localisation = new ResourceBundleLocalisation();
	}
}
