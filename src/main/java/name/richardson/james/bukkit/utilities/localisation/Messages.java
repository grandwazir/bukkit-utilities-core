package name.richardson.james.bukkit.utilities.localisation;

public interface Messages {

	@ColouredMessage(type = MessageType.NOTICE) String updateAvailable(String pluginName, String version);

	@ColouredMessage(type = MessageType.ERROR) String updateException(String message);

	@ColouredMessage(type = MessageType.WARNING) String updateRequired(String pluginName, String version);

	@ColouredMessage(type = MessageType.NOTICE) String updateDownloading(String pluginName, String path);

}
