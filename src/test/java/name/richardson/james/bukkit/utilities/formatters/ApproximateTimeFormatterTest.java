/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ApproximateTimeFormatterTest.java is part of BukkitUtilities.

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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.time.ApproximateTimeFormatter;

@RunWith(JUnit4.class)
public class ApproximateTimeFormatterTest extends AbstractTimeFormatterTest {

	@Test
	public void getHumanReadableTime_WhenTimeInFuture_GetApproximateRemainingTime() {
		assertEquals("2 days from now", getFormatter().getHumanReadableDuration(System.currentTimeMillis() + 131415000));
	}

	@Test
	public void getHumanReadableTime_WhenTimeInPast_GetApproximateElapsedTime() {
		assertEquals("2 days ago", getFormatter().getHumanReadableDuration(System.currentTimeMillis() - 131415000));
	}

	@Before
	public void setUp()
	throws Exception {
		setFormatter(new ApproximateTimeFormatter());
	}
}
