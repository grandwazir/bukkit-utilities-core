package name.richardson.james.bukkit.utilities.argument;

import java.util.Locale;

public class StringArgument implements Argument {

    private String string;
    private boolean caseInsensitive;
    private boolean required;

    @Override
    public String getValue() throws InvalidArgumentException {
        if (this.isRequired()) throw new InvalidArgumentException(null, null);
        return string;
    }

    @Override
    public void parseValue(Object argument) throws InvalidArgumentException {
        if (argument == null) {
            if (this.isRequired()) throw new InvalidArgumentException(null, null);
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
        return required;
    }

    public void setRequired(boolean value) {
        this.required = value;
    }

}
