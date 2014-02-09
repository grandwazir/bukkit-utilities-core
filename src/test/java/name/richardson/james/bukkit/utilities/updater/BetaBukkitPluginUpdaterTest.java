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

import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BetaBukkitPluginUpdaterTest extends BukkitDevPluginUpdaterTest {

	private static final ArtifactVersion REMOTE_VERSION = new DefaultArtifactVersion("2.2.5");
	private static final ArtifactVersion LOCAL_VERSION = new DefaultArtifactVersion("2.1.0");

	@Override
	public void returnsSuppliedBranch()
	throws Exception {
		assertEquals(getUpdater().getBranch(), PluginUpdater.Branch.DEVELOPMENT);
	}

	@Test
	public void IdentifyCorrectRemoteVersion() {
		assertEquals(getUpdater().getLatestRemoteVersion(), REMOTE_VERSION);
	}

	@Override
	public void returnsSuppliedPluginName()
	throws Exception {
		assertEquals(getUpdater().getName(), "BanHammer");
	}

	@Override
	public void returnsSuppliedLocalVersion()
	throws Exception {
		assertEquals(getUpdater().getLocalVersion(), LOCAL_VERSION);
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
