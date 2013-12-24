package name.richardson.james.bukkit.utilities.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.matcher.Matcher;
import name.richardson.james.bukkit.utilities.localisation.AbstractResourceBundleLocalisation;
import name.richardson.james.bukkit.utilities.localisation.FormattedLocalisation;
import name.richardson.james.bukkit.utilities.localisation.StrictResourceBundleLocalisation;

/**
 * This abstract implementation provides final methods for most of the methods provided the Command interface. It should be used for convenience when
 * implementing your own Commands.
 */
public abstract class AbstractCommand implements Command {

	private final FormattedLocalisation formattedLocalisation = new StrictResourceBundleLocalisation();
	private final List<Matcher> matchers = new ArrayList<Matcher>();
	private final String keyPrefix = this.getClass().getSimpleName().toLowerCase();

	@Override
	public final void addMatcher(Matcher matcher) {
		this.matchers.add(matcher);
	}

	@Override
	public final Set<String> getArgumentMatches(CommandContext commandContext) {
		int lastArgumentIndex = commandContext.size() - 1;
		if (commandContext.size() < lastArgumentIndex || commandContext.size() == 0) return Collections.EMPTY_SET;
		if (commandContext.size() < lastArgumentIndex || getMatchers().isEmpty()) return Collections.EMPTY_SET;
		return getMatchers().get(lastArgumentIndex).matches(commandContext.getString(lastArgumentIndex));
	}

	@Override
	public final String getDescription() {
		return formattedLocalisation.getMessage(keyPrefix + ".description");
	}

	public final FormattedLocalisation getLocalisation() {
		return formattedLocalisation;
	}

	@Override
	public final String getName() {
		return formattedLocalisation.getMessage(keyPrefix + ".name");
	}

	@Override
	public final String getUsage() {
		return formattedLocalisation.getMessage(keyPrefix + ".usage");
	}

	/**
	 * Returns an ordered list of all the matchers attached to this command.
	 *
	 * @return the matchers currently attached.
	 */
	protected final List<Matcher> getMatchers() {
		return Collections.unmodifiableList(matchers);
	}

}
