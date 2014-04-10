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

import java.util.regex.Pattern;

public class OptionArgument extends AbstractArgument {

	private final Pattern pattern;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("OptionArgument{");
		sb.append("pattern=").append(pattern);
		sb.append(", prefix='").append(prefix).append('\'');
		sb.append(", ").append(super.toString());
		sb.append('}');
		return sb.toString();
	}

	private final String prefix;

	public OptionArgument(String name, String desc, Class<?> type) {
		super(name, desc, type);
		prefix = "-" + name.substring(0,1).toLowerCase();
		this.pattern = Pattern.compile(prefix + ":(\\w+)");
	}

	@Override
	public void parseValue(final String argument) {
		boolean containsOption = argument.contains(prefix);
		if (containsOption) {
			java.util.regex.Matcher matcher = pattern.matcher(argument);
			if (matcher.find()) {
				setValue(matcher.group(1));
			} else {
				setValue(String.valueOf(Boolean.TRUE));
			}
		} else {
			setValue(String.valueOf(Boolean.FALSE));
		}
	}

}
