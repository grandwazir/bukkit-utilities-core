package name.richardson.james.bukkit.utilities.colours;

import name.richardson.james.bukkit.utilities.localisation.Localised;

public interface ColourScheme {

    public static enum Style {
        ERROR,
        WARNING,
        INFO,
        HEADER
    }

    public String format(Style style, String message, Object... arguments);

}
