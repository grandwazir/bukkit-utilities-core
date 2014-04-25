package name.richardson.james.bukkit.utilities.formatters;

import java.text.MessageFormat;

import org.bukkit.ChatColor;

/**
 * Provides colour formatting for messages depending on the severity of the message. Additionally colours arguments provided in a different colour, using the
 * formatting style one level below.
 * <p/>
 * For example formatting a error message with arguments would result in a red message and the arguments in yellow. Formatting the same message as a warning
 * would result in a yellow message with green arguments. For a full list of colours see the constants specified in this class.
 */
@SuppressWarnings("HardcodedFileSeparator")
public class DefaultMessageFormatter implements MessageFormatter {

	private static final ChatColor WARNING = ChatColor.YELLOW;
	private static final ChatColor WARNING_HIGHLIGHT = ChatColor.GREEN;
	private static final ChatColor INFO = ChatColor.GREEN;
	private static final ChatColor INFO_HIGHLIGHT = ChatColor.AQUA;
	private static final ChatColor ERROR = ChatColor.RED;
	private static final ChatColor ERROR_HIGHLIGHT = ChatColor.YELLOW;
	private static final ChatColor HEADER = ChatColor.LIGHT_PURPLE;
	private static final ChatColor HEADER_HIGHLIGHT = ChatColor.AQUA;

	@Override
	public String asHeaderMessage(final Object... arguments) {
		String message = (String) arguments[0];
		return HEADER + message.replaceAll("\\{", HEADER_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + HEADER);
	}

	@Override
	public String asInfoMessage(final Object... arguments) {
		String message = (String) arguments[0];
		return INFO + message.replaceAll("\\{", INFO_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + INFO);
	}

	@Override
	public String asWarningMessage(final Object... arguments) {
		String message = (String) arguments[0];
		return WARNING + message.replaceAll("\\{", WARNING_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + WARNING);
	}

	@Override
	public String asErrorMessage(final Object... arguments) {
		String message = (String) arguments[0];
		return ERROR + message.replaceAll("\\{", ERROR_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + ERROR);
	}

}
