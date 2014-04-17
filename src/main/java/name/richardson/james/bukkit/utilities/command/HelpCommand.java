/*
 * Copyright (c) 2014 James Richardson.
 *
 * HelpCommand.java is part of BukkitUtilities.
 *
 * bukkit-utilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * bukkit-utilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * bukkit-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.command;

import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;

import name.richardson.james.bukkit.utilities.command.argument.Argument;
import name.richardson.james.bukkit.utilities.command.argument.ArgumentMetadata;
import name.richardson.james.bukkit.utilities.command.argument.PositionalArgument;
import name.richardson.james.bukkit.utilities.command.argument.SimpleArgumentMetadata;
import name.richardson.james.bukkit.utilities.command.argument.suggester.StringSuggester;
import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;

import static name.richardson.james.bukkit.utilities.localisation.PluginLocalisation.*;

public final class HelpCommand extends AbstractCommand {

	private final Argument argument;
	private final Map<String, Command> commands = new HashMap<String, Command>();
	private final String usagePrefix;

	public HelpCommand(Iterable<Command> commands, String usagePrefix) {
		super(HELPCOMMAND_NAME, HELPCOMMAND_DESC);
		this.usagePrefix = ChatColor.RED + "/" + usagePrefix;
		ArgumentMetadata metadata = new SimpleArgumentMetadata(HELPCOMMAND_ARGUMENT_ID, HELPCOMMAND_ARGUMENT_NAME, HELPCOMMAND_ARGUMENT_DESC, null);
		Suggester suggester = createSuggester(commands);
		argument = new PositionalArgument(metadata, suggester, 0);
		addArgument(argument);
		addCommands(commands);
	}

	private static Suggester createSuggester(Iterable<Command> commands) {
		Set<String> names = new HashSet<String>();
		for (Command command : commands) {
			names.add(command.getName());
		}
		return new StringSuggester(names);
	}

	@Override
	public boolean isAuthorised(final Permissible permissible) {
		return true;
	}

	@Override
	public boolean isAsynchronousCommand() {
		return true;
	}

	@Override
	protected void execute() {
		List<String> messages = new ArrayList<String>();
		Command selectedCommand = commands.get(argument.getString());
		CommandSender sender = getContext().getCommandSender();
		if (selectedCommand == null) {
			messages.add(getLocalisation().formatAsHeaderMessage(HELPCOMMAND_HEADER, getLocalisation().getMessage(PLUGIN_NAME), getLocalisation().getMessage(PLUGIN_VERSION)));
			messages.add(getLocalisation().formatAsHeaderMessage(PLUGIN_DESCRIPTION));
			messages.add(getLocalisation().formatAsWarningMessage(HELPCOMMAND_HINT, usagePrefix, getUsage()));
			for (Command command : commands.values()) {
				if (!command.isAuthorised(sender)) continue;
				messages.add(usagePrefix + " " + command.getUsage());
			}
		} else {
			messages.add(getLocalisation().formatAsHeaderMessage(HELPCOMMAND_COMMAND_USAGE_HEADER, selectedCommand.getName(), selectedCommand.getUsage()));
			messages.add(getLocalisation().formatAsInfoMessage(HELPCOMMAND_USAGE, usagePrefix, selectedCommand.getUsage()));
		}
		sender.sendMessage(messages.toArray(new String[messages.size()]));
	}

	private void addCommands(Iterable<Command> commands) {
		for (Command command : commands) {
			this.commands.put(command.getName(), command);
		}
	}

}
