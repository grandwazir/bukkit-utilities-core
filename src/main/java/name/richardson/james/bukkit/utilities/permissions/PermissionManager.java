/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PermissionManager.java is part of BukkitUtilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.permissions;

import java.util.List;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

/**
 * The PermissionManager interface defines a class which is responsible for creating {@link Permission} to prevent
 * access to a class. It is also responsible for keeping track of what permissions have already been created.
 */
public interface PermissionManager {

	/**
	 * Add a permission without making any additional changes.
	 *
	 * @param permission
	 *
	 * @return the permission that was added.
	 */
	public Permission addPermission(Permission permission);

	/**
	 * Create a permission
	 *
	 * This creates a permission with the {@link PermissionDefault} of OP assigns as a parent the next permission up the
	 * tree. In the case that no permission yet exists, it attempts to find a parent one step further up the tree. In case
	 * of no permissions, no parent will be assigned.
	 *
	 * @param node the name of the permission
	 *
	 * @return the permission that was created.
	 */
	public Permission createPermission(String node, String description);

	/**
	 * Create a permission
	 *
	 * This creates a permission with the {@link PermissionDefault} supplied and assigns as a parent the next permission up
	 * the tree. In the case that no permission yet exists, it attempts to find a parent one step further up the tree. In
	 * case of no permissions, no parent will be assigned.
	 *
	 * @param node
	 * @param defaultPermission
	 *
	 * @return the permission that was created.
	 */
	public Permission createPermission(String node, String description, PermissionDefault defaultPermission);


	/**
	 * Create a permission
	 *
	 * This creates a permission with the {@link PermissionDefault} supplied and uses the parent supplied. In the case that
	 * no permission yet exists, it attempts to find a parent one step further up the tree. In case of no permissions, no
	 * parent will be assigned.
	 *
	 * @param node
	 * @param defaultPermission
	 * @param parent
	 *
	 * @return the permission that was created.
	 */
	public Permission createPermission(String node, String description, PermissionDefault defaultPermission, Permission parent);

	public Permission createPermission(String node, String description, PermissionDefault defaultPermission, Permission parent, boolean defaultParent);

	/**
	 * Create permissions
	 *
	 * This is the same as calling createPermission() except it accepts an array of nodes to create.
	 *
	 * @param nodes
	 *
	 * @return list of permissions that were created
	 */
	public List<Permission> createPermissions(String[] nodes);

	/**
	 * Return a list of all permissions created by this manager.
	 *
	 * @return list of permissions
	 */
	public List<Permission> listPermissions();

}
