/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 JodaTimeFormatterTest.java is part of bukkit-utilities.

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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class JodaTimeFormatterTest extends TestCase {

	private TimeFormatter formatter;

	@Test
	public void getDurationInMilliseconds_ReturnCorrectTime() {
		assertEquals(109815000, formatter.getDurationInMilliseconds("1d6h30m15s"));
	}

	@Test
	public void getHumanReadableDuration_ReturnCorrectTime() {
		assertEquals("1 day, 6 hours, 30 minutes and 15 seconds", formatter.getHumanReadableDuration(109815000));
	}

	@Before
	public void setUp()
	throws Exception {
		formatter = new LocalisedTimeFormatter();
	}

}
