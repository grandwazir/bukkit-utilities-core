package name.richardson.james.bukkit.utilities.formatters;

/**
 * Formats a given String with {@link org.bukkit.ChatColor} codes.
 */
public interface ColourFormatter {

	public enum FormatStyle {
		HEADER,
		ERROR,
		WARNING,
		INFO,
		DEBUG
	}

	/**
	 * Returns a String formatted using a FormatStyle.
	 *
	 * @param message the message to be formatted
	 * @param formatStyle the formatting style to apply
	 * @return the formatted message
	 */
	public String format(String message, FormatStyle formatStyle);

	/**
	 * Returns a String formatted with arguments using a FormatStyle.
	 *
	 * @param message the message to be formatted in MessageFormat style.
	 * @param formatStyle the formatting style to apply
	 * @param arguments the arguments to insert into the message
	 * @return the formatted message
	 */
	public String format(String message, FormatStyle formatStyle, Object ... arguments);

}
