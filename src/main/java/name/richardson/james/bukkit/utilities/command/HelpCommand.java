/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 HelpCommand.java is part of BukkitUtilities.

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

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

import name.richardson.james.bukkit.utilities.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.colours.CoreColourScheme;
import name.richardson.james.bukkit.utilities.command.argument.CommandArgument;
import name.richardson.james.bukkit.utilities.command.argument.InvalidArgumentException;

@CommandArguments(arguments = {CommandArgument.class})
public class HelpCommand extends AbstractCommand {

	final static private ChatColor REQUIRED_ARGUMENT_COLOUR = ChatColor.YELLOW;
	final static private ChatColor OPTIONAL_ARGUMENT_COLOUR = ChatColor.GREEN;

	private final String label;
	private final String pluginDescription;
	private final String pluginName;
	private final ColourScheme scheme;

	private Map<String, Command> commands;
	private String commandName;

	public HelpCommand(final String label, final PluginDescriptionFile description) {
		this.label = label;
		this.pluginName = description.getFullName();
		this.pluginDescription = description.getDescription();
		this.scheme = new CoreColourScheme();
	}

	public void execute() {
		if (commands.containsKey(commandName) && commands.get(commandName).isAuthorized(this.getCommandSender())) {
			Command command = commands.get(commandName);
			String message = this.scheme.format(ColourScheme.Style.HEADER, command.getDescription());
			getCommandSender().sendMessage(message);
			message = getColourScheme().format(ColourScheme.Style.ERROR, "list-item", this.label, command.getName(), this.colouriseUsage(this.getUsage()));
			getCommandSender().sendMessage(message);
		} else {
			String message = this.scheme.format(ColourScheme.Style.HEADER, this.pluginName);
			getCommandSender().sendMessage(message);
			getCommandSender().sendMessage(ChatColor.AQUA + this.pluginDescription);
			message = getColourScheme().format(ColourScheme.Style.WARNING, "usage-hint", ChatColor.RED + "/" + this.label, this.getName());
			getCommandSender().sendMessage(message);
			for (final Command command : this.commands.values()) {
				if (!command.isAuthorized(getCommandSender())) continue;
				message = getColourScheme().format(ColourScheme.Style.ERROR, "list-item", ChatColor.RED + "/" + this.label, command.getName(), this.colouriseUsage(command.getUsage()));
				getCommandSender().sendMessage(message);
			}
		}
	}

	public void setCommands(Map<String, Command> commands) {
		this.commands = commands;
	}

	@Override
	protected boolean parseArguments() {
		try {
			super.parseArguments();
			commandName = (String) this.getArgumentValidators().get(0).getValue();
			return true;
		} catch (InvalidArgumentException e) {
			String message = this.scheme.format(ColourScheme.Style.ERROR, e.getMessage());
			this.getCommandSender().sendMessage(message);
			return false;
		}
	}

	@Override
	protected void setArgumentValidators() {
		super.setArgumentValidators();
		this.getArgumentValidators().get(0).setRequired(false);
	}

	private String colouriseUsage(String usage) {
		usage = usage.replaceAll("<", HelpCommand.REQUIRED_ARGUMENT_COLOUR + "<");
		usage = usage.replaceAll("\\[", HelpCommand.OPTIONAL_ARGUMENT_COLOUR + "\\[");
		return usage;
	}
}
