/*
 * Copyright (c) 2014 James Richardson.
 *
 * OptionArgument.java is part of BukkitUtilities.
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;

public class OptionArgument extends AbstractArgument {

	private static final Pattern OPTION_PATTERN = Pattern.compile("-(\\w+):(\\S*)");

	public OptionArgument(ArgumentMetadata metadata, Suggester suggester) {
		super(metadata, suggester);
	}

	@Override
	public boolean isLastArgument(final String arguments) {
		Pattern pattern = Pattern.compile(getPattern().toString() + "$");
		Matcher matcher = pattern.matcher(arguments);
		return matcher.find();
	}

	public final Set<String> suggestValue(String argument) {
		Set<String> suggestions = new HashSet<String>();
		String[] match = getMatch(argument);
		if (match != null && getSuggester() != null) {
			int index = match.length - 1;
			String value = match[index];
			suggestions.addAll(getSuggester().suggestValue(match[index]));
		}
		return suggestions;
	}

	@Override
	protected String[] getMatch(String argument) {
		String[] match = null;
		Matcher matcher = getPattern().matcher(argument);
		while (matcher.find()) {
			String option = matcher.group(1);
			if (option.equals(getName()) || option.equals(getId())) {
				match = getSeparatedValues(matcher.group(2));
				break;
			}
		}
		return match;
	}

	public String getUsage() {
		StringBuilder builder = new StringBuilder();
		builder.append("[-");
		builder.append(getId());
		builder.append("|");
		builder.append(getName());
		builder.append(":");
		builder.append("value");
		builder.append("]");
		return builder.toString();
	}

	public static Pattern getPattern() {
		return OPTION_PATTERN;
	}

}
