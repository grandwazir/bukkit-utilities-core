package name.richardson.james.bukkit.utilities.configuration;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;
import name.richardson.james.bukkit.utilities.updater.Branch;
import name.richardson.james.bukkit.utilities.updater.State;

public class PluginConfiguration extends YAMLStorage {

  public static String NAME = "config.yml";
  
  public PluginConfiguration(JavaPlugin plugin) throws IOException {
    super(plugin, "config.yml");
  }
  
  public boolean isDebugging() {
    return this.configuration.getBoolean("debugging");
  }
  
  public void setDebugging(boolean value) {
    this.configuration.set("debugging", value);
  }
  
  public State getAutomaticUpdaterState() {
    if (!this.configuration.getBoolean("automatic-updates.enabled")) return State.OFF;
    try {
      return State.valueOf(this.configuration.getString("automatic-updates.method").toUpperCase());
    } catch (IllegalArgumentException e) {
      return State.OFF;
    }
  }
  
  public void setAutomaticUpdaterState(State state) {
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
  
  public Branch getAutomaticUpdaterBranch() {
    try {
      return Branch.valueOf(this.configuration.getString("automatic-updates.branch").toUpperCase());
    } catch (IllegalArgumentException e) {
      return Branch.STABLE;
    }
  }
  
  public void setAutomaticUpdaterBranch(Branch branch) {
    switch (branch) {
    case STABLE: 
      this.configuration.set("automatic-updates.branch", "stable");
    case DEVELOPMENT: 
      this.configuration.set("automatic-updates.enabled", "development");
    }
  }
  

}
