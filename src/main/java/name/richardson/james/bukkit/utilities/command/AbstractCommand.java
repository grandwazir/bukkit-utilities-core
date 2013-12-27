package name.richardson.james.bukkit.utilities.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.matcher.Matcher;
import name.richardson.james.bukkit.utilities.command.matcher.MatcherInvoker;
import name.richardson.james.bukkit.utilities.command.matcher.SimpleMatcherInvoker;
import name.richardson.james.bukkit.utilities.localisation.FormattedLocalisation;
import name.richardson.james.bukkit.utilities.localisation.StrictResourceBundleLocalisation;

/**
 * This abstract implementation provides final methods for most of the methods provided the Command interface. It should be used for convenience when
 * implementing your own Commands.
 */
public abstract class AbstractCommand implements Command, MatcherInvoker {

	private static final FormattedLocalisation LOCALISATION = new StrictResourceBundleLocalisation();

	private final String keyPrefix = this.getClass().getSimpleName().toLowerCase();
	private final MatcherInvoker matcherInvoker = new SimpleMatcherInvoker();
	private final List<Matcher> matchers = new ArrayList<Matcher>();

	/**
	 * Add a matcher to this class.
	 *
	 * @param matcher
	 * @since 6.1.0
	 */
	@Override
	public void addMatcher(Matcher matcher) {
		matcherInvoker.addMatcher(matcher);
	}

	/**
	 * Returns all the matches for the last argument passed to the CommandContext. <p/> The order in which the matchers are checked is reflected by the order in
	 * which they are passed to addMatcher.
	 *
	 * @param commandContext
	 * @return the set of possible matches
	 * @since 6.1.0
	 */
	@Override
	public Set<String> getArgumentMatches(CommandContext commandContext) {
		return matcherInvoker.getArgumentMatches(commandContext);
	}

	public final FormattedLocalisation getLocalisation() {
		return LOCALISATION;
	}

	@Override
	public final String getName() {
		return LOCALISATION.getMessage(keyPrefix + ".name");
	}

	@Override
	public final String getDescription() {
		return LOCALISATION.getMessage(keyPrefix + ".description");
	}

	@Override
	public final String getUsage() {
		return LOCALISATION.getMessage(keyPrefix + ".usage");
	}

}
