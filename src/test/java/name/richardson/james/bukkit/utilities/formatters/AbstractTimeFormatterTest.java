/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractTimeFormatterTest.java is part of BukkitUtilities.

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

import java.util.Date;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public abstract class AbstractTimeFormatterTest extends TestCase {

	private TimeFormatter formatter;

	@Test
	public void getDuration_WhenAllUnitsAreValid_ReturnCorrectTime() {
		assertEquals(131415000, getFormatter().getDurationInMilliseconds("1d12h30m15s"));
	}

	@Test
	public void getDuration_WhenSomeUnitsAreValid_ReturnCorrectTime() {
		assertEquals(45015000, getFormatter().getDurationInMilliseconds("1w12h30m15s"));
	}


	@Test
	public void getDuration_WhenAllUnitsAreValid_ReturnZero() {
		assertEquals(0, getFormatter().getDurationInMilliseconds("fred"));
	}

	protected TimeFormatter getFormatter() {
		return formatter;
	}

	protected void setFormatter(TimeFormatter formatter) {
		this.formatter = formatter;
	}
}
