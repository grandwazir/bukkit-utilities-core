package name.richardson.james.bukkit.utilities.matchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;

public class WorldMatcher implements Matcher {

	private final Server server;

	public WorldMatcher() {
		this.server = Bukkit.getServer();
	}

	public List<String> getMatches(String argument) {
		final Set<String> set = new TreeSet<String>();  
		final List<String> list = new ArrayList<String>();
		for (World world : this.server.getWorlds()) {
			if (world.getName().startsWith(argument)) set.add(world.getName());
		}
		list.addAll(set);
		return list;
	}
	
}
