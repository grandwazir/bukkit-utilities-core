package name.richardson.james.bukkit.utilities.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.utilities.plugin.SimplePlugin;

public final class CommandManager implements CommandExecutor {

  private static final ResourceBundle messages = ResourceBundle.getBundle("localisation-bukkit-utils");

  private static String getMessage(String key) {
    return messages.getString(key);
  }

  private final SimplePlugin plugin;

  private final Map<String, Command> commands = new HashMap<String, Command>();

  public CommandManager(SimplePlugin plugin) {
    this.plugin = plugin;
  }

  public void addCommand(PluginCommand command) {
    commands.put(command.getName(), command);
  }
  
  public void addCommand(PluginCommand command, String name) {
    commands.put(name, command);
  }

  public Map<String, Command> getCommands() {
    return Collections.unmodifiableMap(commands);
  }
  
  public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
    
    if (args.length != 0) {
      // if the arguments are not empty assume we have a command to execute
      final String name = args[0];
      if (commands.containsKey(name)) {
        // pass the details on to the relevant command for processing
        
        commands.get(name).onCommand(sender, cmd, label, args);
      } else {
        // send invalid command message to the player and tell them how to get
        // help
        sender.sendMessage(ChatColor.RED + CommandManager.getMessage("invalid-command"));
        sender.sendMessage(ChatColor.YELLOW + String.format(CommandManager.getMessage("invalid-command-hint"), cmd.getName()));
      }
    } else if (args.length == 2 && args[0].equalsIgnoreCase(CommandManager.getMessage("plugin-help-command"))) {
      // if the user has provided enough arguments to look up additional help
      // for a command attempt to do so.
      final String name = args[1];
      if (commands.containsKey(name)) {
        // check to see if command provided is valid.
        Command command = commands.get(name);
        sender.sendMessage(ChatColor.LIGHT_PURPLE + command.getDescription());
        sender.sendMessage(ChatColor.RED + "/" + cmd.getName() + " " + command.getName() + " " + ChatColor.YELLOW + command.getUsage());
      } else {
        // inform the sender how to get the more general help
        sender.sendMessage(ChatColor.RED + String.format(CommandManager.getMessage("invalid-command"), cmd.getName()));
        sender.sendMessage(ChatColor.YELLOW + String.format(CommandManager.getMessage("plugin-help-command-usage")));
      }
    } else {
      // if no arguments have been provided, output default help
      sender.sendMessage(ChatColor.LIGHT_PURPLE + this.plugin.getDescription().getFullName());
      sender.sendMessage(ChatColor.AQUA + this.plugin.getDescription().getDescription());
      sender.sendMessage(ChatColor.GREEN + String.format(CommandManager.getMessage("plugin-help-header"), cmd.getName(), CommandManager.getMessage("plugin-help-command")));
      for (Command command : commands.values()) {
        for (Permission permission : command.getPermissions()) {
          if (sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.YELLOW + "- " + ChatColor.RED + "/" + cmd.getName() + " " + command.getName() + " " + ChatColor.YELLOW + command.getUsage());
            break;
          }
        }
      }
    }

    return true;

  }

}
