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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.matcher.StringMatcher;
import name.richardson.james.bukkit.utilities.formatters.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

public class HelpCommand extends AbstractCommand {

	private final Map<String, Command> commands;
	private final PluginDescriptionFile descriptionFile;
	private final String label;

	private Command command;
	private CommandContext commandContext;

	public HelpCommand(PermissionManager permissionManager, String label, PluginDescriptionFile descriptionFile, Map<String, Command> commands) {
		super(permissionManager);
		this.descriptionFile = descriptionFile;
		this.label = label;
		this.commands = commands;
		this.addMatcher(new StringMatcher(commands.keySet()));
	}

	/**
	 * Attempt to execute the command. In this case it will attempt to find a matching command in the commandMap and dynamically display help to the {#link
	 * CommandSender}. If the command does not exist then a list of all available commands will be displayed.
	 *
	 * @param commandContext
	 */
	@Override
	public void execute(CommandContext commandContext) {
		this.commandContext = commandContext;
		this.setCommand();
		if (command == null) {
			commandContext.getCommandSender().sendMessage(String.format(ChatColor.LIGHT_PURPLE + "%s (%s)", descriptionFile.getName(), descriptionFile.getVersion()));
			commandContext.getCommandSender().sendMessage(ChatColor.AQUA + descriptionFile.getDescription());
			String usage = getColourScheme().format(ColourScheme.Style.COMMAND_USAGE, getUsage());
			String message = getColouredMessage(ColourScheme.Style.WARNING, "help-usage-hint", "/" + label, getName(), usage);
			commandContext.getCommandSender().sendMessage(message);
			for (Command command : commands.values()) {
				if (!command.isAuthorised(commandContext.getCommandSender())) continue;
				usage = getColourScheme().format(ColourScheme.Style.COMMAND_USAGE, command.getUsage());
				message = ChatColor.RED + "/" + label + " " + ChatColor.YELLOW + command.getName() + " " + usage;
				commandContext.getCommandSender().sendMessage(message);
			}
		} else {
			String message = getColourScheme().format(ColourScheme.Style.HEADER, command.getDescription());
			commandContext.getCommandSender().sendMessage(message);
			String usage = getColourScheme().format(ColourScheme.Style.COMMAND_USAGE, command.getUsage());
			message = String.format(ChatColor.RED + "/%s " + ChatColor.YELLOW + "%s %s", label, command.getName(), usage);
			commandContext.getCommandSender().sendMessage(message);
		}
	}

	private void setCommand() {
		if (commandContext.has(1)) {
			command = commands.get(commandContext.getString(1));
		} else {
			command = null;
		}
	}

}
