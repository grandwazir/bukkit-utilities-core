package name.richardson.james.bukkit.utilities.argument;


import java.util.Locale;

public class SentenceArgument extends StringArgument {

    public SentenceArgument() {
        this.setCaseInsensitive(false);
    }

    public String getValue() throws InvalidArgumentException {
        String message = super.getValue();
        StringBuilder builder = new StringBuilder();
        builder.append(message.substring(0,1).toUpperCase(Locale.getDefault()));
        builder.append(message.substring(1));
        if (!builder.substring(builder.length() - 1).contentEquals(".")) builder.append(".");
        return builder.toString();
    }


}
