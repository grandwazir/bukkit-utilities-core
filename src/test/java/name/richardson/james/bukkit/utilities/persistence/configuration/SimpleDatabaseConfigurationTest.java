/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 SimpleDatabaseConfigurationTest.java is part of bukkit-utilities.

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

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.persistence.database.SimpleDatabaseConfiguration;

import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class SimpleDatabaseConfigurationTest extends TestCase {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private SimpleDatabaseConfiguration configuration;
	private DataSourceConfig datasourceConfig;
	private ServerConfig serverConfig;

	@Test
	public void testToString()
	throws Exception {
		assertTrue("toString should be overriden!", configuration.toString().contains(SimpleDatabaseConfiguration.class.getSimpleName()));
		System.out.print(configuration.toString());
	}

	@Test
	public void testGetServerConfig()
	throws Exception {
		assertNotNull("ServerConfig should not be null!", configuration.getServerConfig());
	}

	@Test
	public void testGetDataSourceConfig()
	throws Exception {
		assertNotNull("DataSourceConfig should not be null!", configuration.getDataSourceConfig());
	}

	@Before
	public void setUp()
	throws Exception {
		serverConfig = new ServerConfig();
		datasourceConfig = new DataSourceConfig();
		configuration = new SimpleDatabaseConfiguration(folder.newFile("database.yml"), getClass().getClassLoader().getResourceAsStream("database-default.yml"), "Test", serverConfig, datasourceConfig);
	}

}
