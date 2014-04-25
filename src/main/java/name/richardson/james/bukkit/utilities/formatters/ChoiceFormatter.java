package name.richardson.james.bukkit.utilities.formatters;

import name.richardson.james.bukkit.utilities.localisation.Localised;

public interface ChoiceFormatter {

	String getMessage();

	void setArguments(Object... arguments);

	void setFormats(String... args);

	void setFormats(Localised... args);

	void setLimits(double... limits);

	void setMessage(String message);

}
