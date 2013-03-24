/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * AbstractCommand.java is part of BukkitUtilities.
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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.logging.Logger;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.plugin.Plugin;

public abstract class AbstractCommand implements Command {

  private final String description;

  private final Localisation localisation;

  private final Logger logger;

  private final String name;

  private final PermissionManager permissionManager;
  
  private final List<Permission> permissions = new ArrayList<Permission>();

  private final String usage;

  public AbstractCommand(final Plugin plugin, final boolean wildcard) {
    this.localisation = plugin.getLocalisation();
    this.logger = plugin.getCustomLogger();
    this.permissionManager = plugin.getPermissionManager();
    this.name = this.localisation.getMessage(this, "name");
    this.description = this.localisation.getMessage(this, "description");
    this.usage = this.localisation.getMessage(this, "usage");
    this.registerInitialPermissions();
  }

  public String getDescription() {
    return this.description;
  }

  public Localisation getLocalisation() {
    return this.localisation;
  }

  public Logger getLogger() {
    return this.logger;
  }

  public String getName() {
    return this.name;
  }

  public PermissionManager getPermissionManager() {
    return this.permissionManager;
  }

  public String getUsage() {
    return this.usage;
  }

  public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
    // Do not allow this command to be used from the console unless annotation
    // is present
    if (!this.getClass().isAnnotationPresent(ConsoleCommand.class) && (sender instanceof ConsoleCommandSender)) {
      final String message = this.localisation.getMessage(AbstractCommand.class, "not-allowed-from-console");
      sender.sendMessage(message);
      return true;
    }

    // Check if the sender is allowed to use this command
    if (!this.testPermission(sender)) {
      final String message = this.localisation.getMessage(AbstractCommand.class, "not-permitted");
      sender.sendMessage(message);
      return true;
    }

    try {
      this.parseArguments(args, sender);
      this.execute(sender);
    } catch (final CommandArgumentException exception) {
      sender.sendMessage(exception.getMessage());
      if (exception.getHelp() != null) {
        sender.sendMessage(exception.getHelp());
      }
    } catch (final CommandPermissionException exception) {
      final String message = this.localisation.getMessage(AbstractCommand.class, "not-permitted");
      sender.sendMessage(message);
      if (exception.getMessage() != null) {
        sender.sendMessage(exception.getMessage());
      }
      if (this.logger.isDebugging()) {
        sender.sendMessage(this.localisation.getMessage(AbstractCommand.class, "permission-required", exception.getPermission().getName()));
      }
    } catch (final CommandUsageException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
    }
    return true;
  }

  public boolean testPermission(final CommandSender sender) {
    for (Permission permission : this.permissions) {
      if (this.permissionManager.hasPlayerPermission(sender, permission)) return true;
    }
    return false;
  }

  private void registerInitialPermissions() {
    this.permissions.add(this.permissionManager.createPermission(this, "use-permission", PermissionDefault.OP, permissionManager.getRootPermission(), true));
  }

}
