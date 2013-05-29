package name.richardson.james.bukkit.utilities.command;

import java.lang.annotation.Retention;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface CommandPermissions {
	String[] permissions();
}
