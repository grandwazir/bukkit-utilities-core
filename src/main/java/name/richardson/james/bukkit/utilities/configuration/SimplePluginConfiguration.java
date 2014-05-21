/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 SimplePluginConfiguration.java is part of BukkitUtilities.

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

import org.bukkit.configuration.file.YamlConfiguration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import name.richardson.james.bukkit.utilities.updater.PluginUpdater;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater.Branch;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater.State;

public class SimplePluginConfiguration extends AbstractConfiguration implements PluginConfiguration {

	private enum Keys {
		BRANCH("automatic-updates.branch"),
		UPDATER_STATE("automatic-updates.method"),
		LOGGING_LEVEL("logging"),
	  STATISTICS("send-anonymous-statistics");
		private final String path;

		Keys(final String path) {
			this.path = path;
		}

		private String getPath() {
			return path;
		}

	}

	private static final Logger LOGGER = LogManager.getLogger();

	public SimplePluginConfiguration(final File file, final InputStream defaults) throws IOException {
		super(file, defaults);
		useRuntimeDefaults();
	}

	@Override public final Branch getAutomaticUpdaterBranch() {
		PluginUpdater.Branch defaultBranch = PluginUpdater.Branch.STABLE;
		YamlConfiguration configuration = getConfiguration();
		try {
			String value = configuration.getString(Keys.BRANCH.getPath());
			return PluginUpdater.Branch.valueOf(value.toUpperCase());
			} catch (final IllegalArgumentException e) {
			configuration.set(Keys.BRANCH.getPath(), defaultBranch.name());
				return defaultBranch;
			}
		}

	@Override public final State getAutomaticUpdaterState() {
		PluginUpdater.State defaultState = PluginUpdater.State.NOTIFY;
		YamlConfiguration configuration = getConfiguration();
		try {
			String value = configuration.getString(Keys.UPDATER_STATE.getPath());
			return PluginUpdater.State.valueOf(value.toUpperCase());
		} catch (final IllegalArgumentException e) {
			configuration.set(Keys.UPDATER_STATE.getPath(), defaultState.name());
			return defaultState;
		}
	}

	@Override public final Level getLogLevel() {
		Level defaultLevel = Level.INFO;
		YamlConfiguration configuration = getConfiguration();
		try {
			String value = configuration.getString(Keys.LOGGING_LEVEL.getPath());
			return Level.parse(value.toUpperCase());
		} catch (final IllegalArgumentException e) {
			configuration.set(Keys.LOGGING_LEVEL.getPath(), defaultLevel.getName());
			return defaultLevel;
		}
	}

	@Override public final boolean isCollectingStats() {
		boolean defaultValue = true;
		YamlConfiguration configuration = getConfiguration();
		return configuration.getBoolean(Keys.STATISTICS.getPath(), defaultValue);
	}

}
