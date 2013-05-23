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

// TODO: Auto-generated Javadoc
/**
 * The PermissionManager interface represents the public facing of a PermissionsAPI. 
 */
public interface PermissionManager {

  /**
   * Adds the permission.
   * 
   * This method does 
   *
   * @param permission the permission
   */
  public void addPermission(Permission permission);
  
  /**
   * Creates the permission.
   *
   * @param object the object
   * @param key the key
   * @param permissionDefault the permission default
   * @return the permission
   */
  public Permission createPermission(Object object, String key, PermissionDefault permissionDefault);
  
  /**
   * Creates the permission.
   *
   * @param object the object
   * @param key the key
   * @param permissionDefault the permission default
   * @param parent the parent
   * @param parentDefault the parent default
   * @return the permission
   */
  public Permission createPermission(Object object, String key, PermissionDefault permissionDefault, Permission parent, boolean parentDefault);
  
  /**
   * Gets the permission.
   *
   * @param name the name
   * @return the permission
   */
  public Permission getPermission(String name);

  public Permission getRootPermission();

  /**
   * Checks for permission.
   *
   * @param player the player
   * @param permission the permission
   * @return true, if successful
   */
  public boolean hasPermission(Permissible player, Permission permission);

  /**
   * Checks for permission.
   *
   * @param player the player
   * @param name the name
   * @return true, if successful
   */
  public boolean hasPermission(Permissible player, String name);
  
}
