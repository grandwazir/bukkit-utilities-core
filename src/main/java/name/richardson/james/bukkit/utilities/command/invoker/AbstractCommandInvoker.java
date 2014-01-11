/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractCommandInvoker.java is part of bukkit-utilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.command.invoker;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.command.Command;

/**
 * This abstract implementation provides final methods for all the methods specified in the CommandInvoker interface. It should be used for convenience when
 * implementing your own CommandInvokers.
 */
public abstract class AbstractCommandInvoker implements CommandInvoker {

	private final Map<String, Command> commandMap = new TreeMap<String, Command>(String.CASE_INSENSITIVE_ORDER);

	@Override
	public final void addCommand(Command command) {
		Validate.notNull(command);
		commandMap.put(command.getName(), command);
	}

	@Override
	public final void addCommands(Collection<Command> commands) {
		Validate.notNull(commands);
		for (Command command : commands) {
			commandMap.put(command.getName(), command);
		}
	}

	@Override
	public final Map<String, Command> getCommands() {
		return Collections.unmodifiableMap(commandMap);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("AbstractCommandInvoker{");
		sb.append("commandMap=").append(commandMap);
		sb.append('}');
		return sb.toString();
	}
}
