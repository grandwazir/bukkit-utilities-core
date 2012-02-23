package name.richardson.james.bukkit.utilities.command;

import java.text.MessageFormat;
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

  private static final ResourceBundle messages = ResourceBundle.getBundle("BukkitUtilities");

  private final SimplePlugin plugin;

  private final Map<String, Command> commands = new HashMap<String, Command>();

  public CommandManager(final SimplePlugin plugin) {
    this.plugin = plugin;
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

    if (args.length != 0) {
      // if the arguments are not empty assume we have a command to execute
      final String name = args[0];
      if (this.commands.containsKey(name)) {
        // pass the details on to the relevant command for processing
        this.commands.get(name).onCommand(sender, cmd, label, args);
      } else {
        // send invalid command message to the player and tell them how to get
        // help
        sender.sendMessage(ChatColor.RED + this.getMessage("invalid-command"));
        final String[] mArgs = { cmd.getName() };
        sender.sendMessage(ChatColor.YELLOW + this.getSimpleFormattedMessage("list-all-commands", mArgs));
      }
    } else if ((args.length == 2) && args[0].equalsIgnoreCase(this.getMessage("help-command"))) {
      // if the user has provided enough arguments to look up additional help
      // for a command attempt to do so.
      final String name = args[1];
      if (this.commands.containsKey(name)) {
        // check to see if command provided is valid.
        final Command command = this.commands.get(name);
        sender.sendMessage(ChatColor.LIGHT_PURPLE + command.getDescription());
        // build argument list for formatting
        this.getCommandHelpEntry(cmd.getName(), command);
      } else {
        // inform the sender how to get the more general help
        sender.sendMessage(ChatColor.RED + this.getMessage("invalid-command"));
        final String[] mArgs = { label, this.getMessage("help") };
        sender.sendMessage(ChatColor.YELLOW + this.getSimpleFormattedMessage("list-all-commands", mArgs));
      }
    } else {
      // if no arguments have been provided, output default help
      sender.sendMessage(ChatColor.LIGHT_PURPLE + this.plugin.getDescription().getFullName());
      sender.sendMessage(ChatColor.AQUA + this.plugin.getDescription().getDescription());
      final String[] mArgs = { label, this.getMessage("help-command") };
      sender.sendMessage(ChatColor.GREEN + this.getSimpleFormattedMessage("help-header", mArgs));
      for (final Command command : this.commands.values()) {
        for (final Permission permission : command.getPermissions()) {
          if (sender.hasPermission(permission)) {
            this.getCommandHelpEntry(cmd.getName(), command);
            break;
          }
        }
      }
    }

    return true;

  }

  private String getCommandHelpEntry(final String label, final Command command) {
    final String[] mArgs = { ChatColor.YELLOW + label, ChatColor.AQUA + command.getName(), command.getColouredUsage() };
    return this.getSimpleFormattedMessage("command-entry", mArgs);
  }

  private String getMessage(final String key) {
    return CommandManager.messages.getString(key);
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.util.plugin.Localisable#getMessage(java.lang
   * .String)
   */
  private String getSimpleFormattedMessage(final String key, final Object[] arguments) {
    final MessageFormat formatter = new MessageFormat("");
    formatter.setLocale(this.plugin.getLocale());
    formatter.applyPattern(CommandManager.messages.getString(key));
    return formatter.format(arguments);
  }

}
