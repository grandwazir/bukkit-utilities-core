package name.richardson.james.bukkit.utilities.command.argument;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;


public class CommandArgument extends StringArgument {

    private static Set<String> commands;

    @Override
    public String getValue() throws InvalidArgumentException {
        String commandName = super.getValue();
        if (this.isRequired() && !commands.contains(commandName)) throw new InvalidArgumentException(null, null);
        return commandName;
    }

    public static void setCommands(Set<String> commands) {
        CommandArgument.commands = commands;
    }

    @Override
    public Set<String> getMatches(String argument) {
        Set<String> results = new TreeSet<String>();
        argument = argument.toLowerCase(Locale.ENGLISH);
        for (String commandName : CommandArgument.commands) {
            if (!commandName.toLowerCase(Locale.ENGLISH).startsWith(argument)) continue;
            results.add(commandName);
        }
        return results;
    }

}
