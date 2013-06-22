/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 BooleanMatcherTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.matchers;

import java.util.List;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BooleanMatcherTest extends TestCase {

	private BooleanMatcher matcher;

	@Before
	public void setUp()
	throws Exception {
		this.matcher = new BooleanMatcher();
	}

	@Test
	public void testGetMatches()
	throws Exception {
		List<String> matches = matcher.getMatches("tru");
		Assert.assertTrue("List does not contain 'true'", matches.contains("true"));
		Assert.assertTrue(matches.size() == 1);
	}

	@Test
	public void testGetBlankMatches()
	throws Exception {
		List<String> matches = matcher.getMatches("");
		Assert.assertTrue("List does not contain 'true'", matches.contains("true"));
		Assert.assertTrue("List does not contain 'false'", matches.contains("false"));
		Assert.assertTrue(matches.size() == 2);
	}

	@Test
	public void testCaseInsensitiveMatch()
	throws Exception {
		List<String> matches = matcher.getMatches("FAL");
		Assert.assertTrue("List does not contain 'false'", matches.contains("false"));
		Assert.assertTrue(matches.size() == 1);
	}
}
