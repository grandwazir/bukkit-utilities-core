package name.richardson.james.bukkit.utilities.command;

import java.lang.annotation.Retention;

import name.richardson.james.bukkit.utilities.matchers.Matcher;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface CommandMatchers {
	Class<? extends Matcher>[] matchers();
}
