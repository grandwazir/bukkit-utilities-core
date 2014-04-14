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
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.PluginDescriptionFile;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.command.argument.Argument;
import name.richardson.james.bukkit.utilities.command.argument.PositionalArgument;
import name.richardson.james.bukkit.utilities.command.argument.PlayerMarshaller;
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.matcher.StringMatcher;
import name.richardson.james.bukkit.utilities.formatters.MessageFormatter;
import name.richardson.james.bukkit.utilities.localisation.PermissiveResourceBundleLocalisation;

/**
 * Formats and returns basic help information for a {@link Command} using the {@link CommandMetadata}. Additionally it can provide a list of all known commands
 * and their usage information. <p/> This command was designed to be a fall through command for the {@link name.richardson.james.bukkit.utilities.command.invoker.FallthroughCommandInvoker}
 * providing the user with additional help if the command they request does not exist.
 */
public final class HelpCommand extends AbstractCommand {

	public static final String HINT_KEY = "helpcommand.hint";
	public static final String PLUGIN_DESCRIPTION = "helpcommand.plugin-description";
	public static final String COMMAND_DESCRIPTION_KEY = "helpcommand.command-description";
	private final Map<String, Command> commandMap = new TreeMap<String, Command>(String.CASE_INSENSITIVE_ORDER);
	private final PluginDescriptionFile descriptionFile;
	private final String label;
	private final MessageFormatter localisation = new PermissiveResourceBundleLocalisation();
	private final Argument commandArgument = new PositionalArgument("command-name", "the name of the command you want help with", String.class, 1);
	private final PlayerMarshaller playerMarshaller = new PlayerMarshaller(commandArgument, null);

	/**
	 * Construct a HelpCommand using the label and commands.
	 *
	 * @param label the label that must be prefixed for commands to be valid. This is usually the name of the {@link org.bukkit.command.PluginCommand} that the
	 * executor is attached to.
	 * @param commands the commands that the class should provide help for.
	 */
	public HelpCommand(PluginDescriptionFile descriptionFile, String label, Collection<Command> commands) {
		Validate.notEmpty(label, "Command label can not be empty or null!");
		Validate.notEmpty(commands, "CommandMap can not be empty or null!");
		this.descriptionFile = descriptionFile;
		this.label = label;
		for (Command command : commands) {
			commandMap.put(command.getName(), command);
		}
		this.addMatcher(new StringMatcher(commandMap.keySet()));
	}

	@Override
	public void execute(CommandContext commandContext) {
		Validate.notNull(commandContext, "Command context may not be null!");
		commandArgument.getString()
		CommandSender commandSender = commandContext.getCommandSender();
		Command command = setCommandFromContext(commandContext);
		if (command == null) {
			respondWithCommandList(commandSender);
		} else {
			respondWithCommandDescription(commandSender, command);
		}
	}

	/**
	 * Returns {@code true} if the user is authorised to use this command.
	 *
	 * @param permissible the permissible requesting authorisation.
	 * @return Always returns {@code true}.
	 * @since 6.0.0
	 */
	@Override
	public boolean isAuthorised(Permissible permissible) {
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("HelpCommand{");
		sb.append("commandMap=").append(commandMap);
		sb.append(", descriptionFile=").append(descriptionFile);
		sb.append(", label='").append(label).append('\'');
		sb.append(", localisation=").append(localisation);
		sb.append(", ").append(super.toString());
		sb.append('}');
		return sb.toString();
	}

	private String getColouredCommandUsage(Command command) {
		String message = command.getUsage();
		message = message.replaceAll("\\<", ChatColor.YELLOW + "\\<");
		message = message.replaceAll("\\[", ChatColor.GREEN + "\\[");
		return message;
	}

	private void respondWithCommandDescription(CommandSender commandSender, Command command) {
		String message = getLocalisation().formatAsHeaderMessage(COMMAND_DESCRIPTION_KEY, command.getDescription());
		commandSender.sendMessage(message);
		respondWithCommandUsage(commandSender, command);
	}

	private void respondWithCommandList(CommandSender commandSender) {
		commandSender.sendMessage(localisation.formatAsHeaderMessage(descriptionFile.getFullName()));
		commandSender.sendMessage((getLocalisation().formatAsHeaderMessage(PLUGIN_DESCRIPTION)));
		commandSender.sendMessage((getLocalisation().formatAsInfoMessage(HINT_KEY, "/" + label, getName(), getColouredCommandUsage(this))));
		for (Command command : commandMap.values()) {
			if (!command.isAuthorised(commandSender)) continue;
			respondWithCommandUsage(commandSender, command);
		}
	}

	private void respondWithCommandUsage(CommandSender commandSender, Command command) {
		String message = ChatColor.RED + "/" + label + " " + ChatColor.YELLOW + command.getName() + " " + getColouredCommandUsage(command);
		commandSender.sendMessage(message);
	}

	private Command setCommandFromContext(CommandContext commandContext) {
		if (commandContext.hasArgument(0)) {
			return commandMap.get(commandContext.getArgument(0));
		} else {
			return null;
		}
	}



}
