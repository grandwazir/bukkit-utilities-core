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

import org.apache.commons.lang.StringUtils;

public class PositionalArgument extends AbstractArgument {

	private static final String ARGUMENT_ISOLATOR_PATTERN = "(-\\w:\\w+)|(-\\w)";
	private final int position;

	public PositionalArgument(final String name, final String desc, final Class<?> type, int position) {
		super(name, desc, type);
		this.position = position;
	}

	@Override
	public void parseValue(final String argument) {
		if (argument != null && !argument.isEmpty()) {
			String filteredArguments = argument.replaceAll(getArgumentIsolatorPattern(), "");
			String[] arguments = StringUtils.split(filteredArguments);
			if (arguments.length - 1 >= getPosition()) {
				setValue(arguments[getPosition()]);
			} else {
				setValue(String.valueOf(Boolean.FALSE));
			}
		} else {
			setValue(String.valueOf(Boolean.FALSE));
		}
	}

	protected int getPosition() {
		return position;
	}

	protected static String getArgumentIsolatorPattern() {
		return ARGUMENT_ISOLATOR_PATTERN;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PositionalArgument{");
		sb.append("position=").append(getPosition());
		sb.append(", ").append(super.toString());
		sb.append('}');
		return sb.toString();
	}

}
