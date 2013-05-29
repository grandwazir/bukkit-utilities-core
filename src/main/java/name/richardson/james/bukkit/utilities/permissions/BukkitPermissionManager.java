package name.richardson.james.bukkit.utilities.permissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.logging.Logger;

public class BukkitPermissionManager implements PermissionManager {

	private static final PluginManager pluginManager = Bukkit.getPluginManager();
	private static final ResourceBundle localisation = ResourceBundle.getBundle(ResourceBundles.PERMISSIONS.getBundleName());
	private static final Logger logger = new Logger(BukkitPermissionManager.class);
	
	private final List<Permission> permissions = new ArrayList<Permission>();

	public BukkitPermissionManager(Permission parent) {
		this.permissions.add(parent);
	}
	
	public BukkitPermissionManager() {
		return;
	}

	public Permission createPermission(String node) {
		final String description = BukkitPermissionManager.localisation.getString(node);
		final Permission permission = new Permission(node, description);
		final Permission parent = this.getParentPermission(permission);
		if (this.getParentPermission(permission) != null) {
			permission.addParent(parent, true);
		}
		return this.addPermission(permission);
	}

	public Permission createPermission(String node, PermissionDefault defaultPermission) {
		final String description = BukkitPermissionManager.localisation.getString(node);
		final Permission permission = new Permission(node, description, defaultPermission);
		final Permission parent = this.getParentPermission(permission);
		if (this.getParentPermission(permission) != null) {
			permission.addParent(parent, true);
		}
		return this.addPermission(permission);
	}

	public Permission createPermission(String node, PermissionDefault defaultPermission, Permission parent) {
		final String description = BukkitPermissionManager.localisation.getString(node);
		final Permission permission = new Permission(node, description, defaultPermission);
		permission.addParent(permission, true);
		return this.addPermission(permission);
	}
	
	public Permission createPermission(String node, PermissionDefault defaultPermission, Permission parent, boolean defaultParent) {
		final String description = BukkitPermissionManager.localisation.getString(node);
		final Permission permission = new Permission(node, description, defaultPermission);
		permission.addParent(permission, defaultParent);
		return this.addPermission(permission);
	}
	
	public Permission addPermission(Permission permission) {
		final Object[] params = {permission.getName(), permission.getDefault()};
		BukkitPermissionManager.logger.log(Level.FINE, "Adding permission: {0} (default: {1})", params);
		BukkitPermissionManager.pluginManager.addPermission(permission);
		permissions.add(permission);
		return permission;
	}

	public List<Permission> listPermissions() {
		return Collections.unmodifiableList(this.permissions);
	}

	private Permission getParentPermission(Permission permission) {
		final String[] nodes = permission.getName().split("\\.");
		if (nodes.length > 1) {
			final String parentNode = nodes[nodes.length - 1];
			return Bukkit.getPluginManager().getPermission(parentNode);
		} else {
			return null;
		}
	}

	public List<Permission> createPermissions(String[] nodes) {
		final List<Permission> permissions = new ArrayList<Permission>(); 
		for (String node : nodes) {
			this.createPermission(node);
		}
		return permissions;
	}
	
}
