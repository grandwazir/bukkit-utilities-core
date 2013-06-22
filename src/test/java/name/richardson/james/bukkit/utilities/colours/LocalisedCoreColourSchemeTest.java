/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 LocalisedCoreColourSchemeTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.colours;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.localisation.LocalisedCoreColourScheme;
import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;

public class LocalisedCoreColourSchemeTest extends CoreColourSchemeTest {

	private LocalisedCoreColourScheme colourScheme;

	@Before
	public void setUp()
	throws Exception {
		ResourceBundle bundle = PluginResourceBundle.getBundle(this);
		this.colourScheme = new LocalisedCoreColourScheme(bundle);
	}

	@Test
	public void testFormat()
	throws Exception {
		for (ColourScheme.Style style : ColourScheme.Style.values()) {
			String message = this.colourScheme.format(style, "test-without-arguments");
			Assert.assertTrue("String does not contain a colour", message.contains("§"));
			Assert.assertTrue("String not translated:" + message, message.replaceAll("§.{1}", "").contentEquals("Hello!"));
		}
		Pattern p = Pattern.compile("(§.{1}).*(§.{1}).*(§.{1})");
		for (ColourScheme.Style style : ColourScheme.Style.values()) {
			String message = this.colourScheme.format(style, "test-with-arguments", "grandwazir");
			Matcher matcher = p.matcher(message);
			matcher.find();
			Assert.assertTrue("String does not contain three colour types: " + message, matcher.groupCount() == 3);
			Assert.assertFalse("Arguments are not being coloured", matcher.group(2).contentEquals(matcher.group(1)));
			Assert.assertTrue("String not translated:" + message, message.replaceAll("§.{1}", "").contentEquals("Hello grandwazir!"));
		}
	}

	@Test
	public void testGetResourceBundle()
	throws Exception {
		Assert.assertNotNull(this.colourScheme.getResourceBundle());
	}

	@Test
	public void testGetMessage() {
		Assert.assertEquals(this.colourScheme.getMessage("test-without-arguments"), "Hello!");
	}
}
