/*
 * Copyright (c) 2013 James Richardson.
 *
 * SimpleMatcherInvoker.java is part of BukkitUtilities.
 *
 * bukkit-utilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * bukkit-utilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * bukkit-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.command.matcher;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;

public final class SimpleMatcherInvoker implements MatcherInvoker {

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SimpleMatcherInvoker{");
		sb.append("matchers=").append(matchers);
		sb.append('}');
		return sb.toString();
	}

	private final List<Matcher> matchers = new LinkedList<Matcher>();

	/**
	 * Add a matcher to this class.
	 *
	 * @param matcher
	 * @since 6.1.0
	 */
	@Override
	public final void addMatcher(Matcher matcher) {
		matchers.add(matcher);
	}

	/**
	 * Returns all the matches for the last argument passed to the CommandContext. <p/> The order in which the matchers are checked is reflected by the order in
	 * which they are passed to addMatcher. If no matching matcher is found an empty set is returned.
	 *
	 * @param commandContext
	 * @return the set of possible matches
	 * @since 6.1.0
	 */
	@Override
	public final Set<String> getArgumentMatches(CommandContext commandContext) {
		int argumentIndex = commandContext.size() - 1;
		Set<String> matches = new HashSet<String>();
		if (matchers.size() > argumentIndex) {
			Matcher matcher = matchers.get(argumentIndex);
			String argument = commandContext.getArgument(argumentIndex);
			matches.addAll(matcher.matches(argument));
		}
		return matches;
	}

}
