/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 SimplePluginConfigurationTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.persistence.configuration;

import java.util.logging.Level;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.updater.PluginUpdater;

@RunWith(JUnit4.class)
public class SimplePluginConfigurationTest extends AbstractConfigurationTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private SimplePluginConfiguration invalidConfiguration;
	private SimplePluginConfiguration validConfiguration;

	@Test
	public void testIsCollectingStats()
	throws Exception {
		assertEquals("Automatic stat collection should be true!", true, validConfiguration.isCollectingStats());
		assertEquals("Invalid automatic stat collection should default to true!", true, invalidConfiguration.isCollectingStats());
	}

	@Test
	public void testGetLogLevel()
	throws Exception {
		assertEquals("Log level should equal to INFO!", Level.INFO, validConfiguration.getLogLevel());
		assertEquals("Invalid log levels should default to INFO!", Level.INFO, invalidConfiguration.getLogLevel());
	}

	@Test
	public void testGetAutomaticUpdaterState()
	throws Exception {
		assertEquals("Updater state should equal to NOTIFY!", PluginUpdater.State.NOTIFY, validConfiguration.getAutomaticUpdaterState());
		assertEquals("Invalid updater states should default to NOTIFY!", PluginUpdater.State.NOTIFY, invalidConfiguration.getAutomaticUpdaterState());
	}

	@Test
	public void testGetAutomaticUpdaterBranch()
	throws Exception {
		assertEquals("Branch should equal to STABLE!", PluginUpdater.Branch.STABLE, validConfiguration.getAutomaticUpdaterBranch());
		assertEquals("Invalid branch should default to Stable!", PluginUpdater.Branch.STABLE, invalidConfiguration.getAutomaticUpdaterBranch());
	}

	@Before
	public void setUp()
	throws Exception {
		validConfiguration = new SimplePluginConfiguration(folder.newFile("valid.yml"), getClass().getClassLoader().getResourceAsStream("config-valid.yml"));
		invalidConfiguration = new SimplePluginConfiguration(folder.newFile("invalid.yml"), getClass().getClassLoader().getResourceAsStream("config-invalid.yml"));
	}


}
