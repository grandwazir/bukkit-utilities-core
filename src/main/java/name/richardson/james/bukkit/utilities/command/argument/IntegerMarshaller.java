/*
 * Copyright (c) 2014 James Richardson.
 *
 * IntegerMarshaller.java is part of BukkitUtilities.
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

public class IntegerMarshaller extends AbstractMarshaller {

	private final int defaultValue;

	public IntegerMarshaller(final Argument argument, int defaultValue) {
		super(argument);
		this.defaultValue = defaultValue;
	}

	public int getInteger() {
		if (getString() == null) {
			return defaultValue;
		} else {
			try {
				return Integer.parseInt(getString());
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
	}

}
