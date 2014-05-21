/*
 * Copyright (c) 2014 James Richardson.
 *
 * DefaultDatabaseConfigurationTest.java is part of BukkitUtilities.
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

import java.io.IOException;

import com.avaje.ebean.config.ServerConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DefaultDatabaseConfigurationTest extends AbstractDatabaseConfigurationTest {

	private static final String TEMPLATE_YML = "database-empty.yml";

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	private DatabaseConfiguration configuration;
	private ServerConfig serverConfig;

	@Test
	public void doesDriverMatchServerConfig() {
		assertTrue(serverConfig.getDataSourceConfig().getDriver().contentEquals(configuration.getDataSourceConfig().getDriver()));
	}

	@Test
	public void doesIsolationLevelMatchServerConfig() {
		assertTrue(serverConfig.getDataSourceConfig().getIsolationLevel() == configuration.getDataSourceConfig().getIsolationLevel());
	}

	@Test
	public void doesPasswordMatchServerConfig() {
		assertTrue(serverConfig.getDataSourceConfig().getPassword().contentEquals(configuration.getDataSourceConfig().getPassword()));
	}

	@Test
	public void doesUsernameMatchServerConfig() {
		assertTrue(serverConfig.getDataSourceConfig().getUsername().contentEquals(configuration.getDataSourceConfig().getUsername()));
	}

	@Test
	public void haveDatabaseURLPlaceholdersBeenReplaced()
	throws IOException {
		assertFalse(configuration.getServerConfig().getDataSourceConfig().getUrl().contains("DIR"));
		assertFalse(configuration.getServerConfig().getDataSourceConfig().getUrl().contains("NAME"));
	}

	@Before
	public void setUp()
	throws Exception {
		serverConfig = getDefaultServerConfig();
		configuration = new SimpleDatabaseConfiguration(folder.newFile("database.yml"), getClass().getClassLoader().getResourceAsStream(TEMPLATE_YML), serverConfig, "Test");
	}

}