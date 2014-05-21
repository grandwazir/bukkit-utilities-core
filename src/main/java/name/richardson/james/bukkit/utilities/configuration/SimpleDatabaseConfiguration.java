/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 SimpleDatabaseConfiguration.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;

import name.richardson.james.bukkit.utilities.logging.PluginLoggerFactory;

import static name.richardson.james.bukkit.utilities.localisation.BukkitUtilities.CONFIGURATION_OVERRIDE_VALUE;

public final class SimpleDatabaseConfiguration extends AbstractConfiguration implements DatabaseConfiguration {

	private static final String USERNAME_KEY = "username";
	private static final String PASSWORD_KEY = "password";
	private static final String DRIVER_KEY = "driver";
	private static final String ISOLATION_KEY = "isolation";
	private static final String URL_KEY = "url";
	private final File folder;
	private final Logger logger = PluginLoggerFactory.getLogger(this.getClass());
	private final String pluginName;
	private final ServerConfig serverConfig;

	public SimpleDatabaseConfiguration(final File file, final InputStream defaults, final ServerConfig serverConfig, final String pluginName)
	throws IOException {
		super(file, defaults);
		this.pluginName = pluginName;
		this.folder = file.getParentFile();
		this.serverConfig = serverConfig;
		setUserName();
		setPassword();
		setDriver();
		setIsolation();
		setUrl();
	}

	public final DataSourceConfig getDataSourceConfig() {
		return this.serverConfig.getDataSourceConfig();
	}

	public final ServerConfig getServerConfig() {
		return this.serverConfig;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SimpleDatabaseConfiguration{");
		sb.append("folder=").append(folder);
		sb.append(", pluginName='").append(pluginName).append('\'');
		sb.append(", serverConfig=").append(serverConfig);
		sb.append('}');
		return sb.toString();
	}

	@SuppressWarnings("ReplaceAllDot")
	private String maskString(String string) {
		return string.replaceAll(".", "*");
	}

	private String replaceDatabaseString(String input) {
		input = input.replaceAll("\\{DIR\\}", this.folder.getAbsolutePath() + "/");
		input = input.replaceAll("\\{NAME\\}", this.pluginName.replaceAll("[^\\w_-]", ""));
		return input;
	}

	private void setDriver() {
		final String driver = this.getConfiguration().getString(DRIVER_KEY);
		if (driver != null) {
			logger.log(Level.CONFIG, CONFIGURATION_OVERRIDE_VALUE.asMessage(DRIVER_KEY, driver));
			getDataSourceConfig().setDriver(driver);
		}
	}

	private void setIsolation() {
		try {
			String isolation = this.getConfiguration().getString("isolation");
			if (isolation != null) {
				logger.log(Level.CONFIG, CONFIGURATION_OVERRIDE_VALUE.asMessage(ISOLATION_KEY, isolation));
				getDataSourceConfig().setIsolationLevel(TransactionIsolation.getLevel(isolation));
			}
		} catch (RuntimeException e) {
			logger.log(Level.WARNING, CONFIGURATION_OVERRIDE_VALUE.asMessage(ISOLATION_KEY, getDataSourceConfig().getIsolationLevel()));
		}
	}

	private void setPassword() {
		final String password = this.getConfiguration().getString(PASSWORD_KEY);
		if (password != null) {
			logger.log(Level.CONFIG, CONFIGURATION_OVERRIDE_VALUE.asMessage(PASSWORD_KEY, maskString(password)));
			getDataSourceConfig().setPassword(password);
		}
	}

	private void setUrl() {
		final String url = this.getConfiguration().getString("url");
		if (url != null) {
			logger.log(Level.CONFIG, CONFIGURATION_OVERRIDE_VALUE.asMessage(URL_KEY, url));
			getDataSourceConfig().setUrl(replaceDatabaseString(url));
		} else {
			getDataSourceConfig().setUrl(replaceDatabaseString(getDataSourceConfig().getUrl()));
		}
	}

	private void setUserName() {
		final String username = this.getConfiguration().getString(USERNAME_KEY);
		if (username != null) {
			logger.log(Level.CONFIG, CONFIGURATION_OVERRIDE_VALUE.asMessage(USERNAME_KEY, username));
			getDataSourceConfig().setUsername(username);
		}
	}

}
