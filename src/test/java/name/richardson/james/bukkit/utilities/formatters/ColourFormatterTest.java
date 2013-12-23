/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ColourFormatterTest.java is part of BukkitUtilities.

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

package name.richardson.james.bukkit.utilities.formatters;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public abstract class ColourFormatterTest extends TestCase {

	private ColourFormatter colourFormatter;

	@Test
	public void format_WhenMessageAndArugmentsSupplied_AddColourAndInsertArguments()
	throws Exception {
		for (ColourFormatter.FormatStyle style : ColourFormatter.FormatStyle.values()) {
			String message = getColourFormatter().getMessage("Hello {0}!", style, "James");
			assertTrue(message.contains("James"));
			assertTrue(message.contains("§"));
		}
	}

	@Test
	public void format_WhenMessageSupplied_AddColour()
	throws Exception {
		for (ColourFormatter.FormatStyle style : ColourFormatter.FormatStyle.values()) {
			assertTrue(getColourFormatter().getMessage("Hello", style).contains("§"));
		}
	}

	protected ColourFormatter getColourFormatter() {
		return colourFormatter;
	}

	protected void setColourFormatter(ColourFormatter colourFormatter) {
		this.colourFormatter = colourFormatter;
	}

}
