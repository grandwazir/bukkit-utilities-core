/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 OfflinePlayerSuggester.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.argument.suggester;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

/**
 * Matches arguments against a list of OfflinePlayer names and returns any possible matches. Used for returning a list of possible player names for tab
 * completion when using commands interactively. <p/> At the moment all known offline players are iterated each time a match request is made making this suggester
 * potentially slow on busier servers. If possible use {@link OnlinePlayerSuggester} instead or implement a custom suggester that is more accurate for your
 * requirements.
 */
public class OfflinePlayerSuggester implements Suggester {

	private final Server server;

	public OfflinePlayerSuggester(Server server) {
		this.server = server;
	}

	/**
	 * Return all offline player names that start with the specified argument. <p/> This method is case insensitive.
	 *
	 * @param argument the argument to use for matching
	 * @return the set containing all the possible matches, ordered alphabetically.
	 */
	@Override
	public Set<String> suggestValue(String argument) {
		TreeSet<String> results = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		argument = argument.toLowerCase(Locale.ENGLISH);
		for (OfflinePlayer player : server.getOfflinePlayers()) {
			if (results.size() == Suggester.MAX_MATCHES) break;
			if (!player.getName().toLowerCase(Locale.ENGLISH).startsWith(argument)) continue;
			results.add(player.getName());
		}
		return results;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("OfflinePlayerSuggester{");
		sb.append("server=").append(server);
		sb.append('}');
		return sb.toString();
	}

}
