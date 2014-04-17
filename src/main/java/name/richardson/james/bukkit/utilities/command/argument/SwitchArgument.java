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

public class SwitchArgument extends AbstractArgument {

	private static final Pattern SWITCH_PATTERN = Pattern.compile("-(\\w+|\\w{1})");

	public SwitchArgument(final ArgumentMetadata metadata) {
		super(metadata, null);
	}

	public static Pattern getPattern() {
		return SWITCH_PATTERN;
	}

	@Override
	public boolean isLastArgument(final String arguments) {
		Pattern pattern = Pattern.compile(getPattern().toString() + "$");
		Matcher matcher = pattern.matcher(arguments);
		return matcher.find();
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

	public String getUsage() {
		StringBuilder builder = new StringBuilder();
		builder.append("[-");
		builder.append(getId());
		builder.append("|");
		builder.append(getName());
		builder.append("]");
		return builder.toString();
	}


	@Override
	protected String[] getMatch(final String argument) {
		String[] values = new String[1];
		Matcher matcher = getPattern().matcher(argument);
		while (matcher.find()) {
			String match = matcher.group(1);
			if (match.equals(getName()) || match.equals(getId())) {
				values[0] = String.valueOf(Boolean.TRUE);
				break;
			}
		}
		return values;
	}

}
