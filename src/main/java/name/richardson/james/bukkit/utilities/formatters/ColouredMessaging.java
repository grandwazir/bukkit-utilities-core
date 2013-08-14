package name.richardson.james.bukkit.utilities.formatters;

public interface ColouredMessaging {

	public enum FormatStyle {
		HEADER,
		ERROR,
		WARNING,
		INFO,
		DEBUG
	}

	public String format(String message, FormatStyle formatStyle);

	public String format(String message, FormatStyle formatStyle, Object ... arguments);

}
