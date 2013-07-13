/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PassthroughCommandContext.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.context;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This implementation of CommandContext is simply an unmodified extension of AbstractCommandContext.
 */
public class PassthroughCommandContext extends AbstractCommandContext {

	public PassthroughCommandContext(String[] arguments, CommandSender sender) {
		super(arguments, sender);
	}

	public PassthroughCommandContext(String[] arguments, CommandSender sender, Server server) {
		super(arguments, sender, server);
	}

}
