package name.richardson.james.bukkit.utilities.command.argument;

import name.richardson.james.bukkit.utilities.formatters.TimeFormatter;

import java.util.Collections;
import java.util.Set;

public class TimeArgument implements Argument {

    private final StringArgument stringArgument;

    private long time;
    private boolean required;

    public TimeArgument() {
        this.stringArgument = new StringArgument();
        this.stringArgument.setCaseInsensitive(true);
        this.setRequired(true);
    }


    protected StringArgument getStringArgument() {
        return stringArgument;
    }


    @Override
    public Long getValue() throws InvalidArgumentException {
        return time;
    }

    @Override
    public void parseValue(Object argument) throws InvalidArgumentException {
        this.getStringArgument().parseValue(argument);
        String timeString = this.getStringArgument().getValue();
        if (timeString == null || timeString.isEmpty()) {
            this.time = 0;
        } else {
            this.time = TimeFormatter.parseTime(timeString);
        }
    }

    @Override
    public boolean isRequired() {
        return required;

    }

    @Override
    public void setRequired(boolean value) {
        this.required = value;
        this.getStringArgument().setRequired(value);
    }

    @Override
    public Set<String> getMatches(String argument) {
        return Collections.emptySet();
    }
}
