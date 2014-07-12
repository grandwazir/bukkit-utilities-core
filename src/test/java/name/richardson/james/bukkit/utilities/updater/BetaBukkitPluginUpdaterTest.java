/*
 * Copyright (c) 2013 James Richardson.
 *
 * BetaBukkitPluginUpdaterTest.java is part of BukkitUtilities.
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
public class BetaBukkitPluginUpdaterTest extends BukkitDevPluginUpdaterTest {

	private static final Version REMOTE_VERSION = new RemotePluginVersion("2.2.5", null);
	private static final Version LOCAL_VERSION = new PluginVersion("2.1.0");

	@Override
	public void returnsSuppliedBranch()
	throws Exception {
		assertEquals(getUpdater().getBranch(), PluginUpdater.Branch.DEVELOPMENT);
	}

	@Test
	public void IdentifyCorrectRemoteVersion() {
		assertEquals(REMOTE_VERSION, getUpdater().getLatestRemoteVersion());
	}

	@Override
	public void returnsSuppliedPluginName()
	throws Exception {
		assertEquals("BanHammer", getUpdater().getName());
	}

	@Override
	public void returnsSuppliedLocalVersion()
	throws Exception {
		assertEquals(LOCAL_VERSION, getUpdater().getLocalVersion());
	}

	@Before
	public void setUp()
	throws Exception {
		PluginDescriptionFile descriptionFile = new PluginDescriptionFile("BanHammer", "2.1.0", null);
		BukkitDevPluginUpdater updater = new BukkitDevPluginUpdater(descriptionFile, PluginUpdater.Branch.DEVELOPMENT, PluginUpdater.State.NOTIFY, PROJECT_ID, temporaryFolder.newFolder(), "1.5.2");
		this.setUpdater(updater);
		this.getUpdater().run();
	}

}
