package name.richardson.james.bukkit.utilities.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.utilities.permissions.PermissionsHolder;
import name.richardson.james.bukkit.utilities.plugin.SimplePlugin;

// TODO: Auto-generated Javadoc
/**
 * The Class PluginCommand.
 */
public abstract class PluginCommand implements Command, PermissionsHolder {

  /** The resource bundle for generic messages which will be used across plugins. */
  private static final ResourceBundle messages = ResourceBundle.getBundle("localisation-bukkit-utils");
  
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

  private final Map<String, Object> arguments = new HashMap<String, Object>();
  
  public PluginCommand(SimplePlugin plugin, String name, String description, String usage) {
    this.name = name;
    this.description = description;
    this.usage = usage;
    this.plugin = plugin;
  }
  
  /* (non-Javadoc)
   * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
   */
  public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
    
    // create the argument list and remove the command name from the list
    final List<String> arguments = new LinkedList<String>();
    for (String argument : args) {
      if (!argument.equalsIgnoreCase(this.getName())) arguments.add(argument);
    }
    
    if (!this.getClass().isAnnotationPresent(ConsoleCommand.class) && (sender instanceof ConsoleCommandSender)) {
      // check if this command is available to the console.
      // if it isn't and they try to use it give them an error message.
      sender.sendMessage(ChatColor.RED + PluginCommand.getMessage("command-not-available-to-console"));
      return true;
    }
    
    for (Permission permission : this.getPermissions()) {
      // check to see if the user has one of the permissions required to use this command.
      // if they do execute the command
      if (sender.hasPermission(permission)) {
        try {
          this.parseArguments(arguments, sender);
          this.execute(sender);
        } catch (CommandArgumentException exception) {
          sender.sendMessage(ChatColor.RED + exception.getMessage());
          sender.sendMessage(ChatColor.YELLOW + exception.getHelp());
        } catch (CommandPermissionException exception) {
          sender.sendMessage(ChatColor.RED + PluginCommand.getMessage("command-not-permitted"));
          if (exception.getMessage() != null) sender.sendMessage(ChatColor.YELLOW + exception.getMessage());
          if (this.plugin.isDebugging()) {
            // if debugging is enabled output the permission that is required.
            sender.sendMessage(ChatColor.DARK_PURPLE + String.format(PluginCommand.getMessage("command-permission-required"), exception.getPermission().getName()));
          }
        } catch (CommandUsageException exception) {
          sender.sendMessage(ChatColor.RED + exception.getMessage());
        }
        break;
      }
    }
    
    return true;
   
  }
  
  /**
   * Gets the message.
   *
   * @param key the key
   * @return the message
   */
  private static String getMessage(String key) {
    return messages.getString(key);
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.command.Command#getArguments()
   */
  public Map<String, Object> getArguments() {
    return Collections.unmodifiableMap(arguments);
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.command.Command#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.command.Command#getName()
   */
  public String getName() {
    return this.name;
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.command.Command#getUsage()
   */
  public String getUsage() {
    return this.usage;
  }

  public void addPermission(Permission permission) {
    this.plugin.addPermission(permission);
    this.permissions.add(permission);
  }

  public Permission getPermission(int index) {
    return permissions.get(index);
  }

  public List<Permission> getPermissions() {
    return Collections.unmodifiableList(permissions);
  }

  public Permission getPermission(String path) {
    for (Permission permission : permissions) {
      if (permission.getName().equalsIgnoreCase(path)) return permission;
    }
    return null;
  }
  
  protected void setArguments(Map<String, Object> arguments) {
    this.arguments.clear();
    this.arguments.putAll(arguments);
  }


}
