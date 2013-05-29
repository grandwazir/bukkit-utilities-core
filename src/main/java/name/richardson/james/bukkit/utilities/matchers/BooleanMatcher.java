package name.richardson.james.bukkit.utilities.matchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BooleanMatcher implements Matcher {

	private static final String[] values = {"true", "false"};
	
	public List<String> getMatches(String argument) {
		final Set<String> set = new TreeSet<String>();  
		final List<String> list = new ArrayList<String>();
		for (String string : values) {
			if (string.startsWith(argument)) set.add(string);
		}
		list.addAll(set);
		return list;
	}
	
}
