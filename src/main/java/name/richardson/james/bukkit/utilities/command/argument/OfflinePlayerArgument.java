package name.richardson.james.bukkit.utilities.command.argument;


import org.bukkit.OfflinePlayer;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class OfflinePlayerArgument extends PlayerArgument {

    private OfflinePlayer player;

    public OfflinePlayer getValue() {
        return player;
    }

    public void parseValue(Object argument) throws InvalidArgumentException {
        this.getStringArgument().parseValue(argument);
        String playerName = this.getStringArgument().getValue();
        this.player = getServer().getOfflinePlayer(playerName);
    }

    @Override
    public Set<String> getMatches(String argument) {
        argument = argument.toLowerCase(Locale.ENGLISH);
        TreeSet<String> results = new TreeSet<String>();
        for (OfflinePlayer player : getServer().getOfflinePlayers()) {
            String playerName = player.getName();
            if (!playerName.toLowerCase(Locale.ENGLISH).startsWith(argument)) continue;
            results.add(playerName);
        }
        return results;
    }

}


