package name.richardson.james.bukkit.utilities.configuration;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;
import name.richardson.james.bukkit.utilities.updater.Branch;
import name.richardson.james.bukkit.utilities.updater.State;

public class PluginConfiguration extends YAMLStorage {

  public static String NAME = "config.yml";

  public PluginConfiguration(final JavaPlugin plugin) throws IOException {
    super(plugin, "config.yml");
  }

  public Branch getAutomaticUpdaterBranch() {
    try {
      return Branch.valueOf(this.configuration.getString("automatic-updates.branch").toUpperCase());
    } catch (final IllegalArgumentException e) {
      return Branch.STABLE;
    }
  }

  public State getAutomaticUpdaterState() {
    if (!this.configuration.getBoolean("automatic-updates.enabled")) {
      return State.OFF;
    }
    try {
      return State.valueOf(this.configuration.getString("automatic-updates.method").toUpperCase());
    } catch (final IllegalArgumentException e) {
      return State.OFF;
    }
  }

  public boolean isCollectingStats() {
    return this.configuration.getBoolean("send-anonymous-statistics", true);
  }

  public boolean isDebugging() {
    return this.configuration.getBoolean("debugging");
  }

  public void setAutomaticUpdaterBranch(final Branch branch) {
    switch (branch) {
    case STABLE:
      this.configuration.set("automatic-updates.branch", "stable");
    case DEVELOPMENT:
      this.configuration.set("automatic-updates.enabled", "development");
    }
  }

  public void setAutomaticUpdaterState(final State state) {
    switch (state) {
    case AUTOMATIC:
      this.configuration.set("automatic-updates.enabled", true);
      this.configuration.set("automatic-updates.method", "automatic");
    case NOTIFY:
      this.configuration.set("automatic-updates.enabled", true);
      this.configuration.set("automatic-updates.method", "notify");
    case OFF:
      this.configuration.set("automatic-updates.enabled", false);
    }
  }

  public void setCollectingStats(final boolean value) {
    this.configuration.set("send-anonymous-statistics", value);
  }

  public void setDebugging(final boolean value) {
    this.configuration.set("debugging", value);
  }

}
