/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 BooleanMatcher.java is part of BukkitUtilities.

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

/**
 * A BooleanMatcher matches arguments to either true or false.
 */
public class BooleanMatcher implements Matcher {

	private static final String[] values = { "true", "false" };

	public List<String> getMatches(final String argument) {
		final Set<String> set = new TreeSet<String>();
		final List<String> list = new ArrayList<String>();
		for (final String string : BooleanMatcher.values) {
			if (string.startsWith(argument)) {
				set.add(string);
			}
		}
		list.addAll(set);
		return list;
	}

}
