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

	public PositionalArgument(ArgumentMetadata metadata, int position) {
		super(metadata);
		this.position = position;
	}

	@Override
	public void parseValue(String argument) {
		setValue(null);
		argument = isolateArguments(argument);
		if (argument != null && !argument.isEmpty()) {
			String[] arguments = StringUtils.split(argument);
			if (arguments.length - 1 >= getPosition()) {
				setValue(arguments[getPosition()]);
			}
		}
	}

	protected int getPosition() {
		return position;
	}

	protected static String isolateArguments(String arguments) {
		String isolatedArguments = arguments.replaceAll(OptionArgument.OPTION_PATTERN.toString(), "");
		isolatedArguments = isolatedArguments.replaceAll(SwitchArgument.SWITCH_PATTERN.toString(), "");
		return isolatedArguments;
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
