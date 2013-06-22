package name.richardson.james.bukkit.utilities.argument;


import org.bukkit.OfflinePlayer;

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

}


