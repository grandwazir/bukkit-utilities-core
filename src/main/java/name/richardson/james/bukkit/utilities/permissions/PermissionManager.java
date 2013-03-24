/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * PermissionManager.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.permissions;

import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public interface PermissionManager {

  public void addPermission(Permission permission);
  
  public Permission createPermission(Object object, String key, PermissionDefault permissionDefault);
  
  public Permission createPermission(Object object, String key, PermissionDefault permissionDefault, Permission parent, boolean parentDefault);
  
  public Permission getPermission(String name);

  public Permission getRootPermission();

  public boolean hasPlayerPermission(Permissible player, Permission permission);

  public boolean hasPlayerPermission(Permissible player, String name);

  public void setRootPermission(Permission permission);
  
}
