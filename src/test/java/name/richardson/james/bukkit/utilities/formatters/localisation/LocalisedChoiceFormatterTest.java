/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 LocalisedChoiceFormatterTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.formatters.localisation;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.formatters.colours.ColourScheme;

/**
 * Created with IntelliJ IDEA. User: james Date: 28/06/13 Time: 23:57 To change this template use File | Settings | File Templates.
 */
public class LocalisedChoiceFormatterTest extends TestCase {

	private LocalisedChoiceFormatter formatter;

	@Test
	public void testGetResourceBundle()
	throws Exception {
		Assert.assertNotNull(formatter.getResourceBundle());
	}

	@Test
	public void testGetMessage()
	throws Exception {
		formatter.setLimits(0, 1, 2);
		formatter.setFormats("no-gremlins", "one-gremlin", "many-gremlins");
		formatter.setArguments(1);
		formatter.setMessage("gremlin-message");
		Assert.assertTrue(formatter.getMessage(), formatter.getMessage().contains("There is one gremlin in the database"));
	}

	@Test
	public void testGetColouredMessage()
	throws Exception {
	 	testGetMessage();
		Assert.assertTrue(formatter.getColouredMessage(ColourScheme.Style.ERROR).contains("§"));
	}

	@Before
	public void setUp()
	throws Exception {
		formatter = new LocalisedChoiceFormatter();
	}
}
