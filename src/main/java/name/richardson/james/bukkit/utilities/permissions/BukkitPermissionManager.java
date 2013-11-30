/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 BukkitPermissionManager.java is part of BukkitUtilities.

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

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

import org.apache.commons.lang.StringUtils;

import name.richardson.james.bukkit.utilities.logging.PluginLoggerFactory;

/**
 * An implementation of {@link PermissionManager} using the Bukkit SuperPerms system.
 */
public final class BukkitPermissionManager implements PermissionManager {

	private final Logger logger = PluginLoggerFactory.getLogger(BukkitPermissionManager.class);
	private final PluginManager pluginManager;
	private final List<Permission> permissions = new ArrayList<Permission>();

	public BukkitPermissionManager() {
		this(Bukkit.getServer().getPluginManager());
	}

	public BukkitPermissionManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}

	public Permission addPermission(final Permission permission) {
		logger.log(Level.CONFIG, "Adding permission: {0}", permission.getName());
		pluginManager.addPermission(permission);
		this.permissions.add(permission);
		return permission;
	}

	public Permission createPermission(final String node, String description) {
		return createPermission(node, description, PermissionDefault.OP);
	}

	public Permission createPermission(final String node,  String description, final PermissionDefault defaultPermission) {
		return createPermission(node, description, defaultPermission, getParentPermission(node));
	}

	public Permission createPermission(final String node, String description, final PermissionDefault defaultPermission, final Permission parent) {
		return createPermission(node, description, defaultPermission, parent, true);
	}

	public Permission createPermission(final String node, String description, final PermissionDefault defaultPermission, final Permission parent, final boolean defaultParent) {
		final Permission permission = new Permission(node, description, defaultPermission);
		if (parent != null) permission.addParent(parent, defaultParent);
		return this.addPermission(permission);
	}

	public List<Permission> createPermissions(final String[] nodes) {
		for (final String node : nodes) {
			this.createPermission(node, "No description available");
		}
		return permissions;
	}

	public List<Permission> listPermissions() {
		return Collections.unmodifiableList(this.permissions);
	}

	private Permission getParentPermission(final String permissionName) {
		final List<String> nodes = new LinkedList<String>(Arrays.asList(permissionName.split("\\.")));
		if (nodes.size() > 1) {
			nodes.remove(nodes.size() - 1);
			final String parentNode = StringUtils.join(nodes, ".");
			logger.log(Level.FINEST, "Resolving parent permission as " +  parentNode);
			return pluginManager.getPermission(parentNode);
		} else {
			return null;
		}
	}

}
