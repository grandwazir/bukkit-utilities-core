/*
 * Copyright (c) 2014 James Richardson.
 *
 * WorldMarshaller.java is part of BukkitUtilities.
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

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldMarshaller extends AbstractMarshaller {

	private final Server server;

	public WorldMarshaller(final Argument argument, Server server) {
		super(argument);
		this.server = server;
	}

	public World getWorld() {
		World world = null;
		if (getString() != null) {
			world = server.getWorld(getString());
		}
		return world;
	}


}
