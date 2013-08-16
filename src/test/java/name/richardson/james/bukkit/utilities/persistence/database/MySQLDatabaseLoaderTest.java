/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 MySQLDatabaseLoaderTest.java is part of bukkit-utilities.

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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.logging.AbstractPrefixedLogger;
import name.richardson.james.bukkit.utilities.persistence.configuration.DatabaseConfiguration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MySQLDatabaseLoaderTest extends TestCase {

	private final Logger logger = AbstractPrefixedLogger.getLogger(MySQLDatabaseLoaderTest.class);

	private DatabaseConfiguration configuration;
	private DefaultDatabaseLoader database;
	private ArrayList<Class<?>> databaseClasses;

	@Test
	public void testInitalise()
	throws Exception {
		database = new DefaultDatabaseLoader(this.getClass().getClassLoader(), databaseClasses, configuration);
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
		serverConfig.setName("MySQLDatabaseLoaderTest");
		DataSourceConfig dataSourceConfig = serverConfig.getDataSourceConfig();
		dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1:3306/test");
		dataSourceConfig.setPassword("");
		dataSourceConfig.setUsername("travis");
		dataSourceConfig.setDriver("com.mysql.jdbc.Driver");
		dataSourceConfig.setIsolationLevel(8);
		configuration = mock(DatabaseConfiguration.class);
		when(configuration.getDataSourceConfig()).thenReturn(dataSourceConfig);
		when(configuration.getServerConfig()).thenReturn(serverConfig);
		logger.setLevel(Level.ALL);
	}

	public void tearDown()
	throws Exception {
		Method method = database.getClass().getSuperclass().getDeclaredMethod("drop");
		method.setAccessible(true);
		method.invoke(database);
	}

}
