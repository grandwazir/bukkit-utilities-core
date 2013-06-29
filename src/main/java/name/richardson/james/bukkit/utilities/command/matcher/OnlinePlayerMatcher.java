/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 OnlinePlayerMatcher.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.matcher;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

/**
 * An OnlinePlayerMatcher attempts to match an argument against a list of all the {@link Player} known to the server.
 *
 */
public class OnlinePlayerMatcher implements Matcher {

	private static final Server SERVER = Bukkit.getServer();

	@Override
	public Set<String> matches(String argument) {
		TreeSet<String> results = new TreeSet<String>();
		argument = argument.toLowerCase(Locale.ENGLISH);
		for (Player player : SERVER.getOnlinePlayers()) {
			if (results.size() == Matcher.MAX_MATCHES) break;
			if (!player.getName().startsWith(argument)) continue;
			results.add(player.getName());
		}
		return results;
	}


}
