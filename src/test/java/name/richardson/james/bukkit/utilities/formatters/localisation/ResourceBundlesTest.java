/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ResourceBundlesTest.java is part of bukkit-utilities.

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
import org.junit.Test;

public class ResourceBundlesTest extends TestCase {

	@Test
	public void testToString()
	throws Exception {
		Assert.assertTrue(ResourceBundles.MESSAGES.toString().contentEquals("Messages"));
	}

	@Test
	public void testGetBundleName()
	throws Exception {
		Assert.assertTrue(ResourceBundles.MESSAGES.getBundleName().contentEquals("Messages"));
	}

	@Test
	public void testGetBundle()
	throws Exception {
		Assert.assertNotNull(ResourceBundles.MESSAGES.getBundle());
	}

}
