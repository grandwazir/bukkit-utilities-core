package name.richardson.james.bukkit.utilities.formatters;

public interface ChoiceFormatter {

	String getMessage();

	void setArguments(Object... arguments);

	void setFormats(String... args);

	void setLimits(double... limits);

	void setMessage(String message);

}
