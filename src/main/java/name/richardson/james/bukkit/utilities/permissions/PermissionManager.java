package name.richardson.james.bukkit.utilities.permissions;

import java.util.List;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public interface PermissionManager {
	
	public Permission createPermission(String node);
	
	public Permission createPermission(String node, PermissionDefault defaultPermission);
	
	public Permission createPermission(String node, PermissionDefault defaultPermission, Permission parent);
	
	public List<Permission> createPermissions(String[] nodes);
	
	public List<Permission> listPermissions();
		
}
