/*
 * Copyright (c) 2013 James Richardson.
 *
 * ReleaseBukkitPluginUpdaterTest.java is part of BukkitUtilities.
 *
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.updater;

import org.bukkit.plugin.PluginDescriptionFile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ReleaseBukkitPluginUpdaterTest extends BukkitDevPluginUpdaterTest {

	@Test
	public void IdentifyCorrectRemoteVersion() {
		assertEquals(getUpdater().getRemoteVersion(), "2.2.0");
	}

	@Override
	public void returnsSuppliedPluginName()
	throws Exception {
		assertEquals(getUpdater().getName(), "BanHammer");
	}

	@Override
	public void returnsSuppliedLocalVersion()
	throws Exception {
		assertEquals(getUpdater().getLocalVersion(), "2.1.0");
	}

	@Before
	public void setUp()
	throws Exception {
		PluginDescriptionFile descriptionFile = new PluginDescriptionFile("BanHammer", "2.1.0", null);
		BukkitDevPluginUpdater updater = new BukkitDevPluginUpdater(descriptionFile, PluginUpdater.Branch.STABLE, PluginUpdater.State.NOTIFY, PROJECT_ID, temporaryFolder.newFolder(), "1.5.2");
		this.setUpdater(updater);
		this.getUpdater().run();
	}

}
