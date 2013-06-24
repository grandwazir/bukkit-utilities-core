package name.richardson.james.bukkit.utilities.formatters.localisation;

import name.richardson.james.bukkit.utilities.formatters.colours.CoreColourScheme;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class LocalisedCoreColourScheme extends CoreColourScheme {

    private final ResourceBundle resourceBundle;

    public LocalisedCoreColourScheme(final ResourceBundle resourceBundle) {
        if (resourceBundle == null) throw new IllegalArgumentException("ResourceBundle can not be null!");
        this.resourceBundle = resourceBundle;
    }

    @Override
    public String format(Style style, String key, Object... arguments) {
        return MessageFormat.format(this.format(style, key), arguments);
    }

    public String format(Style style, String key) {
        String message = this.getMessage(key);
        return super.format(style, message);
    }

    public ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }

    public String getMessage(String key, Object... arguments) {
        return MessageFormat.format(this.getResourceBundle().getString(key), arguments);
    }
}
