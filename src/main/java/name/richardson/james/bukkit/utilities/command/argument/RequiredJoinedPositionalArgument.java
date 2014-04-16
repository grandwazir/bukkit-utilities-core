/*
 * Copyright (c) 2014 James Richardson.
 *
 * RequiredJoinedPositionalArguments.java is part of BukkitUtilities.
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

import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;

public class RequiredJoinedPositionalArgument extends JoinedPositionalArgument {

	public RequiredJoinedPositionalArgument(ArgumentMetadata metadata, int position) {
		super(metadata, position);
	}

	@Override
	public void parseValue(final String argument)
	throws InvalidArgumentException {
		super.parseValue(argument);
		if (getString() == null) throw new InvalidArgumentException(getError());
	}

}


