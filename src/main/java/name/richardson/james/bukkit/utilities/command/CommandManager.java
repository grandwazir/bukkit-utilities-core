package name.richardson.james.bukkit.utilities.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.utilities.plugin.Localisable;
import name.richardson.james.bukkit.utilities.plugin.SimplePlugin;

public final class CommandManager implements CommandExecutor, Localisable {

  private final SimplePlugin plugin;

  private final Map<String, Command> commands = new HashMap<String, Command>();

  /** The full name of the plugin including version */
  private String pluginName;

  /** The localised description of the plugin  */
  private String pluginDescription;

  public CommandManager(final SimplePlugin plugin) {
    this.plugin = plugin;
    this.pluginName = plugin.getDescription().getFullName();
    this.pluginDescription = plugin.getMessage("plugin-description");
  }

  public void addCommand(final PluginCommand command) {
    this.commands.put(command.getName(), command);
  }

  public void addCommand(final PluginCommand command, final String name) {
    this.commands.put(name, command);
  }

  public Map<String, Command> getCommands() {
    return Collections.unmodifiableMap(this.commands);
  }

  public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command cmd, final String label, final String[] args) {

    if (args.length == 0) {
      // display command listing and help
      sender.sendMessage(ChatColor.LIGHT_PURPLE + this.pluginName);
      sender.sendMessage(ChatColor.AQUA + this.pluginDescription);
      sender.sendMessage(ChatColor.GREEN + this.getSimpleFormattedMessage("commandmanager-help-usage", cmd.getName()));
      for (Command command : commands.values()) {
        if (command.testPermission(sender)) {
          sender.sendMessage(this.getCommandHelpEntry(label, command));
        }
      }
      return true;
    } 
      
    if (args.length != 0 && this.commands.containsKey(args[0].toLowerCase())) {
      // execute the command
      final Command command = commands.get(args[0]);
      String[] arguments = this.prepareArguments(args, args[0]);
      command.onCommand(sender, cmd, null, arguments);
      return true;
    } else if (args.length == 2 && args[0].equalsIgnoreCase(this.getMessage("commandmanager-help-command"))) {
      if (commands.containsKey(args[1]) && commands.get(args[1]).testPermission(sender)) {
        final Command command = commands.get(args[1]);
        sender.sendMessage(ChatColor.LIGHT_PURPLE + command.getDescription());
        sender.sendMessage(this.getCommandHelpEntry(label, command));
      } else {
        sender.sendMessage(ChatColor.RED + this.getMessage("commandmanager-invalid-command"));
        sender.sendMessage(ChatColor.YELLOW + this.getMessage("commandmanager-list-commands-hint"));
      }
      return true;
    } else {
      sender.sendMessage(ChatColor.RED + this.getMessage("commandmanager-invalid-command"));
      sender.sendMessage(ChatColor.YELLOW + this.getMessage("commandmanager-list-commands-hint"));
      return true;
    }

  }

  private String[] prepareArguments(String[] args, String name) {
    List<String> arguments = new ArrayList<String>();
    for (String argument : args) {
      if (!argument.equalsIgnoreCase(name)) arguments.add(argument);
    }
    return (String[]) arguments.toArray();
  }

  private String getCommandHelpEntry(final String label, final Command command) {
    String usage = command.getUsage();
    usage = usage.replaceAll("<", ChatColor.RED + "<");
    usage = usage.replaceAll("[", ChatColor.AQUA + "[");
    final String[] arguments = { ChatColor.YELLOW + label, ChatColor.AQUA + command.getName(), usage };
    return this.getSimpleFormattedMessage("commandmanager-help-entry", arguments);
  }


  public Locale getLocale() {
    return this.plugin.getLocale();
  }

  public String getMessage(String key) {
    return this.plugin.getMessage(key);
  }
  

  public String getSimpleFormattedMessage(String key, Object[] arguments) {
    return this.plugin.getSimpleFormattedMessage(key, arguments);
  }

  public String getSimpleFormattedMessage(final String key, final String argument) {
    final String[] arguments = { argument };
    return this.plugin.getSimpleFormattedMessage(key, arguments);
  }

  public String getChoiceFormattedMessage(String key, Object[] arguments, String[] formats, double[] limits) {
    return this.plugin.getChoiceFormattedMessage(key, arguments, formats, limits);
  }
  


}