/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractDatabaseLoader.java is part of BukkitUtilities.

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

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.avaje.ebean.LogLevel;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.persistence.configuration.DatabaseConfiguration;
import name.richardson.james.bukkit.utilities.persistence.database.support.EntityChild;
import name.richardson.james.bukkit.utilities.persistence.database.support.EntityParent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractDatabaseLoaderTest extends TestCase {

	private DatabaseLoader databaseLoader;
	private ServerConfig serverConfig;

	@Test
	public final void initaliseDatabaseWithoutLogging()
	throws Exception {
		getDatabaseLoader().initalise();
		assertNotNull(getDatabaseLoader().getEbeanServer());
	}

	@Test
	public final void initaliseDatabaseWithLogging() {
		LogManager.getLogManager().getLogger("").setLevel(Level.ALL);
		getDatabaseLoader().initalise();
		assertTrue("Logging should be set to SQL but is actually set to " + serverConfig.getLoggingLevel(), serverConfig.getLoggingLevel().equals(LogLevel.SQL));
		assertNotNull(getDatabaseLoader().getEbeanServer());
	}

	protected static final ServerConfig createDefaultServerConfig() {
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.setDefaultServer(false);
		serverConfig.setRegister(false);
		ArrayList<Class<?>> databaseClasses = new ArrayList<Class<?>>();
		databaseClasses.add(EntityParent.class);
		databaseClasses.add(EntityChild.class);
		serverConfig.setClasses(databaseClasses);
		serverConfig.setName(RandomStringUtils.random(4));
		return serverConfig;
	}


	@Test
	public void checkToStringIsOverriden() {
		assertTrue(getDatabaseLoader().toString().contains("AbstractDatabaseLoader"));
	}

	protected final DatabaseLoader getDatabaseLoader() {
		return databaseLoader;
	}

	protected final void setDatabaseLoader(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
		DatabaseConfiguration configuration = mock(DatabaseConfiguration.class);
		when(configuration.getDataSourceConfig()).thenReturn(serverConfig.getDataSourceConfig());
		when(configuration.getServerConfig()).thenReturn(serverConfig);
		this.databaseLoader = DatabaseLoaderFactory.getDatabaseLoader(configuration);
	}

}
