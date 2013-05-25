package name.richardson.james.bukkit.utilities.matchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import name.richardson.james.bukkit.utilities.command.Command;

public class CommandMatcher implements Matcher {

	private final Map<String, Command> commands;

	public CommandMatcher(Map<String, Command> commands) {
		this.commands = commands;
	}
	
	public List<String> getMatches(String argument) {
		final Set<String> set = new TreeSet<String>();  
		final List<String> list = new ArrayList<String>();
		for (String commandName : commands.keySet()) {
			if (commandName.startsWith(argument)) set.add(commandName); 
		}
		list.addAll(set);
		return list;
	}

}
