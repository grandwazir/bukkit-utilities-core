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

package name.richardson.james.bukkit.utilities.command;

import java.util.*;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.command.Command;
import name.richardson.james.bukkit.utilities.command.CommandInvoker;
import name.richardson.james.bukkit.utilities.command.argument.suggester.StringSuggester;
import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;

/**
 * This abstract implementation provides final methods for all the methods specified in the CommandInvoker interface. It should be used for convenience when
 * implementing your own CommandInvokers.
 */
public abstract class AbstractCommandInvoker implements CommandInvoker {

	private final Map<String, Command> commandMap = new TreeMap<String, Command>();
	private final Plugin plugin;
	private final BukkitScheduler scheduler;

	public AbstractCommandInvoker(Plugin plugin, BukkitScheduler scheduler) {
		this.plugin = plugin;
		this.scheduler = scheduler;
	}

	protected static Suggester createSuggester(Iterable<Command> commands) {
		Set<String> names = new HashSet<String>();
		for (Command command : commands) {
			names.add(command.getName());
		}
		return new StringSuggester(names);
	}

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

	protected void scheduleCommand(Command command, CommandContext context) {
		command.setContext(context);
		if (command.isAsynchronousCommand()) {
			getScheduler().runTaskAsynchronously(getPlugin(), command);
		} else {
			getScheduler().runTask(getPlugin(), command);
		}
	}

	protected Command getCommand(String[] arguments) {
		String name = (arguments.length == 0) ? null : arguments[0];
		if (name != null && getCommands().containsKey(name)) {
			return getCommands().get(name);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("AbstractCommandInvoker{");
		sb.append("commandMap=").append(commandMap);
		sb.append('}');
		return sb.toString();
	}

	protected final Plugin getPlugin() {
		return plugin;
	}

	protected final BukkitScheduler getScheduler() {
		return scheduler;
	}
}
