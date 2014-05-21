/*
 * Copyright (c) 2013 James Richardson.
 *
 * TestDatabaseLoader.java is part of BukkitUtilities.
 *
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.persistence.database.support;

import java.util.List;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import org.apache.commons.lang.RandomStringUtils;

import name.richardson.james.bukkit.utilities.configuration.DatabaseConfiguration;
import name.richardson.james.bukkit.utilities.persistence.database.DatabaseLoader;
import name.richardson.james.bukkit.utilities.persistence.database.DatabaseLoaderFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This test helper class creates a memory backed SQLite database. It is useful for intergration test.
 */
public class TestDatabaseLoader {

	private final List<Class<?>> classList;
	private final DatabaseLoader databaseLoader;

	private ServerConfig serverConfig;

	public TestDatabaseLoader(List<Class<?>> classList) {
		this.classList = classList;
		this.prepareConfiguration();
		DatabaseConfiguration configuration = mock(DatabaseConfiguration.class);
		when(configuration.getDataSourceConfig()).thenReturn(serverConfig.getDataSourceConfig());
		when(configuration.getServerConfig()).thenReturn(serverConfig);
		this.databaseLoader = DatabaseLoaderFactory.getDatabaseLoader(configuration);
	}

	public List<Class<?>> getClassList() {
		return classList;
	}

	public DatabaseLoader getDatabaseLoader() {
		return databaseLoader;
	}

	private void prepareConfiguration() {
		serverConfig = new ServerConfig();
		serverConfig.setDefaultServer(false);
		serverConfig.setRegister(false);
		serverConfig.setClasses(getClassList());
		serverConfig.setName(RandomStringUtils.random(8));
		DataSourceConfig dataSourceConfig = serverConfig.getDataSourceConfig();
		dataSourceConfig.setUrl("jdbc:sqlite::memory:");
		dataSourceConfig.setPassword("");
		dataSourceConfig.setUsername("travis");
		dataSourceConfig.setDriver("org.sqlite.JDBC");
		dataSourceConfig.setIsolationLevel(8);
	}



}
