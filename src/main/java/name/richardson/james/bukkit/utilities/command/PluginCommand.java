package name.richardson.james.bukkit.utilities.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.utilities.permissions.PermissionsHolder;
import name.richardson.james.bukkit.utilities.plugin.Localisable;
import name.richardson.james.bukkit.utilities.plugin.SimplePlugin;

public abstract class PluginCommand implements Command, PermissionsHolder, Localisable {

  /** The plugin. */
  protected SimplePlugin plugin;

  /** The description of what this command does */
  private final String description;

  /** The name of this command */
  private final String name;

  /** The usage message for this command */
  private final String usage;

  /** The permissions associated with this command */
  private final List<Permission> permissions = new LinkedList<Permission>();

  public PluginCommand(final SimplePlugin plugin) {
    final String pathPrefix = this.getClass().getSimpleName().toLowerCase();
    this.name = plugin.getMessage(pathPrefix + "-name");
    this.description = plugin.getMessage(pathPrefix + "-description");
    this.usage = plugin.getMessage(pathPrefix + "-usage");
    this.plugin = plugin;
  }

  public void addPermission(final Permission permission) {
    this.plugin.addPermission(permission);
    this.permissions.add(permission);
  }

  public String getChoiceFormattedMessage(String key, Object[] arguments, Object[] formats, Double[] limits) {
    return plugin.getChoiceFormattedMessage(key, arguments, formats, limits);
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.command.Command#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  public Locale getLocale() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getMessage(final String key) {
    return this.plugin.getMessage(key);
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

  public String getSimpleFormattedMessage(String key, Object[] arguments) {
    return this.plugin.getSimpleFormattedMessage(key, arguments);
  }

  public String getSimpleFormattedMessage(final String key, final String argument) {
    final String[] arguments = { argument };
    return this.getSimpleFormattedMessage(key, arguments);
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
      sender.sendMessage(ChatColor.RED + this.getMessage("command-not-available-to-console"));
      return true;
    }

    if (!this.testPermission(sender)) {
      sender.sendMessage(ChatColor.RED + this.getMessage("command-no-permission"));
      return true;
    }
    
    try {
      this.parseArguments(args, sender);
    } catch (CommandArgumentException exception) {
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
      sender.sendMessage(ChatColor.RED + this.getMessage("command-no-permission"));
      if (exception.getMessage() != null) sender.sendMessage(ChatColor.YELLOW + exception.getMessage());
      if (this.plugin.isDebugging()) sender.sendMessage(ChatColor.DARK_PURPLE + this.getSimpleFormattedMessage("command-permission-required", exception.getPermission().getName()));
    } catch (final CommandUsageException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
    }

    return true;

  }
  
  private boolean testPermission(CommandSender sender) {
    for (Permission permission : this.permissions) {
      if (sender.hasPermission(permission)) return true;
    }
    return false;
  }


}
