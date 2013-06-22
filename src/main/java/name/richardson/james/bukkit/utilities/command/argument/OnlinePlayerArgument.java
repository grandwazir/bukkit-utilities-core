/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 OnlinePlayerArgument.java is part of bukkit-utilities.

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

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.entity.Player;

import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;

public class OnlinePlayerArgument extends PlayerArgument {

	private static final ResourceBundle bundle = PluginResourceBundle.getBundle(OnlinePlayerArgument.class);

	private WeakReference<Player> player;

	public Player getValue()
	throws InvalidArgumentException {
		if (player.get() == null && isRequired()) throw new InvalidArgumentException(bundle.getString("no-longer-online"), null);
		return player.get();
	}

	public void parseValue(Object argument)
	throws InvalidArgumentException {
		this.getStringArgument().parseValue(argument);
		String playerName = this.getStringArgument().getValue();
		player = new WeakReference<Player>(getServer().getPlayer(playerName));
		if (player.get() == null && isRequired()) throw new InvalidArgumentException(bundle.getString("not-online"), (String) argument);
	}

	@Override
	public Set<String> getMatches(String argument) {
		argument = argument.toLowerCase(Locale.ENGLISH);
		TreeSet<String> results = new TreeSet<String>();
		for (Player player : getServer().getOnlinePlayers()) {
			String playerName = player.getName();
			if (!playerName.toLowerCase(Locale.ENGLISH).startsWith(argument)) continue;
			results.add(playerName);
		}
		return results;
	}

}

