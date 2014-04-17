package name.richardson.james.bukkit.utilities.command.argument.suggester;

import java.util.*;

import com.sun.org.apache.regexp.internal.recompile;

public final class StringSuggester implements Suggester {

	private Collection<String> strings = new HashSet<String>();

	public StringSuggester(Collection<String> strings) {
		this.strings.addAll(strings);
	}

	@Override
	public Set<String> suggestValue(String argument) {
		System.out.print(argument);
		TreeSet<String> results = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		argument = argument.toLowerCase(Locale.ENGLISH);
		for (String string : this.strings) {
			if (results.size() == Suggester.MAX_MATCHES) break;
			if (!string.toLowerCase(Locale.ENGLISH).startsWith(argument)) continue;
			results.add(string);
		}
		System.out.print(this.toString());
		return results;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("StringSuggester{");
		sb.append("strings=").append(strings);
		sb.append('}');
		return sb.toString();
	}
}
