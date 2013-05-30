/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 * 
 * HelpCommand.java is part of BukkitUtilities.
 * 
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.command;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.utilities.matchers.CommandMatcher;
import name.richardson.james.bukkit.utilities.matchers.Matcher;

public class HelpCommand extends AbstractCommand {

	final static private ChatColor REQUIRED_ARGUMENT_COLOUR = ChatColor.YELLOW;
	final static private ChatColor OPTIONAL_ARGUMENT_COLOUR = ChatColor.GREEN;

	final private String label;
	final private Map<String, Command> commands;

	private final String pluginDescription;

	private final String pluginName;

	public HelpCommand(final Map<String, Command> commands, final String label) {
		super();
		this.label = label;
		this.commands = commands;
		this.pluginName = Bukkit.getPluginCommand(label).getPlugin().getDescription().getFullName();
		this.pluginDescription = this.getMessage("plugin.description");
		final Matcher matcher = new CommandMatcher(commands);
		this.getMatchers().add(matcher);
	}

	public void execute(final List<String> arguments, final CommandSender sender) {
		if (!arguments.isEmpty() && this.commands.containsKey(arguments.get(0))) {
			final Command command = this.commands.get(0);
			sender.sendMessage(command.getDescription());
			sender.sendMessage(this.getMessage("notice.helpcommand.entry", this.label, command.getName(), command.getUsage()));
		} else {
			sender.sendMessage(ChatColor.LIGHT_PURPLE + this.pluginName);
			sender.sendMessage(ChatColor.AQUA + this.pluginDescription);
			sender.sendMessage(this.getMessage("notice.helpcommand.hint", this.label, this.getName()));
			for (final Command command : this.commands.values()) {
				if (command.isAuthorized(sender)) {
					sender.sendMessage(this.getMessage("notice.helpcommand.entry", this.label, command.getName(), this.colouriseUsage(command.getUsage())));
				}
			}
		}
	}

	private String colouriseUsage(String usage) {
		usage = usage.replaceAll("<", HelpCommand.REQUIRED_ARGUMENT_COLOUR + "<");
		usage = usage.replaceAll("\\[", HelpCommand.OPTIONAL_ARGUMENT_COLOUR + "\\[");
		return usage;
	}

}
