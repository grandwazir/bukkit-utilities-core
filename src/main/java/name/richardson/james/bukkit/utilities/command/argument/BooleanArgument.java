package name.richardson.james.bukkit.utilities.command.argument;

import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

public class BooleanArgument implements Argument {

    private static final ResourceBundle RESOURCE_BUNDLE = PluginResourceBundle.getBundle(BooleanArgument.class);
    private static final String TRUE = RESOURCE_BUNDLE.getString("true");
    private static final String FALSE = RESOURCE_BUNDLE.getString("false");

    private static final String[] VALUES = {TRUE,FALSE};

    private boolean value;

    private boolean required;

    @Override
    public Boolean getValue() throws InvalidArgumentException {
        return value;
    }

    @Override
    public void parseValue(Object argument) throws InvalidArgumentException {
        String value = (String) argument;
        if (value.contentEquals(TRUE)) this.value = true;
        if (value.contentEquals(FALSE)) this.value = false;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public void setRequired(boolean value) {
        required = value;
    }

    @Override
    public Set<String> getMatches(String argument) {
        argument = argument.toLowerCase(Locale.ENGLISH);
        TreeSet<String> results = new TreeSet<String>();
        for (String value : VALUES) {
            if (!value.startsWith(argument)) continue;
            results.add(value);
        }
        return results;
    }

}
