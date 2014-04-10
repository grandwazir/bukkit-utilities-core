/*
 * Copyright (c) 2014 James Richardson.
 *
 * RequiredOptionArgument.java is part of BukkitUtilities.
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

public class RequiredOptionArgument extends OptionArgument {

	public RequiredOptionArgument(final String name, final String desc, final Class<?> type) {
		super(name, desc, type);
	}

	@Override
	public void parseValue(final String argument) {
		super.parseValue(argument);
		if (getValue() == String.valueOf(Boolean.TRUE) || getValue() == String.valueOf(Boolean.FALSE)) throw new InvalidArgumentException();
	}

}
