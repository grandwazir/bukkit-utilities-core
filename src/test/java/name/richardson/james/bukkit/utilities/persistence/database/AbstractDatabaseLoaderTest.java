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

import com.avaje.ebean.config.ServerConfig;
import junit.framework.TestCase;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.persistence.database.support.EntityChild;
import name.richardson.james.bukkit.utilities.persistence.database.support.EntityParent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractDatabaseLoaderTest extends TestCase {

	private DatabaseLoader databaseLoader;

	@Test
	public final void initalise_WhenSuccessfullyInitalisingDatabase_MakeDatabaseAvailable()
	throws Exception {
		getDatabaseLoader().initalise();
		assertNotNull(getDatabaseLoader().getEbeanServer());
	}

	protected final ServerConfig getServerConfig() {
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.setDefaultServer(false);
		serverConfig.setRegister(false);
		ArrayList<Class<?>> databaseClasses = new ArrayList<Class<?>>();
		databaseClasses.add(EntityParent.class);
		databaseClasses.add(EntityChild.class);
		serverConfig.setClasses(databaseClasses);
		serverConfig.setName(this.getClass().getSimpleName());
		return serverConfig;
	}

	protected final DatabaseLoader getDatabaseLoader() {
		return databaseLoader;
	}

	protected final void setDatabaseLoader(ServerConfig serverConfig) {
		DatabaseConfiguration configuration = mock(DatabaseConfiguration.class);
		when(configuration.getDataSourceConfig()).thenReturn(serverConfig.getDataSourceConfig());
		when(configuration.getServerConfig()).thenReturn(serverConfig);
		this.databaseLoader = DatabaseLoaderFactory.getDatabaseLoader(configuration);
	}

}
