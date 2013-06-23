package name.richardson.james.bukkit.utilities.command;

import java.util.List;

import org.bukkit.command.*;
import org.bukkit.command.Command;

public abstract AbstractTestCommand implements org.bukkit.command.Command, TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

	}

	@Override
	public boolean execute(CommandSender commandSender, String s, String[] strings) {

	}


}
