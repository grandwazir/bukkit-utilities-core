package name.richardson.james.bukkit.utilities.argument;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;

public class ServerFixture {

    public static String[] PLAYER_NAMES = {"grandwazir", "Sergeant_Subtle"};

    public static Server getServer() {
        Server server = createNiceMock(Server.class);
        Player[] players = getPlayers();
        OfflinePlayer[] offlinePlayers = getOfflinePlayers();
        expect(server.getOnlinePlayers()).andReturn(players).atLeastOnce();
        expect(server.getOfflinePlayers()).andReturn(offlinePlayers).atLeastOnce();
        expect(server.getPlayer((String) anyObject())).andReturn(players[0]);
        expect(server.getPlayer((String) anyObject())).andReturn(players[1]);
        expect(server.getPlayer((String) anyObject())).andReturn(null);
        expect(server.getOfflinePlayer((String) anyObject())).andReturn(players[0]);
        replay(server);
        return server;
    }

    public static Player[] getPlayers() {
        List<Player> players = new ArrayList<Player>(PLAYER_NAMES.length);
        for (String playerName : PLAYER_NAMES) {
            Player player = createNiceMock(Player.class);
            expect(player.getName()).andReturn(playerName).atLeastOnce();
            replay(player);
            players.add(player);
        }
        return players.toArray(new Player[players.size()]);
    }

    public static OfflinePlayer[] getOfflinePlayers() {
        List<OfflinePlayer> players = new ArrayList<OfflinePlayer>(PLAYER_NAMES.length);
        for (String playerName : PLAYER_NAMES) {
            OfflinePlayer player = createNiceMock(OfflinePlayer.class);
            expect(player.getName()).andReturn(playerName).atLeastOnce();
            replay(player);
            players.add(player);
        }
        return players.toArray(new OfflinePlayer[players.size()]);
    }

}
