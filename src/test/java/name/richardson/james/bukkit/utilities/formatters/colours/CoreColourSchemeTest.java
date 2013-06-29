/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CoreColourSchemeTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.formatters.colours;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class CoreColourSchemeTest extends TestCase {

	private ColourScheme colourScheme;

	@Before
	public void setUp()
	throws Exception {
		this.colourScheme = new CoreColourScheme();
	}

	@Test
	public void testFormat()
	throws Exception {
		for (ColourScheme.Style style : ColourScheme.Style.values()) {
			String message = this.colourScheme.format(style, "Hello!");
			if (style == ColourScheme.Style.COMMAND_USAGE) continue;
			Assert.assertTrue("String does not contain a colour: " + message, message.contains("§"));
		}
		Pattern p = Pattern.compile("(§.{1}).*(§.{1}).*(§.{1})");
		for (ColourScheme.Style style : ColourScheme.Style.values()) {
			if (style == ColourScheme.Style.COMMAND_USAGE) continue;
			String message = this.colourScheme.format(style, "Hello {0}!", "grandwazir");
			Matcher matcher = p.matcher(message);
			matcher.find();
			Assert.assertTrue("String does not contain three colour types: " + message, matcher.groupCount() == 3);
			Assert.assertFalse("ArgumentParser are not being coloured", matcher.group(2).contentEquals(matcher.group(1)));
		}
	}

}
