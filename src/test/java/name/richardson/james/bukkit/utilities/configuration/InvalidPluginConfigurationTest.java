/*
 * Copyright (c) 2014 James Richardson.
 *
 * InvalidPluginConfigurationTest.java is part of BukkitUtilities.
 *
 * bukkit-utilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * bukkit-utilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * bukkit-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.configuration;

import java.util.logging.Level;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.updater.BukkitDevPluginUpdater;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class InvalidPluginConfigurationTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	private PluginConfiguration configuration;

	@Test
	public void whenCollectingStatsValueIsInvalidRevertToDefault()
	throws Exception {
		assertEquals("Invalid automatic stat collection should default to true!", true, configuration.isCollectingStats());
	}

	@Test
	public void whenLogLevelInvalidRevertToDefault()
	throws Exception {
		assertEquals("Invalid log levels should default to INFO!", Level.INFO, configuration.getLogLevel());
	}

	@Test
	public void whenUpdaterStateInvalidRevertToDefault()
	throws Exception {
		assertEquals("Invalid updater states should default to NOTIFY!", BukkitDevPluginUpdater.State.NOTIFY, configuration.getAutomaticUpdaterState());
	}

	@Test
	public void whenUpdaterBranchInvalidRevertToDefault()
	throws Exception {
		assertEquals("Invalid branch should default to Stable!", BukkitDevPluginUpdater.Branch.STABLE, configuration.getAutomaticUpdaterBranch());
	}

	@Before
	public void setUp()
	throws Exception {
		configuration = new SimplePluginConfiguration(folder.newFile("invalid.yml"), getClass().getClassLoader().getResourceAsStream("config-invalid.yml"));
	}

}
