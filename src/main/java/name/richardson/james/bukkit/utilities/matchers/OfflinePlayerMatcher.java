/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 OfflinePlayerMatcher.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.matchers;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

/**
 * A CommandMatcher is an implementation of {@link Matcher} which matches the supplied argument with the names of all
 * the players who have ever logged into the server.
 */
public class OfflinePlayerMatcher implements Matcher {

    private static Server server;

    private final TreeSet<String> sorted = new TreeSet<String>();

    public OfflinePlayerMatcher() {
        OfflinePlayerMatcher.getServer();
    }

    public static void setServer(Server server) {
        OfflinePlayerMatcher.server = server;
    }

    public static Server getServer() {
        if (OfflinePlayerMatcher.server == null) {
            throw new IllegalStateException("OfflinePlayerMatcher.server has not been set!");
        } else {
            return OfflinePlayerMatcher.server;
        }
    }

	public List<String> getMatches(String argument) {
		argument = argument.toLowerCase(Locale.ENGLISH);
        sorted.clear();
		final List<String> list = new ArrayList<String>();
		// this is here to prevent large sets disconnecting clients
		// up to around 1000 names appears to be ok at once.
		if (argument.length() != 0) {
			for (final OfflinePlayer player : getServer().getOfflinePlayers()) {
                String playerName = player.getName().toLowerCase(Locale.ENGLISH);
				if (playerName.startsWith(argument)) {
					sorted.add(player.getName());
				}
			}
		}
		list.addAll(sorted);
		return list;
	}

}
