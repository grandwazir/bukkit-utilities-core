package name.richardson.james.bukkit.utilities.command.matcher;

import java.util.Set;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;

public interface MatcherInvoker {

	public void addMatcher(Matcher matcher);

	public Set<String> getArgumentMatches(CommandContext commandContext);

}
