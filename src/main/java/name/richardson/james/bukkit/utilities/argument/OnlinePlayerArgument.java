package name.richardson.james.bukkit.utilities.argument;

import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.ResourceBundle;

public class OnlinePlayerArgument extends PlayerArgument {

    private static final ResourceBundle bundle = PluginResourceBundle.getBundle(StringArgument.class);

    private WeakReference<Player> player;

    public Player getValue() throws InvalidArgumentException {
        if (player.get() == null && isRequired()) throw new InvalidArgumentException(bundle.getString("no-longer-online"), null);
        return player.get();
    }

    public void parseValue(Object argument) throws InvalidArgumentException {
        this.getStringArgument().parseValue(argument);
        String playerName = this.getStringArgument().getValue();
        player = new WeakReference<Player>(getServer().getPlayer(playerName));
        if (player.get() == null && isRequired()) throw new InvalidArgumentException(bundle.getString("not-online"), (String) argument);
    }

}

