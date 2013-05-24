/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * DatabaseConfiguration.java is part of BukkitUtilities.
 * 
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;

import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;

public final class DatabaseConfiguration extends YAMLStorage {

	public static final String FILE_NAME = "database.yml";
	private DataSourceConfig dataSourceConfig;
	private final File folder;
	private ServerConfig serverConfig;

	public DatabaseConfiguration(final InputStream defaults, final File folder) throws IOException {
		super(FILE_NAME, defaults);
		this.folder = folder;
	}

	public DataSourceConfig getDataSourceConfig() {
		if (this.dataSourceConfig == null) {
			final ConfigurationSection section = this.getConfiguration().getConfigurationSection("database");
			this.dataSourceConfig = this.serverConfig.getDataSourceConfig();
			this.dataSourceConfig.setUrl(this.replaceDatabaseString(this.dataSourceConfig.getUrl()));
			if (section.get("username") != null) {
				this.dataSourceConfig.setUsername(section.getString("username"));
			}
			if (section.get("password") != null) {
				this.dataSourceConfig.setPassword((section.getString("password")));
			}
			if (section.get("url") != null) {
				this.dataSourceConfig.setUrl(this.replaceDatabaseString((section.getString("url"))));
			}
			if (section.get("driver") != null) {
				this.dataSourceConfig.setDriver((section.getString("driver")));
			}
			if (section.get("isolation") != null) {
				this.dataSourceConfig.setIsolationLevel(TransactionIsolation.getLevel(section.getString("isolation")));
			}
		}
		return this.dataSourceConfig;
	}

	public ServerConfig getServerConfig() {
		if (this.serverConfig == null) {
			this.serverConfig = new ServerConfig();
			this.serverConfig.setDefaultServer(false);
			this.serverConfig.setRegister(false);
			// configure database defaults from bukkit.yml
			Bukkit.getServer().configureDbConfig(this.serverConfig);
		}
		return this.serverConfig;
	}

	private String replaceDatabaseString(String url) {
		url = url.replaceAll("\\{DIR\\}", this.folder.getPath().replaceAll("\\\\", "/") + "/");
		url = url.replaceAll("\\{NAME\\}", this.folder.getName().replaceAll("[^\\w_-]", ""));
		return url;
	}
}
