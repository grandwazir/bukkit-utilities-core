package name.richardson.james.bukkit.utilities.argument;


public class InvalidArgumentException extends Exception {

    private final String argument;

    public InvalidArgumentException(String message, String argument) {
        super(message);
        this.argument = argument;
    }
    
    public String getArgument() {
        return this.argument;
    }
    
}
