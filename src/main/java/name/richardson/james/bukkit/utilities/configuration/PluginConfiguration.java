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

import name.richardson.james.bukkit.utilities.persistence.AbstractYAMLStorage;
import name.richardson.james.bukkit.utilities.plugin.Plugin;
import name.richardson.james.bukkit.utilities.updater.Branch;
import name.richardson.james.bukkit.utilities.updater.State;

public class PluginConfiguration extends AbstractYAMLStorage {

  public static String NAME = "config.yml";

  public PluginConfiguration(Plugin plugin) throws IOException {
    super(plugin, "config.yml");
  }

  public Branch getAutomaticUpdaterBranch() {
    try {
      return Branch.valueOf(this.getConfiguration().getString("automatic-updates.branch", "STABLE"));
    } catch (final IllegalArgumentException e) {
      return Branch.STABLE;
    }
  }

  public State getAutomaticUpdaterState() {
    try {
      if (!this.getConfiguration().getBoolean("automatic-updates.enabled", true)) {
        return State.OFF;
      } else {
        return State.valueOf(this.getConfiguration().getString("automatic-updates.method", "NOTIFY").toUpperCase());
      }
    } catch (final IllegalArgumentException e) {
        return State.NOTIFY;
    }
  }

  public boolean isCollectingStats() {
    return this.getConfiguration().getBoolean("send-anonymous-statistics", true);
  }

  public boolean isDebugging() {
    return this.getConfiguration().getBoolean("debugging");
  }

  public void setAutomaticUpdaterBranch(final Branch branch) {
    this.getConfiguration().set("automatic-updates.branch", branch.toString());
  }

  public void setAutomaticUpdaterState(final State state) {
    switch (state) {
    case NOTIFY:
      this.getConfiguration().set("automatic-updates.enabled", true);
      this.getConfiguration().set("automatic-updates.method", "NOTIFY");
    case OFF:
      this.getConfiguration().set("automatic-updates.enabled", false);
    }
  }

  public void setCollectingStats(final boolean value) {
    this.getConfiguration().set("send-anonymous-statistics", value);
  }

  public void setDebugging(final boolean value) {
    this.getConfiguration().set("debugging", value);
  }

}
