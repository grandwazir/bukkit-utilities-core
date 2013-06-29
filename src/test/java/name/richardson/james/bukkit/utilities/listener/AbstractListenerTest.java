/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractListenerTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA. User: james Date: 28/06/13 Time: 23:21 To change this template use File | Settings | File Templates.
 */
public class AbstractListenerTest extends TestCase {

	public class AbstractListenerTestClass extends AbstractListener {

		public AbstractListenerTestClass(Plugin plugin, PluginManager pluginManager) {
			super(plugin, pluginManager);
		}
	}

	@Test
	public void testCreation() {
		PluginManager pluginManager = EasyMock.createNiceMock(PluginManager.class);
		pluginManager.registerEvents((Listener) EasyMock.anyObject(), (Plugin) EasyMock.anyObject());
		EasyMock.expectLastCall();
		EasyMock.replay(pluginManager);
		new AbstractListener(null, pluginManager);
		EasyMock.verify(pluginManager);
	}

}
