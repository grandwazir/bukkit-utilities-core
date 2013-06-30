/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CommandContext.java is part of bukkit-utilities.

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.apache.commons.lang.StringUtils;

public class CommandContext implements Context {

	private static final Matcher ARGUMENT_MATCHER = Pattern.compile("(\\w+)").matcher("");
	private static final Matcher FLAG_MATCHER = Pattern.compile("(\\w{1}):(\\w+)").matcher("");

	private final List<String> arguments = new ArrayList<String>();
	private final CommandSender commandSender;
	private final Map<String, String> flags = new HashMap<String, String>();
	private final Server server;

	public CommandContext(String[] arguments, CommandSender commandSender, Server server) {
		this(StringUtils.join(arguments, " "), commandSender, server);
	}


	public CommandContext(String[] arguments, CommandSender commandSender) {
		this(StringUtils.join(arguments, " "), commandSender, Bukkit.getServer());
	}

	private CommandContext(String arguments, CommandSender commandSender, Server server) {
		this.server = server;
		this.commandSender = commandSender;
		FLAG_MATCHER.reset(arguments);
		while (FLAG_MATCHER.find()) {
			flags.put(FLAG_MATCHER.group(1), FLAG_MATCHER.group(2));
		}
		ARGUMENT_MATCHER.reset(arguments.replaceAll(FLAG_MATCHER.pattern().toString(), ""));
		while (ARGUMENT_MATCHER.find()) {
			this.arguments.add(ARGUMENT_MATCHER.group(1));
		}
	}

	@Override
	public CommandSender getCommandSender() {
		return commandSender;
	}

	@Override
	public String getFlag(String label) {
		return flags.get(label);
	}

	@Override
	public String getJoinedArguments(int initialIndex) {
		final List<String> subList = this.arguments.subList(initialIndex, this.arguments.size());
		return StringUtils.join(subList, " ");
	}

	@Override
	public OfflinePlayer getOfflinePlayer(int index) {
		return server.getOfflinePlayer(this.arguments.get(index));
	}

	@Override
	public Player getPlayer(int index) {
		return server.getPlayer(arguments.get(index));
	}

	@Override
	public String getString(int index) {
		return this.arguments.get(index);
	}

	@Override
	public boolean has(int index) {
		return (arguments.size() < index || arguments.isEmpty() ) ? false : true;
	}

	@Override
	public boolean hasFlag(String label) {
		return flags.containsKey(label);
	}

	@Override
	public boolean isConsoleCommandSender() {
		return (!(commandSender instanceof Player));
	}

	@Override
	public int size() {
		return arguments.size();
	}

}
