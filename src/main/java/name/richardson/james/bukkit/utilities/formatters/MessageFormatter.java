package name.richardson.james.bukkit.utilities.formatters;

/**
 * Formats a given String with {@link org.bukkit.ChatColor} codes.
 */
public interface MessageFormatter {

	public String formatAsHeaderMessage(String message);

	public String formatAsInfoMessage(String message);

	public String formatAsWarningMessage(String message);

	public String formatAsErrorMessage(String message);

}
