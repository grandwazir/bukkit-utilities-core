/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * Plugin.java is part of BukkitUtilities.
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

package name.richardson.james.bukkit.util;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Plugin extends JavaPlugin {

  protected Logger logger = new Logger(this.getClass());

  private Permission permission;

  public void addPermission(final Permission permission, final boolean parentPlugin) {
    if (parentPlugin) {
      permission.addParent(this.permission, true);
    }
    logger.debug(String.format("Adding permission: %s (default: %s)", permission.getName(), permission.getDefault()));
    this.getServer().getPluginManager().addPermission(permission);
  }

  public Permission getPermission() {
    return this.permission;
  }

}
