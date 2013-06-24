package name.richardson.james.bukkit.utilities.formatters.colours;

import java.text.MessageFormat;

import org.bukkit.ChatColor;

public class CoreColourScheme implements ColourScheme {

	public static final ChatColor WARNING = ChatColor.YELLOW;
	public static final ChatColor WARNING_HIGHLIGHT = ChatColor.GREEN;
	public static final ChatColor INFO = ChatColor.GREEN;
	public static final ChatColor INFO_HIGHLIGHT = ChatColor.AQUA;
	public static final ChatColor ERROR = ChatColor.RED;
	public static final ChatColor ERROR_HIGHLIGHT = ChatColor.YELLOW;
	public static final ChatColor HEADER = ChatColor.LIGHT_PURPLE;
	public static final ChatColor HEADER_HIGHLIGHT = ChatColor.AQUA;

	@Override
	public String format(Style style, String message, Object... arguments) {
		return MessageFormat.format(this.format(style, message), arguments);
	}

	public String format(Style style, String message) {
		switch (style) {
			case ERROR:
				return ERROR + message.replaceAll("\\{", ERROR_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + ERROR);
			case WARNING:
				return WARNING + message.replaceAll("\\{", WARNING_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + WARNING);
			case INFO:
				return INFO + message.replaceAll("\\{", INFO_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + INFO);
			case HEADER:
				return HEADER + message.replaceAll("\\{", HEADER_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + HEADER);
			case COMMAND_USAGE:
				message = message.replaceAll("\\<", WARNING + "\\<").replaceAll("\\>", "\\>" + WARNING);
				message = message.replaceAll("\\[", INFO + "\\[").replaceAll("\\]", "\\]" + WARNING);
				return message;
			default:
				return message;
		}
	}

}
