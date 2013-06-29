/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 LocalisedExceptionTest.java is part of bukkit-utilities.

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

/**
 * Created with IntelliJ IDEA. User: james Date: 29/06/13 Time: 00:38 To change this template use File | Settings | File Templates.
 */
public class LocalisedExceptionTest extends TestCase {

	public class LocalisedExceptionTestClass extends LocalisedException {

		public LocalisedExceptionTestClass(String key, Object... arguments) {
			super(key, arguments);
		}
	}

	@Test
	public void testException() {
		LocalisedException exception = new LocalisedExceptionTestClass("gremlin-message", "one-gremlin");
		Assert.assertNotNull(exception.getResourceBundle());
		Assert.assertTrue(exception.getLocalizedMessage(), exception.getLocalizedMessage().contentEquals("There one-gremlin in the database."));
		Assert.assertTrue(exception.getMessage().contains(exception.getLocalizedMessage()));
		Assert.assertTrue(exception.getMessage(), exception.getMessage("gremlin-message", "one-gremlin").contentEquals("There one-gremlin in the database."));
	}
}
