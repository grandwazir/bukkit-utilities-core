/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 StringMatcher.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.matcher;

import java.util.*;

public final class StringMatcher implements Matcher {

	private Set<String> strings = new HashSet<String>();

	public StringMatcher(Collection<String> strings) {
		this.setStrings(new HashSet<String>(strings));
	}

	@Override
	public Set<String> matches(String argument) {
		TreeSet<String> results = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		argument = argument.toLowerCase(Locale.ENGLISH);
		for (String string : getStrings()) {
			if (results.size() == Matcher.MAX_MATCHES) break;
			if (!string.toLowerCase(Locale.ENGLISH).startsWith(argument)) continue;
			results.add(string);
		}
		return results;
	}

	protected Set<String> getStrings() {
		return Collections.unmodifiableSet(strings);
	}

	protected void setStrings(Collection<String> strings) {
		this.strings.clear();
		this.strings.addAll(strings);
	}

}
