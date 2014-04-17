/*
 * Copyright (c) 2014 James Richardson.
 *
 * SimpleArgumentInvoker.java is part of BukkitUtilities.
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

package name.richardson.james.bukkit.utilities.command.argument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;

public class SimpleArgumentInvoker implements ArgumentInvoker {

	private List<Argument> arguments = new ArrayList<Argument>();

	@Override
	public void addArgument(final Argument argument) {
		Validate.notNull(argument);
		arguments.add(argument);
	}

	@Override
	public void parseArguments(final String arguments)
	throws InvalidArgumentException {
		for (Argument argument : this.arguments) {
			argument.parseValue(arguments);
		}
	}

	@Override
	public Set<String> suggestArguments(final String arguments) {
		Set<String> suggestions = new HashSet<String>();
		for (Argument argument : this.arguments) {
			if (argument.isLastArgument(arguments)) {
				suggestions.addAll(argument.suggestValue(arguments));
				break;
			}
		}
		return suggestions;
	}

	@Override
	public void removeArgument(final Argument argument) {
		Validate.notNull(argument);
		arguments.remove(argument);
	}

	@Override
	public String getUsage() {
		StringBuilder builder = new StringBuilder();
		for (Argument argument : this.arguments) {
			builder.append(argument.getUsage());
			builder.append(" ");
		}
		return builder.toString().trim();
	}

}
