package name.richardson.james.bukkit.utilities.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater.Branch;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater.State;

public class SimplePluginConfiguration extends YAMLStorage implements PluginConfiguration {

	public final static String DEFAULT_FILE_NAME = "config.yml";

	public SimplePluginConfiguration(final File file, final InputStream defaults) throws IOException {
		super(file, defaults);
	}

	public SimplePluginConfiguration(final String filePath, final InputStream defaults) throws IOException {
		super(filePath, defaults);
	}

	public Branch getAutomaticUpdaterBranch() {
		final String key = "automatic-updates.branch";
		final PluginUpdater.Branch defaultBranch = PluginUpdater.Branch.STABLE;
		try {
			return PluginUpdater.Branch.valueOf(this.getConfiguration().getString(key));
		} catch (final IllegalArgumentException e) {
			this.getConfiguration().set(key, defaultBranch.name());
			this.save();
			return defaultBranch;
		}
	}

	public State getAutomaticUpdaterState() {
		final String key = "automatic-updates.method";
		final PluginUpdater.State defaultState = PluginUpdater.State.NOTIFY;
		try {
			return PluginUpdater.State.valueOf(this.getConfiguration().getString(key));
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
