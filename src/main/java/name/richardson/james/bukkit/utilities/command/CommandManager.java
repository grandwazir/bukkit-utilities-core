package name.richardson.james.bukkit.utilities.command;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localised;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.matchers.CommandMatcher;
import name.richardson.james.bukkit.utilities.matchers.Matcher;

public class CommandManager implements TabExecutor, Localised {

	final Map<String, Command> commands = new LinkedHashMap<String,Command>();
	final Command helpCommand;
	final Matcher matcher;
	private final static ResourceBundle localisation = ResourceBundle.getBundle(ResourceBundles.UTILITIES.getBundleName());
	
	public CommandManager(String commandName) {
		Bukkit.getServer().getPluginCommand(commandName).setExecutor(this);
		this.helpCommand = new HelpCommand(ResourceBundles.MESSAGES, this.commands, commandName);
		this.matcher = new CommandMatcher(commands);
	}
	
	public void addCommand(Command command) {
		this.commands.put(command.getName(), command);
	}
	
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		final List<String> arguments = new LinkedList<String>(Arrays.asList(args));
		if (arguments.isEmpty()) {
			this.helpCommand.execute(arguments, sender);
		} else if (commands.containsKey(arguments.get(0))) {
			final Command command = commands.get(arguments.get(0));
			arguments.remove(0);
			if (command.isAuthorized(sender)) {
				command.execute(arguments, sender);
			} else {
				sender.sendMessage(this.getMessage("permission-denied"));
			}
		} else {
			this.helpCommand.execute(arguments, sender);
		}
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		final List<String> arguments = new LinkedList<String>(Arrays.asList(args));
		final Command command = commands.get(arguments.get(0));
		if (command != null) {
			arguments.remove(0);
			return command.onTabComplete(arguments, sender);
		} else {
			return this.matcher.getMatches(arguments.get(0));
		}
	}

	public String getMessage(String key) {
    String message = localisation.getString(key);
    message = ColourFormatter.replace(message);
    return message;
	}

	public String getMessage(String key, Object... elements) {
    MessageFormat formatter = new MessageFormat(localisation.getString(key));
    formatter.setLocale(Locale.getDefault());
    String message = formatter.format(elements);
    message = ColourFormatter.replace(message);
    return message;
	}
  
}
