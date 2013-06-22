package name.richardson.james.bukkit.utilities.argument;


import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;

import java.util.Locale;
import java.util.ResourceBundle;

public class StringArgument implements Argument {

    private static final ResourceBundle bundle = PluginResourceBundle.getBundle(StringArgument.class);

    private String string;

    private boolean caseInsensitive;
    private boolean required;

    @Override
    public String getValue() throws InvalidArgumentException {
        if (this.isRequired()) throw new InvalidArgumentException(bundle.getString("invalid"), null);
        return string;
    }

    @Override
    public void parseValue(Object argument) throws InvalidArgumentException {
        if (argument == null) {
            if (this.isRequired()) throw new InvalidArgumentException(bundle.getString("invalid"), null);
            this.string = null;
        } else {
            this.string = (String) argument;
            if (caseInsensitive) string = string.toLowerCase(Locale.ENGLISH);
        }
    }

    public void setCaseInsensitive(boolean value) {
        this.caseInsensitive = value;
    }

    public boolean isRequired() {
        return this.isRequired();
    }

    public void setRequired(boolean value) {
        this.required = value;
    }



}
