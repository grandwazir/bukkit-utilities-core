/*
 * Copyright (c) 2014 James Richardson.
 *
 * JoinedPositionalArgument.java is part of BukkitUtilities.
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

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class JoinedPositionalArgument extends PositionalArgument {

	public JoinedPositionalArgument(ArgumentMetadata metadata, final int position) {
		super(metadata, null, position);
	}

	@Override
	public String getString() {
		String value = null;
		if (!getStrings().isEmpty()) {
			value = StringUtils.join(getStrings(), " ");
		}
		return value;
	}

	public String getUsage() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(getName());
		builder.append("]");
		return builder.toString();
	}


	@Override
	protected String[] getMatch(final String arguments) {
		String[] values = null;
		String[] isolatedArguments = removeOptionsAndSwitches(arguments);
		if (isolatedArguments != null && isolatedArguments.length > 0) {
			if (isolatedArguments.length - 1 >= getPosition()) {
				values = (String[]) ArrayUtils.subarray(isolatedArguments, getPosition(), isolatedArguments.length);
			}
		}
		return values;
	}

}
