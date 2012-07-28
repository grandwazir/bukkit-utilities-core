/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * PluginCommand.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localised;
import name.richardson.james.bukkit.utilities.permissions.PermissionsHolder;
import name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin;

public abstract class PluginCommand extends Localised implements Command, PermissionsHolder {

  /** The plugin that is command belongs to. */
  protected SkeletonPlugin plugin;

  /** The description of what this command does */
  private final String description;

  /** The name of this command */
  private final String name;

  /** The usage message for this command */
  private final String usage;

  /** The permissions associated with this command */
  private final List<Permission> permissions = new LinkedList<Permission>();

  public PluginCommand(final SkeletonPlugin plugin) {
    super(plugin);
    this.plugin = plugin;
    this.name = this.getMessage("name");
    this.description = this.getMessage("description");
    this.usage = this.getMessage("usage");
  }

  public void addPermission(final Permission permission) {
    this.plugin.addPermission(permission);
    this.permissions.add(permission);
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.command.Command#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.command.Command#getName()
   */
  public String getName() {
    return this.name;
  }

  public Permission getPermission(final int index) {
    return this.permissions.get(index);
  }

  public Permission getPermission(final String path) {
    for (final Permission permission : this.permissions) {
      if (permission.getName().equalsIgnoreCase(path)) {
        return permission;
      }
    }
    return null;
  }

  public List<Permission> getPermissions() {
    return Collections.unmodifiableList(this.permissions);
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.command.Command#getUsage()
   */
  public String getUsage() {
    return this.usage;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
   * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
   */
  public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {

    if (!this.getClass().isAnnotationPresent(ConsoleCommand.class) && (sender instanceof ConsoleCommandSender)) {
      String message = ColourFormatter.replace("&", this.plugin.getMessage("plugincommand.not-available-to-console"));
      sender.sendMessage(message);
      return true;
    }

    if (!this.testPermission(sender)) {
      String message = ColourFormatter.replace("&", this.plugin.getMessage("plugincommand.no-permission"));
      sender.sendMessage(message);
      return true;
    }

    try {
      this.parseArguments(args, sender);
    } catch (final CommandArgumentException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
      sender.sendMessage(ChatColor.YELLOW + exception.getHelp());
      return true;
    }

    try {
      this.execute(sender);
    } catch (final CommandArgumentException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
      sender.sendMessage(ChatColor.YELLOW + exception.getHelp());
    } catch (final CommandPermissionException exception) {
      sender.sendMessage(ColourFormatter.replace("&", this.plugin.getMessage("plugincommand.no-permission")));
      if (exception.getMessage() != null) {
        sender.sendMessage(ChatColor.YELLOW + exception.getMessage());
      }
      if (this.plugin.isDebugging()) {
        sender.sendMessage(ColourFormatter.replace("&", this.plugin.getSimpleFormattedMessage("plugincommand.permission-required", exception.getPermission().getName())));
      }
    } catch (final CommandUsageException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
    }

    return true;

  }

  public boolean testPermission(final CommandSender sender) {
    for (final Permission permission : this.permissions) {
      if (sender.hasPermission(permission)) {
        return true;
      }
    }
    return false;
  }

}
