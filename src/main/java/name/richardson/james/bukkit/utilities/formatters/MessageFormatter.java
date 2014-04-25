package name.richardson.james.bukkit.utilities.formatters;

/**
 * Formats a given String with {@link org.bukkit.ChatColor} codes.
 */
public interface MessageFormatter {

	public String asHeaderMessage(Object... arguments);

	public String asInfoMessage(Object... arguments);

	public String asWarningMessage(Object... arguments);

	public String asErrorMessage(Object... arguments);

}
