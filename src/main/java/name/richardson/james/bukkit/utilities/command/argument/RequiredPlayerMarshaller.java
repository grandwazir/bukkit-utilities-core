/*
 * Copyright (c) 2014 James Richardson.
 *
 * RequiredPlayerMarshaller.java is part of BukkitUtilities.
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

import java.util.Set;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public class RequiredPlayerMarshaller extends PlayerMarshaller {

	public RequiredPlayerMarshaller(final Argument argument, final Server server) {
		super(argument, server);
	}

	@Override
	public Set<Player> getPlayers() {
		Set<Player> players = super.getPlayers();
		if (players.isEmpty()) throw new InvalidArgumentException(getError());
		return players;
	}

	@Override
	public Player getPlayer() {
		Player player = super.getPlayer();
		if (player == null) throw new InvalidArgumentException(getError());
		return player;
	}

}
