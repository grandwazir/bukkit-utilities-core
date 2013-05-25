package name.richardson.james.bukkit.utilities.command;


public @interface CommandPermissions {
	String[] required();
	String[] optional();
}
