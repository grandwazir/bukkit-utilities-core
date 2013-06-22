/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 OnlinePlayerMatcher.java is part of BukkitUtilities.

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
import org.bukkit.Server;
import org.bukkit.entity.Player;

/**
 * A CommandMatcher is an implementation of {@link Matcher} which matches the beginning of an argument with the names
 * of players who are currently online.
 */
public class OnlinePlayerMatcher implements Matcher {

    private static Server server;

    private final TreeSet<String> sorted = new TreeSet<String>();

    public OnlinePlayerMatcher() {
        OnlinePlayerMatcher.getServer();
    }

    public static void setServer(Server server) {
        OnlinePlayerMatcher.server = server;
    }

    public static Server getServer() {
        if (OnlinePlayerMatcher.server == null) {
            throw new IllegalStateException("OnlinePlayerMatcher.server has not been set!");
        } else {
            return OnlinePlayerMatcher.server;
        }
    }

	public List<String> getMatches(String argument) {
        sorted.clear();
		argument = argument.toLowerCase();
        final List<String> list = new ArrayList<String>();
		for (final Player player : getServer().getOnlinePlayers()) {
			String playerName = player.getName().toLowerCase(Locale.ENGLISH);
            if (playerName.startsWith(argument)) {
                sorted.add(player.getName());
			}
		}
		list.addAll(sorted);
        return list;
	}

}
