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

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.logging.ConsoleLogger;
import name.richardson.james.bukkit.utilities.logging.Logger;

public class BukkitPermissionManager implements PermissionManager {

  private final PluginManager pluginManager;
  
  private final List<Permission> permissions = new LinkedList<Permission>();

  private Localisation localisation;

  private final Logger logger = new ConsoleLogger(this.getClass().getName());

  public BukkitPermissionManager() {
    this.pluginManager = Bukkit.getServer().getPluginManager();
  }
  
  public BukkitPermissionManager(final Localisation localisation) {
    this.localisation = localisation;
    this.pluginManager = Bukkit.getServer().getPluginManager();
  }

  public void addPermission(final Permission permission) {
    this.pluginManager.addPermission(permission);
    this.permissions.add(permission);
    String message = String.format("Adding permission: %s (%s)", permission.getName(), permission.getDefault().toString());
    this.logger.debug(message);
  }

  public Permission getPermission(final String name) {
    return this.pluginManager.getPermission(name);
  }

  public Permission getRootPermission() {
    return this.permissions.get(0);
  }

  public boolean hasPermission(final Permissible permissible, final Permission permission) {
    boolean result = permissible.hasPermission(permission);
    String message = String.format("Checking permission: %s has %s? %b", permissible.toString(), permission.getName(), result);
    this.logger.debug(this, message);
    return result;
  }

  public boolean hasPermission(final Permissible permissible, final String name) {
    boolean result = permissible.hasPermission(name);
    this.getLogger().debug(this, String.format("Checking permission: %s has %s? %b", permissible.toString(), name, result));
    return result;
  }
  
  public Permission createPermission(String node, String description, PermissionDefault permissionDefault) {
    Permission permission = new Permission(node, description, permissionDefault);
    permission.addParent(this.getRootPermission(), true);
    this.addPermission(permission);
    return permission;
  }
  
  public Permission createPermission(Object object, String node, PermissionDefault permissionDefault, Permission parent) {
    String description = this.localisation.getMessage(object, node + "-description");
    Permission permission = new Permission(node, description, permissionDefault);
    permission.addParent(parent, true);
    this.addPermission(permission);
    return permission; 
  }
  
  public Permission createLocalisedPermission(Object object, String node) {
    String description = this.localisation.getMessage(object, node + "-description");
    Permission permission = new Permission(node, description, PermissionDefault.OP);
    permission.addParent(this.getRootPermission(), true);
    this.addPermission(permission);
    return permission;
  }
  
  public Permission createLocalisedPermission(Object object, String node, PermissionDefault permissionDefault) {
    String description = this.localisation.getMessage(object, node + "-description");
    Permission permission = new Permission(node, description, permissionDefault);
    permission.addParent(this.getRootPermission(), true);
    this.addPermission(permission);
    return permission;
  }
  
  public Permission createLocalisedPermission(Object object, String node, PermissionDefault permissionDefault, Permission parent) {
    String description = this.localisation.getMessage(object, node + "-description");
    Permission permission = new Permission(node, description, permissionDefault);
    permission.addParent(parent, true);
    this.addPermission(permission);
    return permission; 
  }

  public String resolveName(String name, Permission parent) {
    String resolvedName = String.format("%s.%s", parent.getName(), name.replace(parent.getName(), ""));
    return resolvedName;
  } 
}
