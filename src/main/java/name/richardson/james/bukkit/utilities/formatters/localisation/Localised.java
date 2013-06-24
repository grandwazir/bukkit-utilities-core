package name.richardson.james.bukkit.utilities.formatters.localisation;

import java.util.ResourceBundle;

public interface Localised {

    public ResourceBundle getResourceBundle();

    public String getMessage(String key, Object... arguments);

}
