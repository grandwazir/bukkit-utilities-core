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

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AbstractListenerTest extends TestCase {

	private AbstractListenerTestClass listener;
	private Plugin plugin;
	private PluginManager pluginManager;

	public class AbstractListenerTestClass extends AbstractListener {

		public AbstractListenerTestClass(Plugin plugin, PluginManager pluginManager) {
			super(plugin, pluginManager);
		}

	}

	@Test
	public void testListenerRegistered() {
		verify(pluginManager).registerEvents(listener, plugin);
	}

	@Before
	public void setUp() {
		plugin = mock(Plugin.class);
		pluginManager = mock(PluginManager.class);
		listener = new AbstractListenerTestClass(plugin, pluginManager);
	}


}
