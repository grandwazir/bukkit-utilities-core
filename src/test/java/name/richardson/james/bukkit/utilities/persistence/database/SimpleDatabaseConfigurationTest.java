/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 SimpleDatabaseConfigurationTest.java is part of BukkitUtilities.

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

package name.richardson.james.bukkit.utilities.persistence.database;

import java.io.File;
import java.io.IOException;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SimpleDatabaseConfigurationTest extends TestCase {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private SimpleDatabaseConfiguration configuration;
	private ServerConfig serverConfig;

	@Test
	public void onCreation_WhenNoValuesAreOverriden_ReturnDefaultServerConfig()
	throws IOException {
		configuration = createConfiguration("database-empty.yml");
		ServerConfig serverConfig = getDefaultServerConfig();
		assertTrue(serverConfig.getDataSourceConfig().getUsername().contentEquals(configuration.getDataSourceConfig().getUsername()));
		assertTrue(serverConfig.getDataSourceConfig().getPassword().contentEquals(configuration.getDataSourceConfig().getPassword()));
		assertTrue(serverConfig.getDataSourceConfig().getDriver().contentEquals(configuration.getDataSourceConfig().getDriver()));
		assertTrue(serverConfig.getDataSourceConfig().getIsolationLevel() == configuration.getDataSourceConfig().getIsolationLevel());
		assertTrue(serverConfig.getDataSourceConfig().getPassword().contentEquals(configuration.getDataSourceConfig().getPassword()));
	}

	@Test
	public void onCreation_WhenValuesAreOverriden_ReturnModifiedServerConfig()
	throws IOException {
		File f = new File(getClass().getClassLoader().getResource("database-custom.yml").getPath());
		configuration = new SimpleDatabaseConfiguration(f, getClass().getClassLoader().getResourceAsStream("database-empty.yml"), "Test", serverConfig);
		ServerConfig serverConfig = getDefaultServerConfig();
		assertFalse(serverConfig.getDataSourceConfig().getUsername().contentEquals(configuration.getDataSourceConfig().getUsername()));
		assertFalse(serverConfig.getDataSourceConfig().getPassword().contentEquals(configuration.getDataSourceConfig().getPassword()));
		assertFalse(serverConfig.getDataSourceConfig().getDriver().contentEquals(configuration.getDataSourceConfig().getDriver()));
		assertFalse(serverConfig.getDataSourceConfig().getIsolationLevel() == configuration.getDataSourceConfig().getIsolationLevel());
		assertFalse(serverConfig.getDataSourceConfig().getPassword().contentEquals(configuration.getDataSourceConfig().getPassword()));
	}

	@Test
	public void onCreation_WhenCreatingUrl_PlaceholdersReplaced()
	throws IOException {
		configuration = createConfiguration("database-empty.yml");
		assertFalse(configuration.getServerConfig().getDataSourceConfig().getUrl().contains("DIR"));
		assertFalse(configuration.getServerConfig().getDataSourceConfig().getUrl().contains("NAME"));
	}


	@Before
	public void setUp()
	throws Exception {
		serverConfig = getDefaultServerConfig();
	}

	private ServerConfig getDefaultServerConfig() {
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.getDataSourceConfig().setUrl("jdbc:sqlite::memory:");
		serverConfig.getDataSourceConfig().setPassword("");
		serverConfig.getDataSourceConfig().setUsername("travis");
		serverConfig.getDataSourceConfig().setDriver("org.sqlite.JDBC");
		serverConfig.getDataSourceConfig().setIsolationLevel(8);
		serverConfig.getDataSourceConfig().setUrl("jdbc:sqlite:{DIR}{NAME}.db");
		return serverConfig;
	}


	private SimpleDatabaseConfiguration createConfiguration(String defaultFile)
	throws IOException {
		return new SimpleDatabaseConfiguration(folder.newFile("database.yml"), getClass().getClassLoader().getResourceAsStream(defaultFile), "Test", serverConfig);
	}

}
