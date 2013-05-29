/*******************************************************************************
 * Copyright (c) 2013 James Richardson
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

import java.util.List;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public interface PermissionManager {

	public Permission addPermission(Permission permission);

	public Permission createPermission(String node);

	public Permission createPermission(String node, PermissionDefault defaultPermission);

	public Permission createPermission(String node, PermissionDefault defaultPermission, Permission parent);

	public List<Permission> createPermissions(String[] nodes);

	public List<Permission> listPermissions();

}
