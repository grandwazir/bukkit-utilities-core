package name.richardson.james.bukkit.utilities.updater;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 16/06/13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public interface PluginUpdater extends Runnable {

	String getLocalVersion();

	String getRemoteVersion();

	AbstractPluginUpdater.State getState();

	boolean isNewVersionAvailable();

	void run();

}
