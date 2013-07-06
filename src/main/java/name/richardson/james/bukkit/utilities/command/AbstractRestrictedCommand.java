package name.richardson.james.bukkit.utilities.command;

import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.utilities.permissions.BukkitPermissionManager;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.permissions.Permissions;

public abstract class AbstractRestrictedCommand extends AbstractCommand {

	private final PluginManager pluginManager;
	private PermissionManager permissionManager;

	public AbstractRestrictedCommand(PluginManager pluginManager) {
		this.pluginManager = pluginManager;
		if (this.getClass().isAnnotationPresent(Permissions.class)) this.setPermissions();
	}


	private void setPermissions() {
		final Permissions annotation = this.getClass().getAnnotation(Permissions.class);
		this.permissionManager = new BukkitPermissionManager(pluginManager);
		this.permissionManager.createPermissions(annotation.permissions());
	}

}
