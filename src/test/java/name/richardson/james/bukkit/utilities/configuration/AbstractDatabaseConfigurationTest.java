/*
 * Copyright (c) 2014 James Richardson.
 *
 * AbstractDatabaseConfigurationTest.java is part of BukkitUtilities.
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

import com.avaje.ebean.config.ServerConfig;
import junit.framework.TestCase;

public abstract class AbstractDatabaseConfigurationTest extends TestCase {

	protected final ServerConfig getDefaultServerConfig() {
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.getDataSourceConfig().setUrl("jdbc:sqlite::memory:");
		serverConfig.getDataSourceConfig().setPassword("");
		serverConfig.getDataSourceConfig().setUsername("travis");
		serverConfig.getDataSourceConfig().setDriver("org.sqlite.JDBC");
		serverConfig.getDataSourceConfig().setIsolationLevel(8);
		serverConfig.getDataSourceConfig().setUrl("jdbc:sqlite:{DIR}{NAME}.db");
		return serverConfig;
	}

}
