package name.richardson.james.bukkit.utilities.command;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.matchers.CommandMatcher;
import name.richardson.james.bukkit.utilities.matchers.Matcher;

public class HelpCommand extends AbstractCommand {

	final static private ResourceBundles bundle = ResourceBundles.UTILITIES;
  
	final private String label;
	final private Map<String,Command> commands;
	
	public HelpCommand(String resourceBundleName, Map<String,Command> commands, String label) {
		super(HelpCommand.bundle);
		this.label = label;
		this.commands = commands;
		final Matcher matcher = new CommandMatcher(commands);
		this.getMatchers().add(matcher);
	}
	
	public void execute(List<String> arguments, CommandSender sender) {
		if (!arguments.isEmpty() && commands.containsKey(arguments.get(0))) {
			final Command command = commands.get(0);
			sender.sendMessage(command.getDescription());
      sender.sendMessage(this.getMessage("helpcommand.help-entry", label, command.getName(), command.getUsage()));
		} else {
      sender.sendMessage(ChatColor.LIGHT_PURPLE + this.getMessage("plugin.name"));
      sender.sendMessage(ChatColor.AQUA + this.getMessage("plugin.description"));
      sender.sendMessage(this.getMessage("helpcommand.hint", label, this.getUsage()));
      for (final Command command : this.commands.values()) {
        if (command.isAuthorized(sender)) {
          sender.sendMessage(this.getMessage("helpcommand.help-entry", label, command.getName(), command.getUsage()));
        }
      }
		}
	}
	
}
