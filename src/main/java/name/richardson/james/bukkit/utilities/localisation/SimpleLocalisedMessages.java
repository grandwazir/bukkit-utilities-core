package name.richardson.james.bukkit.utilities.localisation;

import java.text.MessageFormat;

@SuppressWarnings("ChainedMethodCall")
public class SimpleLocalisedMessages extends AbstractLocalisedMessages implements LocalisedMessages {

	protected SimpleLocalisedMessages(final String path) {
		super(path);
	}

	@Override public String updateAvailable(final String pluginName, final String version) {
		String key = getBundle().getString("update.available");
		return MessageFormat.format(key, pluginName, version);
	}

	@Override public String updateException(final String message) {
		String key = getBundle().getString("update.exception");
		return MessageFormat.format(key, message);
	}

	@Override public String updateRequired(final String pluginName, final String version) {
		String key = getBundle().getString("update.required");
		return MessageFormat.format(key, pluginName, version);
	}

	@Override public String updateDownloading(final String pluginName, final String path) {
		String key = getBundle().getString("update.downloading");
		return MessageFormat.format(key, pluginName, path);
	}

}
