/*
 * Copyright (c) 2014 James Richardson.
 *
 * AbstractMarshaller.java is part of BukkitUtilities.
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

import java.util.Collection;
import java.util.Set;

public abstract class AbstractMarshaller implements Argument {

	private final Argument argument;

	public AbstractMarshaller(Argument argument) {
		this.argument = argument;
	}

	@Override
	public String getDescription() {
		return argument.getDescription();
	}

	@Override
	public String getId() {
		return argument.getId();
	}

	@Override
	public String getError() {
		return argument.getError();
	}

	@Override
	public String getName() {
		return argument.getName();
	}

	@Override
	public Collection<String> getStrings() {
		return argument.getStrings();
	}

	@Override
	public final String getUsage() {
		return argument.getUsage();
	}

	@Override
	public boolean isLastArgument(final String arguments) {
		return argument.isLastArgument(arguments);
	}

	@Override
	public void parseValue(final String argument)
	throws InvalidArgumentException {
		this.argument.parseValue(argument);
	}

	@Override
	public String getString() {
		return argument.getString();
	}

	@Override
	public Set<String> suggestValue(final String argument) {
		return this.argument.suggestValue(argument);
	}

}
