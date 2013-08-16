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
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.logging.PluginLoggerFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class MySQLDatabaseLoaderTest extends AbstractDatabaseLoaderTest {

	@Before
	public void setUp()
	throws Exception {
		ServerConfig serverConfig = getServerConfig();
		DataSourceConfig dataSourceConfig = serverConfig.getDataSourceConfig();
		dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1:3306/test");
		dataSourceConfig.setPassword("");
		dataSourceConfig.setUsername("travis");
		dataSourceConfig.setDriver("com.mysql.jdbc.Driver");
		dataSourceConfig.setIsolationLevel(8);
		setDatabaseLoader(serverConfig);
	}

	public void tearDown()
	throws Exception {
		Method method = getDatabaseLoader().getClass().getSuperclass().getDeclaredMethod("drop");
		method.setAccessible(true);
		method.invoke(getDatabaseLoader());
	}

}
