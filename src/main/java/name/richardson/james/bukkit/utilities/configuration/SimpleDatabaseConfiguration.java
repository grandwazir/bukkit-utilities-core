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

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;

import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;

public final class SimpleDatabaseConfiguration extends YAMLStorage implements DatabaseConfiguration {

	private final DataSourceConfig dataSourceConfig;
	private final ServerConfig serverConfig;
	private final String folder;
	private final String PluginName;

	public SimpleDatabaseConfiguration(final File file, final InputStream defaults, final String pluginName) throws IOException {
		super(file, defaults);
		this.PluginName = pluginName;
		this.folder = file.getParentFile().getAbsolutePath();
		// configure database defaults from bukkit.yml
		this.serverConfig = new ServerConfig();
		this.serverConfig.setDefaultServer(false);
		this.serverConfig.setRegister(false);
		Bukkit.getServer().configureDbConfig(this.serverConfig);
		// configure datastore
		this.dataSourceConfig = this.serverConfig.getDataSourceConfig();
		final String username = this.getConfiguration().getString("username");
		if (username != null) {
			this.dataSourceConfig.setUsername(username);
		}
		final String password = this.getConfiguration().getString("password");
		if (password != null) {
			this.dataSourceConfig.setPassword(password);
		}
		final String url = this.getConfiguration().getString("url");
		if (url != null) {
			this.dataSourceConfig.setUrl(this.replaceDatabaseString(url));
		} else {
			this.dataSourceConfig.setUrl(this.replaceDatabaseString(this.dataSourceConfig.getUrl()));
		}
		final String driver = this.getConfiguration().getString("driver");
		if (driver != null) {
			this.dataSourceConfig.setDriver(driver);
		}
		final String isolation = this.getConfiguration().getString("isolation");
		if (isolation != null) {
			this.dataSourceConfig.setIsolationLevel(TransactionIsolation.getLevel(isolation));
		}
	}

	public DataSourceConfig getDataSourceConfig() {
		return this.dataSourceConfig;
	}

	public ServerConfig getServerConfig() {
		return this.serverConfig;
	}

	private String replaceDatabaseString(String url) {
		url = url.replaceAll("\\{DIR\\}", this.folder + File.separatorChar);
		url = url.replaceAll("\\{NAME\\}", this.PluginName.replaceAll("[^\\w_-]", ""));
		return url;
	}

}
