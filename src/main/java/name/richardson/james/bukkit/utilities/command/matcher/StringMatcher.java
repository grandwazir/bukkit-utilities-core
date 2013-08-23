package name.richardson.james.bukkit.utilities.command.matcher;

import java.util.*;

public final class StringMatcher implements Matcher {

	private Set<String> strings = new HashSet<String>();

	public StringMatcher(Collection<String> strings) {
		this.strings.addAll(strings);
	}

	@Override
	public Set<String> matches(String argument) {
		TreeSet<String> results = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		argument = argument.toLowerCase(Locale.ENGLISH);
		for (String string : this.strings) {
			if (results.size() == Matcher.MAX_MATCHES) break;
			if (!string.toLowerCase(Locale.ENGLISH).startsWith(argument)) continue;
			results.add(string);
		}
		return results;
	}

}
