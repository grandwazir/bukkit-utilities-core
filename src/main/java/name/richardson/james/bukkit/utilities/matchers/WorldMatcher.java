/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 WorldMatcher.java is part of BukkitUtilities.

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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;

/**
 * A CommandMatcher is an implementation of {@link Matcher} which matches the beginning of an argument with the names
 * of all the worlds that are currently loaded.
 */
public class WorldMatcher implements Matcher {

    private static Server server;

    private final TreeSet<String> sorted = new TreeSet<String>();

    public WorldMatcher() {
        WorldMatcher.getServer();
    }

    public static void setServer(Server server) {
        WorldMatcher.server = server;
    }

    public static Server getServer() {
        if (WorldMatcher.server == null) {
            throw new IllegalStateException("WorldMatcher.server has not been set!");
        } else {
            return WorldMatcher.server;
        }
    }


    public List<String> getMatches(final String argument) {
        this.sorted.clear();
		final List<String> list = new ArrayList<String>();
		for (final World world : WorldMatcher.getServer().getWorlds()) {
            String worldName = world.getName();
            if (worldName.startsWith(argument)) {
				this.sorted.add(worldName);
			}
		}
		list.addAll(this.sorted);
		return list;
	}

}
