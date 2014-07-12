package name.richardson.james.bukkit.utilities.localisation;

public interface LocalisedMessages {

	String updateAvailable(String pluginName, String version);

	String updateException(String message);

	String updateRequired(String pluginName, String version);

	String updateDownloading(String pluginName, String path);

}
