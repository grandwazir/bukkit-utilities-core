/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 OfflinePlayerArgument.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.argument;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.OfflinePlayer;

public class OfflinePlayerArgument extends PlayerArgument {

	private OfflinePlayer player;

	public OfflinePlayer getValue() {
		return player;
	}

	public void parseValue(Object argument)
	throws InvalidArgumentException {
		this.getStringArgument().parseValue(argument);
		String playerName = this.getStringArgument().getValue();
		this.player = getServer().getOfflinePlayer(playerName);
	}

	@Override
	public Set<String> getMatches(String argument) {
		argument = argument.toLowerCase(Locale.ENGLISH);
		TreeSet<String> results = new TreeSet<String>();
		for (OfflinePlayer player : getServer().getOfflinePlayers()) {
			String playerName = player.getName();
			if (!playerName.toLowerCase(Locale.ENGLISH).startsWith(argument)) continue;
			results.add(playerName);
		}
		return results;
	}

}


