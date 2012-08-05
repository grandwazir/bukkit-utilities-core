package name.richardson.james.bukkit.utilities.permissions;

import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;

public interface PermissionManager {

  public void addPermission(Permission permission, boolean attachToRoot);

  public Permission getPermission(String name);

  public Permission getRootPermission();

  public boolean hasPlayerPermission(Permissible player, Permission permission);

  public boolean hasPlayerPermission(Permissible player, String name);

  public void setRootPermission(Permission permission);

}
