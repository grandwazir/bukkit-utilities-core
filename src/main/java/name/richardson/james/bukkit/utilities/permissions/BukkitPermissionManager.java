/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * BukkitPermissionManager.java is part of BukkitUtilities.
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

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.plugin.Plugin;

public class BukkitPermissionManager extends AbstractPermissionManager {

  private final PluginManager pluginManager;
  
  private Permission rootPermission;

  private Localisation localisation;

  public BukkitPermissionManager(final Plugin plugin) {
    super(plugin);
    this.localisation = plugin.getLocalisation();
    this.pluginManager = Bukkit.getServer().getPluginManager();
  }

  public void addPermission(final Permission permission) {
    this.pluginManager.addPermission(permission);
    this.getLogger().debug(this, String.format("Adding permission: %s (%s)", permission.getName(), permission.getDefault().toString()));
  }

  public Permission getPermission(final String name) {
    return this.pluginManager.getPermission(name);
  }

  public Permission getRootPermission() {
    return this.rootPermission;
  }

  public boolean hasPlayerPermission(final Permissible permissible, final Permission permission) {
    boolean result = permissible.hasPermission(permission);
    this.getLogger().debug(this, String.format("Checking permission: %s has %s? %b", permissible.toString(), permission.getName(), result));
    return result;
  }

  public boolean hasPlayerPermission(final Permissible permissible, final String name) {
    return this.hasPlayerPermission(permissible, this.getPermission(name));
  }

  public void setRootPermission(final Permission permission) {
    this.addPermission(permission);
    this.rootPermission = permission;
    this.getLogger().debug(this, String.format("Setting root permission: %s (%s)", permission.getName(), permission.getDefault().toString()));
  }

  public Permission createPermission(Object object, String key, PermissionDefault permissionDefault) {
    String name = this.localisation.getMessage(object, key + ".name");
    String description = this.localisation.getMessage(object, key + ".description");
    Permission permission = new Permission(name, description, permissionDefault);
    permission.addParent(this.getRootPermission(), true);
    this.addPermission(permission);
    return permission;
  }
  
  public Permission createPermission(Object object, String key, PermissionDefault permissionDefault, Permission parent, boolean parentDefault) {
    String name = this.resolveName(this.localisation.getMessage(object, key + ".name"), parent);
    String description = this.localisation.getMessage(object, key + ".description");
    Permission permission = new Permission(name, description, permissionDefault);
    permission.addParent(parent, parentDefault);
    this.addPermission(permission);
    return permission; 
  }

  private String resolveName(String name, Permission parent) {
    String resolvedName = String.format("%s.%s", parent.getName(), name.replace(parent.getName(), ""));
    return resolvedName;
  } 
}
