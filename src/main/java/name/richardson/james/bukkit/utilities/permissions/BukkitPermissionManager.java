package name.richardson.james.bukkit.utilities.permissions;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.utilities.plugin.Plugin;

public class BukkitPermissionManager extends AbstractPermissionManager {

  private final PluginManager pluginManager;

  private Permission rootPermission;

  public BukkitPermissionManager(final Plugin plugin) {
    super(plugin);
    this.pluginManager = Bukkit.getServer().getPluginManager();
  }

  public void addPermission(final Permission permission, final boolean attachToRoot) {
    if (attachToRoot) {
      permission.addParent(this.getRootPermission(), true);
    }
    if (this.pluginManager.getPermission(permission.getName()) == null) {
      this.pluginManager.addPermission(permission);
      this.getLogger().debug(this, "permission-added", permission.getName(), permission.getDefault());
    } else {
      this.getLogger().warning(this, "permission-already-exists", permission.getName());
    }
  }

  public Permission getPermission(final String name) {
    return this.pluginManager.getPermission(name);
  }

  public Permission getRootPermission() {
    return this.rootPermission;
  }

  public boolean hasPlayerPermission(final Permissible player, final Permission permission) {
    return player.hasPermission(permission);
  }

  public boolean hasPlayerPermission(final Permissible player, final String name) {
    return player.hasPermission(name);
  }

  public void setRootPermission(final Permission permission) {
    if (this.rootPermission != null) {
      this.getLogger().warning(this, "root-permission-already-set", this.rootPermission.getName());
    } else {
      this.getLogger().debug(this, "root-permission-added", permission.getName(), permission.getDefault());
    }
  }

}
