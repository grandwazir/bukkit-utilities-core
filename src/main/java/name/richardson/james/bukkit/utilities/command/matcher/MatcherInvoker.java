package name.richardson.james.bukkit.utilities.command.matcher;

import java.util.Set;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;

/**
 * MatcherInvoker represents a class that has Matchers attached to it and wishes to make those available others. By implementing this interface the class has a
 * contract to provide possible matches, if any, for any arguments passed to it through a CommandContext.
 */
public interface MatcherInvoker {

	/**
	 * Add a matcher to this class.
	 *
	 * @param matcher
	 * @since 6.1.0
	 */
	public void addMatcher(Matcher matcher);

	/**
	 * Returns all the matches for the last argument passed to the CommandContext.
	 * <p/>
	 * The order in which the matchers are checked is reflected by the order in which they are passed to addMatcher.
	 *
	 * @param commandContext
	 * @return the set of possible matches
	 * @since 6.1.0
	 */
	public Set<String> getArgumentMatches(CommandContext commandContext);

}
