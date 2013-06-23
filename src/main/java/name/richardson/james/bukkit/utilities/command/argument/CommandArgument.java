/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CommandArgument.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.argument;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class CommandArgument extends StringArgument {

	private static Set<String> commands = new TreeSet<String>();

	public static void setCommands(Set<String> commands) {
		CommandArgument.commands.clear();
		CommandArgument.commands.addAll(commands);
	}

	@Override
	public String getValue()
	throws InvalidArgumentException {
		String commandName = super.getValue();
		if (this.isRequired() && !commands.contains(commandName)) throw new InvalidArgumentException(null, null);
		return commandName;
	}

	@Override
	public Set<String> getMatches(String argument) {
		Set<String> results = new TreeSet<String>();
		argument = argument.toLowerCase(Locale.ENGLISH);
		for (String commandName : CommandArgument.commands) {
			if (!commandName.toLowerCase(Locale.ENGLISH).startsWith(argument)) continue;
			results.add(commandName);
		}
		return results;
	}

}
