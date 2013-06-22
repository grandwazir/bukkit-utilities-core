package name.richardson.james.bukkit.utilities.command;


import name.richardson.james.bukkit.utilities.argument.Argument;

import java.lang.annotation.Retention;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface CommandArguments {
    Class<? extends Argument>[] arguments();
}
