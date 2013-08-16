/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 SQLiteDatabaseLoaderTest.java is part of bukkit-utilities.

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

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SQLiteDatabaseLoaderTest extends TestCase {

	private DatabaseConfiguration configuration;
	private SQLiteDatabaseLoader database;
	private ArrayList<Class<?>> databaseClasses;

	@Test
	public void testInitalise()
	throws Exception {
		database = new SQLiteDatabaseLoader(this.getClass().getClassLoader(), databaseClasses, configuration);
		database.initalise();
	}

	@Before
	public void setUp()
	throws Exception {
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.setDefaultServer(false);
		serverConfig.setRegister(false);
		databaseClasses = new ArrayList<Class<?>>();
		databaseClasses.add(TestBeanParent.class);
		databaseClasses.add(TestBeanChild.class);
		serverConfig.setClasses(databaseClasses);
		serverConfig.setName("SQLiteDatabaseLoaderTest");
		DataSourceConfig dataSourceConfig = serverConfig.getDataSourceConfig();
		dataSourceConfig.setUrl("jdbc:sqlite::memory:");
		dataSourceConfig.setPassword("");
		dataSourceConfig.setUsername("travis");
		dataSourceConfig.setDriver("org.sqlite.JDBC");
		dataSourceConfig.setIsolationLevel(8);
		configuration = mock(DatabaseConfiguration.class);
		when(configuration.getDataSourceConfig()).thenReturn(dataSourceConfig);
		when(configuration.getServerConfig()).thenReturn(serverConfig);
	}

	public void tearDown()
	throws Exception {
		Method method = database.getClass().getSuperclass().getDeclaredMethod("drop");
		method.setAccessible(true);
		method.invoke(database);
	}

}
