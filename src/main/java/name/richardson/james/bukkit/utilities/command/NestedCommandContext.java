/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 NestedCommandContext.java is part of bukkit-utilities.

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

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import org.apache.commons.lang.ArrayUtils;

public class NestedCommandContext extends CommandContext {

	public NestedCommandContext(String[] arguments, CommandSender commandSender, Server server) {
		super((String[]) ArrayUtils.remove(arguments, 0), commandSender, server);
	}

	public NestedCommandContext(String[] arguments, CommandSender commandSender) {
		this(arguments, commandSender, Bukkit.getServer());
	}

}
