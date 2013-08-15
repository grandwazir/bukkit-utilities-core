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

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.formatters.DefaultColourFormatter;

public final class HelpCommand extends AbstractCommand {

	private final ColourFormatter colourFormatter = new DefaultColourFormatter();
	private final Map<String, Command> commandMap = new TreeMap<String, Command>(String.CASE_INSENSITIVE_ORDER);
	private final Localisation localisation = new ResourceBundleByClassLocalisation(HelpCommand.class);
	private final String label;

	public HelpCommand(String label, Set<Command> commands) {
		Validate.notEmpty(label, "Command label can not be empty or null!");
		Validate.notEmpty(commands, "CommandMap can not be empty or null!");
		this.label = label;
		for(Command command : commands) {
			commandMap.put(command.getName(), command);
		}
	}

	@Override
	public void execute(CommandContext commandContext) {
		Validate.notNull(commandContext, "Command context may not be null!");
		CommandSender commandSender = commandContext.getCommandSender();
		Command requestedCommand = getCommand(commandContext);
		if (requestedCommand == null) {
			commandSender.sendMessage(ChatColor.LIGHT_PURPLE + localisation.getMessage("plugin-name-and-version"));
			commandSender.sendMessage(ChatColor.AQUA + localisation.getMessage("plugin-description"));
			commandSender.sendMessage(colourFormatter.format(localisation.getMessage("using-help"), ColourFormatter.FormatStyle.WARNING, "/" + label, getName(), getColouredCommandUsage(getUsage())));
			for(Command command : commandMap.values()) {
				if (!command.isAuthorised(commandSender)) continue;
				commandSender.sendMessage(getCommandUsage(command));
			}
		} else {
			commandSender.sendMessage(ChatColor.AQUA + requestedCommand.getDescription());
			commandSender.sendMessage(getCommandUsage(requestedCommand));
		}
	}

	@Override
	public boolean isAuthorised(Permissible permissible) {
		return true;
	}

	private String getColouredCommandUsage(String usage) {
		usage = usage.replaceAll("\\<", ChatColor.YELLOW + "\\<");
		usage = usage.replaceAll("\\[", ChatColor.GREEN + "\\[");
		return usage;
	}

	private Command getCommand(CommandContext commandContext) {
		if (commandContext.has(0)) {
			return commandMap.get(commandContext.getString(0));
		} else {
			return null;
		}
	}

	private String getCommandUsage(Command command) {
		String usage = getColouredCommandUsage(command.getUsage());
		String message = ChatColor.RED + "/" + label + " " + ChatColor.YELLOW + command.getName() + " " + usage;
		return message;
	}

}
