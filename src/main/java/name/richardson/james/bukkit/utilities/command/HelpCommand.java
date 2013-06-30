/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 HelpCommand.java is part of bukkit-utilities.

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

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

import name.richardson.james.bukkit.utilities.formatters.colours.ColourScheme;

public class HelpCommand extends AbstractCommand {

	private final Map<String, Command> commands = new HashMap<String, Command>();
	private final PluginDescriptionFile descriptionFile;

	private Context context;
	private Command command;
	private String label;

	public HelpCommand(String label, PluginDescriptionFile descriptionFile) {
		this.descriptionFile = descriptionFile;
		this.label = label;
	}

	public void addCommand(Command command) {
		commands.put(command.getName(), command);
	}

	@Override
	public void execute(Context context) {
		this.context = context;
		this.setCommand();
		if (command != null) {
			String message = getColourScheme().format(ColourScheme.Style.HEADER, command.getDescription());
			context.getCommandSender().sendMessage(message);
			message = getColourScheme().format(ColourScheme.Style.COMMAND_USAGE, command.getUsage());
			context.getCommandSender().sendMessage(message);
		} else {
			context.getCommandSender().sendMessage(ChatColor.LIGHT_PURPLE + descriptionFile.getFullName());
			context.getCommandSender().sendMessage(ChatColor.AQUA + descriptionFile.getDescription());
			String usage = getColourScheme().format(ColourScheme.Style.COMMAND_USAGE, getUsage());
			String message = getColouredMessage(ColourScheme.Style.WARNING, "help-usage-hint", "/" + label, getName(), usage);
			context.getCommandSender().sendMessage(message);
			for (Command command : commands.values()) {
				if (!command.isAuthorised(context.getCommandSender())) continue;
				usage = getColourScheme().format(ColourScheme.Style.COMMAND_USAGE, getUsage());
				message = ChatColor.RED + "/" + label + " " + ChatColor.YELLOW + command.getName() + " " + usage;
				context.getCommandSender().sendMessage(message);
			}

		}
	}

	@Override
	public Set<String> getArgumentMatches(Context context) {
		Set<String> matches = new HashSet<String>();
		if (context.has(0)) {
			for (Command command : commands.values()) {
				if (command.getName().startsWith(context.getString(0).toLowerCase())) matches.add(command.getName());
			}
		}
		return matches;
	}

	private void setCommand() {
		if (context.has(0)) {
			command = commands.get(context.getString(0));
		} else {
			command = null;
		}
	}

}
