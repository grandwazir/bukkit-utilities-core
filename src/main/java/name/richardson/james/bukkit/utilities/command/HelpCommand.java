package name.richardson.james.bukkit.utilities.command;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.matchers.CommandMatcher;
import name.richardson.james.bukkit.utilities.matchers.Matcher;

public class HelpCommand extends AbstractCommand {

	final static private ResourceBundles bundle = ResourceBundles.UTILITIES;
  
	final static private ChatColor REQUIRED_ARGUMENT_COLOUR = ChatColor.YELLOW;
	final static private ChatColor OPTIONAL_ARGUMENT_COLOUR = ChatColor.RED;
	
	final private String label;
	final private Map<String,Command> commands;

	private String pluginDescription;

	private String pluginName;
	
	public HelpCommand(ResourceBundles plugin, Map<String,Command> commands, String label) {
		super(HelpCommand.bundle);
		this.label = label;
		this.commands = commands;
		ResourceBundle bundle = ResourceBundle.getBundle(plugin.getBundleName());
		this.pluginName = Bukkit.getPluginCommand(label).getPlugin().getDescription().getFullName();
		this.pluginDescription = bundle.getString("plugin.description");
		final Matcher matcher = new CommandMatcher(commands);
		this.getMatchers().add(matcher);
	}
	
	public void execute(List<String> arguments, CommandSender sender) {
		if (!arguments.isEmpty() && commands.containsKey(arguments.get(0))) {
			final Command command = commands.get(0);
			sender.sendMessage(command.getDescription());
      sender.sendMessage(this.getMessage("helpcommand.help-entry", this.label, command.getName(), command.getUsage()));
		} else {
      sender.sendMessage(ChatColor.LIGHT_PURPLE + this.pluginName);
      sender.sendMessage(ChatColor.AQUA + this.pluginDescription);
      sender.sendMessage(this.getMessage("helpcommand.hint", this.label, this.getName()));
      for (final Command command : this.commands.values()) {
        if (command.isAuthorized(sender)) {
          sender.sendMessage(this.getMessage("helpcommand.help-entry", this.label, command.getName(), this.colouriseUsage(command.getUsage())));
        }
      }
		}
	}
	
	private String colouriseUsage(String usage) {
		usage.replaceAll("<", HelpCommand.REQUIRED_ARGUMENT_COLOUR + "<");
		usage.replaceAll("\\[", HelpCommand.OPTIONAL_ARGUMENT_COLOUR + "\\[");
		return usage;
	}
	
}
