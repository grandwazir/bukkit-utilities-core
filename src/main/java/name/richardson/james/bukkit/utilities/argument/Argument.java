package name.richardson.james.bukkit.utilities.argument;

public interface Argument {

    public Object getValue() throws InvalidArgumentException;

    public void parseValue(Object argument) throws InvalidArgumentException;

    public boolean isRequired();

    public void setRequired(boolean value);

}
