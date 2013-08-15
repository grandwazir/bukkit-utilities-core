package name.richardson.james.bukkit.utilities.command;

import java.util.*;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.matcher.Matcher;
import name.richardson.james.bukkit.utilities.command.matcher.MatcherInvoker;

public abstract class AbstractCommand implements Command, MatcherInvoker {

	private final CommandMetadata commandMetadata = new DefaultCommandMetadata(this.getClass());
	private final List<Matcher> matchers = new ArrayList<Matcher>();

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
		return getCommandMetadata().getDescription();
	}

	@Override
	public final String getName() {
		return getCommandMetadata().getName();
	}

	@Override
	public final String getUsage() {
		return getCommandMetadata().getUsage();
	}

	protected final CommandMetadata getCommandMetadata() {
		return commandMetadata;
	}

	protected final List<Matcher> getMatchers() {
		return Collections.unmodifiableList(matchers);
	}

}
