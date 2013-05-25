package name.richardson.james.bukkit.utilities.command;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import name.richardson.james.bukkit.utilities.matchers.CommandMatcher;
import name.richardson.james.bukkit.utilities.matchers.Matcher;

public class CommandManager implements TabExecutor {

	final Map<String, Command> commands = new LinkedHashMap<String,Command>();
	final Command helpCommand;
	final Matcher matcher;
	
	public CommandManager(String label, String resourceBundleName) {
		this.helpCommand = new HelpCommand(resourceBundleName, this.commands, label);
		this.matcher = new CommandMatcher(commands);
	}
	
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		final List<String> arguments = Arrays.asList(args);
		if (arguments.isEmpty()) {
			this.helpCommand.execute(arguments, sender);
		} else if (commands.containsKey(arguments.get(0))) {
			final Command command = commands.get(arguments.get(0));
			command.execute(arguments, sender);
		} else {
			this.helpCommand.execute(arguments, sender);
		}
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		final List<String> arguments = Arrays.asList(args);
		final Command command = commands.get(arguments.get(0));
		if (command != null) {
			arguments.remove(0);
			return command.onTabComplete(arguments, sender);
		} else {
			return this.matcher.getMatches(arguments.get(0));
		}
	}
  
}
