/*
 * Copyright (c) 2014 James Richardson.
 *
 * SwitchArgument.java is part of BukkitUtilities.
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

import java.util.Collections;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;

public class SwitchArgument extends AbstractArgument {

	public static final Pattern SWITCH_PATTERN = Pattern.compile("-(\\w+|\\w{1})");

	public SwitchArgument(final ArgumentMetadata metadata, Suggester suggester) {
		super(metadata, suggester);
	}

	@Override
	public boolean isLastArgument(final String arguments) {
		return false;
	}

	@Override
	public void parseValue(final String argument)
	throws InvalidArgumentException {
		Matcher matcher = SWITCH_PATTERN.matcher(argument);
		setValue(null);
		while (matcher.find()) {
			String match = matcher.group(1);
			if (match.equals(getName()) || match.equals(getId())) {
				setValue(String.valueOf(Boolean.TRUE));
				break;
			}
		}
	}

	/**
	 * Return all the possible matches.
	 *
	 * @param argument
	 * @return the set of possible matches.
	 */
	@Override
	public Set<String> suggestValue(final String argument) {
		return Collections.emptySet();
	}
}
