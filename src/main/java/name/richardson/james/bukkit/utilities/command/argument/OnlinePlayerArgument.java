package name.richardson.james.bukkit.utilities.command.argument;

import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

public class OnlinePlayerArgument extends PlayerArgument {

    private static final ResourceBundle bundle = PluginResourceBundle.getBundle(OnlinePlayerArgument.class);

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

    @Override
    public Set<String> getMatches(String argument) {
        argument = argument.toLowerCase(Locale.ENGLISH);
        TreeSet<String> results = new TreeSet<String>();
        for (Player player : getServer().getOnlinePlayers()) {
            String playerName = player.getName();
            if (!playerName.toLowerCase(Locale.ENGLISH).startsWith(argument)) continue;
            results.add(playerName);
        }
        return results;
    }

}

