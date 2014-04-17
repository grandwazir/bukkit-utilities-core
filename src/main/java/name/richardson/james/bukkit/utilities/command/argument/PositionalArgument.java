/*
 * Copyright (c) 2014 James Richardson.
 *
 * PositionalArgument.java is part of BukkitUtilities.
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

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;

public class PositionalArgument extends AbstractArgument {

	private final int position;

	public PositionalArgument(ArgumentMetadata metadata, Suggester suggester, int position) {
		super(metadata, suggester);
		this.position = position;
	}

	@Override
	public void parseValue(String argument) {
		setValue(null);
		String[] arguments = getMatch(argument);
		if (arguments != null && arguments.length > 0) setValues(arguments);
	}

	protected String[] getMatch(String arguments) {
		String[] values = null;
		String[] isolatedArguments = removeOptionsAndSwitches(arguments);
		if (isolatedArguments != null || isolatedArguments.length > 0) {
			if (isolatedArguments.length - 1 >= getPosition()) {
				values = getSeparatedValues(isolatedArguments[getPosition()]);
			}
		}
		return values;
	}

	public String getUsage() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(getName());
		builder.append("]");
		return builder.toString();
	}


	protected final String[] removeOptionsAndSwitches(String arguments) {
		arguments = arguments.replaceAll(OptionArgument.getPattern().toString(), "");
		arguments = arguments.replaceAll(SwitchArgument.getPattern().toString(), "");
		return StringUtils.split(arguments);
	}

	protected int getPosition() {
		return position;
	}

	public final Set<String> suggestValue(String argument) {
		Set<String> suggestions = new HashSet<String>();
		String[] arguments = getMatch(argument);
		if (arguments != null && isLastArgument(argument)) {
			if (arguments.length - 1 == getPosition() && getSuggester() != null) {
				String[] values = getSeparatedValues(arguments[getPosition()]);
				suggestions = getSuggester().suggestValue(values[values.length]);
			}
		}
		return suggestions;
	}

	@Override
	public boolean isLastArgument(String arguments) {
		return removeOptionsAndSwitches(arguments).length - 1 == getPosition();
	}

}
