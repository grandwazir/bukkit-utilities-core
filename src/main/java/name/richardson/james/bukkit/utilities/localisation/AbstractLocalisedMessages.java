package name.richardson.james.bukkit.utilities.localisation;

import java.util.ResourceBundle;

public class AbstractLocalisedMessages {

	private final ResourceBundle bundle;

	protected AbstractLocalisedMessages (String path) {
		bundle = ResourceBundle.getBundle(path);
	}

	protected ResourceBundle getBundle() {
		return bundle;
	}

}
