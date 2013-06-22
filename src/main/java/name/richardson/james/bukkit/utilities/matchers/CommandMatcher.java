/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CommandMatcher.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.matchers;

import java.util.*;

import name.richardson.james.bukkit.utilities.command.Command;

/**
 * A CommandMatcher is an implementation of {@link Matcher} which matches the beginning of an argument with map of
 * of {@link Command}s.
 */
public class CommandMatcher implements Matcher {

    private static Map<String,Command> commands;

    private final TreeSet<String> sorted = new TreeSet<String>();

    public static void setCommands(Map<String, Command> commands) {
        CommandMatcher.commands = commands;
    }

	public CommandMatcher(final Map<String, Command> commands) {
		if (CommandMatcher.commands == null) throw new IllegalArgumentException("Commands can not be null!");
	}

	public List<String> getMatches(String argument) {
        argument = argument.toLowerCase(Locale.ENGLISH);
        sorted.clear();
        final List<String> list = new ArrayList<String>();
		for (final String commandName : this.commands.keySet()) {
			if (commandName.startsWith(argument)) {
                sorted.add(commandName);
			}
		}
		list.addAll(sorted);
		return list;
	}

}
