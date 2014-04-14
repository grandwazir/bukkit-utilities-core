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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptionArgument extends AbstractArgument {

	public static final Pattern OPTION_PATTERN = Pattern.compile("(-\\w:)(\\S+)");

	public OptionArgument(ArgumentMetadata metadata) {
		super(metadata);
	}

	@Override
	public void parseValue(final String argument) {
		Matcher matcher = OPTION_PATTERN.matcher(argument);
		setValue(null);
		while (matcher.find()) {
			String option = matcher.group(0);
			if (option.equals(getName()) || option.equals(getId())) {
				String[] payload = getSeparatedValues(matcher.group(1));
				setValues(payload);
				break;
			}
		}
	}

}
