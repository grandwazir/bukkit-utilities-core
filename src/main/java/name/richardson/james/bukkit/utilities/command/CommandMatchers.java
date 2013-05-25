package name.richardson.james.bukkit.utilities.command;

import name.richardson.james.bukkit.utilities.matchers.Matcher;

public @interface CommandMatchers {
	Class<Matcher>[] matchers();
}
