/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * PluginConfiguration.java is part of BukkitUtilities.
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

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.persistence.AbstractYAMLStorage;
import name.richardson.james.bukkit.utilities.updater.Branch;
import name.richardson.james.bukkit.utilities.updater.State;

public class PluginConfiguration extends AbstractYAMLStorage {

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
