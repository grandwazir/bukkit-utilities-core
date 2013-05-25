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
	private static final Logger logger = new Logger(BukkitPermissionManager.class.getName());
	
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
		if (this.getParentPermission() != null) permission.addParent(this.getParentPermission(), true);
		return this.addPermission(permission);
	}

	public Permission createPermission(String node, PermissionDefault defaultPermission) {
		final String description = BukkitPermissionManager.localisation.getString(node);
		final Permission permission = new Permission(node, description, defaultPermission);
		if (this.getParentPermission() != null) {
			permission.addParent(this.getParentPermission(), true);
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
		BukkitPermissionManager.logger.log(Level.FINE, "Adding permission: %s (default: %s)", params);
		BukkitPermissionManager.pluginManager.addPermission(permission);
		return permission;
	}

	public List<Permission> listPermissions() {
		return Collections.unmodifiableList(this.permissions);
	}

	public Permission getParentPermission() {
		return permissions.get(0);
	}

	public List<Permission> createPermissions(String[] nodes) {
		final List<Permission> permissions = new ArrayList<Permission>(); 
		for (String node : nodes) {
			permissions.add(this.createPermission(node));
		}
		return permissions;
	}
	
}
