/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 StringMatcherTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.matcher;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class StringMatcherTest extends TestCase {

	private final String[] commandNames = {"ban", "kick", "remove", "kicked"};
	private StringMatcher matcher;

	@Test
	public void testInvalidMatch() {
		Set<String> results = matcher.matches("fr");
		assertTrue("A match should not have been returned!", results.size() == 0);
	}

	@Test
	public void testValidMatch() {
		Set<String> results = matcher.matches("kiC");
		assertTrue("A match should have been returned!", results.contains("kick"));
		assertTrue("Two matches should have been returned!", results.size() == 2);
	}

	@Before
	public void setUp()
	throws Exception {
		List<String> commandNames = Arrays.asList(this.commandNames);
		this.matcher = new StringMatcher(commandNames);
	}

}
