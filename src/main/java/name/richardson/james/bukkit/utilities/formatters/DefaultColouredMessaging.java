package name.richardson.james.bukkit.utilities.formatters;

import java.text.MessageFormat;

import org.bukkit.ChatColor;

import name.richardson.james.bukkit.utilities.formatters.ColouredMessaging;

public class DefaultColouredMessaging implements ColouredMessaging {

	private static final ChatColor WARNING = ChatColor.YELLOW;
	private static final ChatColor WARNING_HIGHLIGHT = ChatColor.GREEN;
	private static final ChatColor INFO = ChatColor.GREEN;
	private static final ChatColor INFO_HIGHLIGHT = ChatColor.AQUA;
	private static final ChatColor ERROR = ChatColor.RED;
	private static final ChatColor ERROR_HIGHLIGHT = ChatColor.YELLOW;
	private static final ChatColor HEADER = ChatColor.LIGHT_PURPLE;
	private static final ChatColor HEADER_HIGHLIGHT = ChatColor.AQUA;
	private static final ChatColor DEBUG = ChatColor.LIGHT_PURPLE;

	@Override
	public String format(String message, FormatStyle formatStyle) {
		switch (formatStyle) {
			case ERROR:
				return ERROR + message.replaceAll("\\{", ERROR_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + ERROR);
			case WARNING:
				return WARNING + message.replaceAll("\\{", WARNING_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + WARNING);
			case INFO:
				return INFO + message.replaceAll("\\{", INFO_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + INFO);
			case HEADER:
				return HEADER + message.replaceAll("\\{", HEADER_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + HEADER);
			case DEBUG:
				return DEBUG + message;
			default:
				return message;
		}
	}

	@Override
	public String format(String message, FormatStyle formatStyle, Object... arguments) {
		return MessageFormat.format(format(message, formatStyle), arguments);
	}

}
