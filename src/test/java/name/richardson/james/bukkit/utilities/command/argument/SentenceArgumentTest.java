/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 SentenceArgumentTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.argument;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class SentenceArgumentTest extends TestCase {

	private SentenceArgument argument;

	@Before
	public void setUp()
	throws Exception {
		argument = new SentenceArgument();
	}

	@Test
	public void testGetValue()
	throws Exception {
		argument.parseValue("this is a sentence");
		String sentence = argument.getValue();
		Assert.assertTrue("Sentence does not start with captial letter!: " + sentence, sentence.startsWith("T"));
		Assert.assertTrue("Sentence does not end with a full stop!: " + sentence, sentence.endsWith("."));
	}
}
