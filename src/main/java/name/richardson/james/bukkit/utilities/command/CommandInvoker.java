/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CommandInvoker.java is part of bukkit-utilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.command;

import java.util.*;

import org.bukkit.command.CommandSender;

public class CommandInvoker implements Invoker {

	private final Map<String, Command> commandMap = new HashMap<String, Command>();

	@Override
	public void addCommand(Command command) {
		commandMap.put(command.getName(), command);
	}

	@Override
	public Map<String, Command> getCommands() {
		return Collections.unmodifiableMap(commandMap);
	}

	@Override
	public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String commandLabel, String[] arguments) {
		Context context = new CommandContext(arguments, commandSender);
		if (context.size() != 0 && commandMap.containsKey(context.getString(0))) {
			commandMap.get(context.getString(0)).execute(new NestedCommandContext(arguments, commandSender));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String commandLabel, String[] arguments) {
		Context context = new CommandContext(arguments, commandSender);
		if (context.size() != 0 && commandMap.containsKey(context.getString(0))) {
			return new ArrayList<String>(commandMap.get(context.getString(0)).getArgumentMatches(new NestedCommandContext(arguments, commandSender)));
		} else {
			return Collections.emptyList();
		}
	}


}
