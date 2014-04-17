/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 FallthroughCommandInvoker.java is part of bukkit-utilities.

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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.command.AbstractCommandInvoker;
import name.richardson.james.bukkit.utilities.command.Command;

/**
 * A FallthroughCommandInvoker attempts to match a command based on the first argument passed to it. If no command can be found the arguments `fall through` to
 * a default argument.
 */
public class FallthroughCommandInvoker extends AbstractCommandInvoker {

	private final Command fallthroughCommand;

	public FallthroughCommandInvoker(final Plugin plugin, final BukkitScheduler scheduler, Command command) {
		super(plugin, scheduler);
		this.fallthroughCommand = command;
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		Command selectedCommand = getCommand(args);
		if (selectedCommand != null) {
			CommandContext commandContext = new NestedCommandContext(args, sender);
			scheduleCommand(selectedCommand, commandContext);
		} else {
			CommandContext commandContext = new PassthroughCommandContext(args, sender);
			scheduleCommand(fallthroughCommand, commandContext);
		}
		return true;
	}

	private void scheduleCommand(Command command, CommandContext context) {
		command.setContext(context);
		if (command.isAsynchronousCommand()) {
			getScheduler().runTaskAsynchronously(getPlugin(), command);
		} else {
			getScheduler().runTask(getPlugin(), command);
		}
	}

	private Command getCommand(String[] arguments) {
		String name = (arguments.length == 0) ? null : arguments[0];
		if (name != null && getCommands().containsKey(name)) {
			return getCommands().get(name);
		} else {
			return null;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
		Command selectedCommand = getCommand(args);
		List<String> suggestions = new ArrayList<String>();
		if (selectedCommand != null) {
			CommandContext context = new NestedCommandContext(args, sender);
			String arguments = context.getArguments();
			suggestions.addAll(selectedCommand.suggestArguments(arguments));
		} else {
			CommandContext context = new PassthroughCommandContext(args, sender);
			String arguments = context.getArguments();
			suggestions.addAll(fallthroughCommand.suggestArguments(arguments));
		}
		return suggestions;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("FallthroughCommandInvoker{");
		sb.append("fallthroughCommand=").append(fallthroughCommand);
		sb.append(", ").append(super.toString());
		sb.append('}');
		return sb.toString();
	}

}
