package name.richardson.james.bukkit.utilities.argument;

import org.bukkit.Server;

public abstract class PlayerArgument implements Argument {

    private final StringArgument stringArgument;

    private static Server server;
    private boolean required;

    public PlayerArgument() {
        this.stringArgument = new StringArgument();
        this.stringArgument.setCaseInsensitive(true);
    }


    public StringArgument getStringArgument() {
        return stringArgument;
    }


    public static void setServer(Server server) {
        PlayerArgument.server = server;
    }

    protected static Server getServer() {
        return PlayerArgument.server;
    }

    public boolean isRequired() {
        return this.isRequired();
    }

    public void setRequired(boolean value) {
        this.required = value;
    }

}
