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
package name.richardson.james.bukkit.utilities.persistence.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;
import name.richardson.james.bukkit.utilities.plugin.updater.MavenPluginUpdater;
import name.richardson.james.bukkit.utilities.plugin.updater.PluginUpdater.Branch;
import name.richardson.james.bukkit.utilities.plugin.updater.PluginUpdater.State;

public class SimplePluginConfiguration extends YAMLStorage implements PluginConfiguration {

	public SimplePluginConfiguration(final File file, final InputStream defaults) throws IOException {
		super(file, defaults);
	}

	public SimplePluginConfiguration(final String filePath, final InputStream defaults) throws IOException {
		super(filePath, defaults);
	}

	public Branch getAutomaticUpdaterBranch() {
		final String key = "automatic-updates.branch";
		final MavenPluginUpdater.Branch defaultBranch = MavenPluginUpdater.Branch.STABLE;
		try {
			return MavenPluginUpdater.Branch.valueOf(this.getConfiguration().getString(key));
		} catch (final IllegalArgumentException e) {
			this.getConfiguration().set(key, defaultBranch.name());
			this.save();
			return defaultBranch;
		}
	}

	public State getAutomaticUpdaterState() {
		final String key = "automatic-updates.method";
		final MavenPluginUpdater.State defaultState = MavenPluginUpdater.State.NOTIFY;
		try {
			return MavenPluginUpdater.State.valueOf(this.getConfiguration().getString(key));
		} catch (final IllegalArgumentException e) {
			this.getConfiguration().set(key, defaultState.name());
			this.save();
			return defaultState;
		}
	}

	public Level getLogLevel() {
		final String key = "logging";
		final Level defaultLevel = Level.INFO;
		try {
			return Level.parse(this.getConfiguration().getString(key));
		} catch (final IllegalArgumentException e) {
			this.getConfiguration().set(key, defaultLevel.getName());
			this.save();
			return defaultLevel;
		}
	}

	public boolean isCollectingStats() {
		final String key = "send-anonymous-statistics";
		final boolean defaultValue = true;
		return this.getConfiguration().getBoolean(key, defaultValue);
	}

	public void setAutomaticUpdaterBranch(final Branch branch) {
		final String key = "automatic-updates.branch";
		this.getConfiguration().set(key, branch.name());
	}

	public void setAutomaticUpdaterState(final State state) {
		final String key = "automatic-updates.state";
		this.getConfiguration().set(key, state.name());
	}

	public void setCollectingStats(final boolean value) {
		final String key = "send-anonymous-statistics";
		this.getConfiguration().set(key, value);
	}

	public void setLogLevel(final Level level) {
		final String key = "logging";
		this.getConfiguration().set(key, level);
	}
}
