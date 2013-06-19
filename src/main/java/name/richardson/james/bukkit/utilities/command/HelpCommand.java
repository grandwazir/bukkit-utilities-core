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
import java.util.ResourceBundle;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import name.richardson.james.bukkit.utilities.localisation.LocalisedCommandSender;
import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;
import name.richardson.james.bukkit.utilities.matchers.CommandMatcher;
import name.richardson.james.bukkit.utilities.matchers.Matcher;

public class HelpCommand extends AbstractCommand {

	final static private ChatColor REQUIRED_ARGUMENT_COLOUR = ChatColor.YELLOW;
	final static private ChatColor OPTIONAL_ARGUMENT_COLOUR = ChatColor.GREEN;

	private final String label;
	private final Map<String, Command> commands;

	private final String pluginDescription;
	private final String pluginName;

	public HelpCommand(final Map<String, Command> commands, final String label,
	                   final PluginDescriptionFile description) {
		super();
		this.label = label;
		this.pluginName = description.getFullName();
		this.pluginDescription = description.getDescription();
		final ResourceBundle bundle = PluginResourceBundle.getBundle(this.getClass());
		this.commands = commands;
		final Matcher matcher = new CommandMatcher(commands);
		this.getMatchers().add(matcher);
	}

	public void execute(final List<String> arguments, final CommandSender sender) {
		LocalisedCommandSender lsender = new LocalisedCommandSender(sender, this.localisation);
		if (!arguments.isEmpty() && this.commands.containsKey(arguments.get(0))) {
			final Command command = this.commands.get(arguments.get(0));
			sender.sendMessage(ChatColor.LIGHT_PURPLE + command.getDescription());
			lsender.send("command-list-item", this.label, command.getName(), this.colouriseUsage(command.getUsage()));
		} else {
			sender.sendMessage(ChatColor.LIGHT_PURPLE + this.pluginName);
			sender.sendMessage(ChatColor.AQUA + this.pluginDescription);
			lsender.send("usage-hint", this.label, this.getName());
			for (final Command command : this.commands.values()) {
				if (command.isAuthorized(sender)) {
					lsender.send("command-list-item", this.label, command.getName(), this.colouriseUsage(command.getUsage()));
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
