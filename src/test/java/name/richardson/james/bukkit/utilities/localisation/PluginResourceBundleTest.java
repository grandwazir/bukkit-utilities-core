/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PluginResourceBundleTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.localisation;

import java.util.ResourceBundle;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

public class PluginResourceBundleTest extends TestCase {

	@Test
	public void testGetBundle()
	throws Exception {
		ResourceBundle bundle = PluginResourceBundle.getBundle(this.getClass());
		Assert.assertNotNull(bundle);
	}

	@Test
	public void testGetBundleName()
	throws Exception {
		String bundleName = PluginResourceBundle.getBundleName(this.getClass());
		Assert.assertTrue("ResourceBundle name is inconsistent: " + bundleName, bundleName.contentEquals("localisation.localisation.PluginResourceBundleTest"));
	}

	@Test
	public void testExists()
	throws Exception {
		Assert.assertTrue(PluginResourceBundleTest.class.getName() + " should exist!", PluginResourceBundle.exists(PluginResourceBundleTest.class));
	}

	@Test
	public void testDoesNotExists()
	throws Exception {
		Assert.assertFalse(PluginResourceBundle.class.getName() + " should not exist!", PluginResourceBundle.exists(PluginResourceBundle.class));
	}
}
