package name.richardson.james.bukkit.utilities.localisation;

import com.vityuk.ginger.Localizable;

public interface LocalisedMessages extends Localizable {

	String updateAvailable(String pluginName, String version);

	String updateException(String message);

	String updateRequired(String pluginName, String version);

	String updateDownloading(String pluginName, String path);

}
