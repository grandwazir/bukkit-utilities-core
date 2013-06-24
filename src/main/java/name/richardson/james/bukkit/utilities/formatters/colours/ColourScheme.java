package name.richardson.james.bukkit.utilities.formatters.colours;

public interface ColourScheme {

	public static enum Style {
		ERROR,
		WARNING,
		INFO,
		HEADER,
		COMMAND_USAGE
	}

	public String format(Style style, String message, Object... arguments);

}
