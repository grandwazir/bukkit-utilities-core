/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 * 
 * CommandMatcher.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.matchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import name.richardson.james.bukkit.utilities.command.Command;

public class CommandMatcher implements Matcher {

	private final Map<String, Command> commands;

	public CommandMatcher(final Map<String, Command> commands) {
		this.commands = commands;
	}

	public List<String> getMatches(final String argument) {
		final Set<String> set = new TreeSet<String>();
		final List<String> list = new ArrayList<String>();
		for (final String commandName : this.commands.keySet()) {
			if (commandName.startsWith(argument)) {
				set.add(commandName);
			}
		}
		list.addAll(set);
		return list;
	}

}
