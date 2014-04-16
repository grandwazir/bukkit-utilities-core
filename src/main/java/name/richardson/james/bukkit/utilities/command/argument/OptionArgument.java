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

	public static final Pattern OPTION_PATTERN = Pattern.compile("-(\\w):(\\S+)");

	public OptionArgument(ArgumentMetadata metadata, Suggester suggester) {
		super(metadata, suggester);
	}

	public final Set<String> suggestValue(String argument) {
		Set<String> suggestions = new HashSet<String>();
		String[] payload = isolateOption(argument);
		if (payload != null && getSuggester() != null) {
			getSuggester().suggestValue(payload[payload.length -1]);
		}
		return suggestions;
	}

	@Override
	public boolean isLastArgument(final String arguments) {
		Pattern pattern = Pattern.compile(OPTION_PATTERN.toString() + "$");
		Matcher matcher = pattern.matcher(arguments);
		return matcher.find();
	}

	@Override
	public void parseValue(final String argument) {
		setValue(null);
		String[] payload = isolateOption(argument);
		if (payload != null) setValues(payload);
	}

	protected String[] isolateOption(String argument) {
		String[] payload = null;
		Matcher matcher = OPTION_PATTERN.matcher(argument);
		while (matcher.find()) {
			String option = matcher.group(1);
			System.out.print(option);
			if (option.equals(getName()) || option.equals(getId())) {
				payload = getSeparatedValues(matcher.group(2));
				break;
			}
		}
		return payload;
	}

}
