package name.richardson.james.bukkit.utilities.matchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class OfflinePlayerMatcher implements Matcher {

	private final Server server;

	public OfflinePlayerMatcher() {
		this.server = Bukkit.getServer();
	}

	public List<String> getMatches(String argument) {
		final Set<String> set = new TreeSet<String>();  
		final List<String> list = new ArrayList<String>();
		for (Player player : this.server.getOnlinePlayers()) {
			if (player.getName().startsWith(argument)) set.add(player.getName());
		}
		list.addAll(set);
		return list;
	}
	
}
