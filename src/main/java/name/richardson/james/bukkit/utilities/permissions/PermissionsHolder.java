/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * PermissionsHolder.java is part of BukkitUtilities.
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

import java.util.List;

import org.bukkit.permissions.Permission;

/**
 * A interface representing methods used when a class implements Permissions.
 * This should usually be used when a class has Permissions that restrict what
 * players can do.
 * 
 * For example a Command would implement Permissions to ensure that only certain
 * players can use certain commands or options within it. A plugin would also
 * implement permissions to provide a shortcut for Commands to register new
 * permissions and to create wildcarded shortcuts for example.
 */
public interface PermissionsHolder {

  /**
   * Registers a permission and adds it to the class's internal list.
   * 
   * @param permission the new permission to add.
   */
  public void addPermission(Permission permission);

  /**
   * Gets a permission from the class's internal list.
   * 
   * @param index the index of the permission
   * @return the permission or null if no such permission exists.
   */
  public Permission getPermission(int index);

  /**
   * Gets a registered permission.
   * 
   * @param path the node path of the permission sought.
   * @return the permission or null if no permission could be found.
   */
  public Permission getPermission(String path);

  /**
   * Gets the full list of permissions associated with this class
   * 
   * @return the permissions
   */
  public List<Permission> getPermissions();

}
