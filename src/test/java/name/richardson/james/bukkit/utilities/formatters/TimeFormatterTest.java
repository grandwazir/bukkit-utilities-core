/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TimeFormatterTest.java is part of bukkit-utilities.

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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TimeFormatterTest extends TestCase {

	@Test(expected = NumberFormatException.class)
	public void testParseTime()
	throws Exception {
		Assert.assertTrue(TimeFormatter.parseTime("1d") == 86400000);
		Assert.assertTrue(TimeFormatter.parseTime("12m") == 60000 * 12);
		Assert.assertTrue(TimeFormatter.parseTime("1d12h30m15s") == 86400000 + 43200000 + 1800000 + 15000);
		Assert.assertTrue(TimeFormatter.parseTime("1w") == 86400000 * 7);
		Assert.assertTrue(TimeFormatter.parseTime("1y") == 0);
	}

	@Test
	public void testMillisToLongDHMS()
	throws Exception {
		String message = TimeFormatter.millisToLongDHMS((86400000 * 7) + 86400000 + 43200000 + 1800000 + 15000);
		Assert.assertTrue(message, message.contentEquals("8 days, 12 hours, 30 minutes and 15 seconds"));
		Assert.assertTrue(TimeFormatter.millisToLongDHMS(0).contentEquals("0 second"));
	}
}
