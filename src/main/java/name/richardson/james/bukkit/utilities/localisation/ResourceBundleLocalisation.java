package name.richardson.james.bukkit.utilities.localisation;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.lang.Validate;

public final class ResourceBundleLocalisation implements Localisation {

	private final ResourceBundle resourceBundle;

	public ResourceBundleLocalisation(String resourceBundleName) {
		this(ResourceBundle.getBundle(resourceBundleName));
	}

	public ResourceBundleLocalisation(ResourceBundle resourceBundle) {
		Validate.notNull(resourceBundle, "resourceBundle can not be null!");
		this.resourceBundle = resourceBundle;
	}

	public String getMessage(String key) {
		return this.getResourceBundle().getString(key);
	}

	@Override
	public String getMessage(String key, Object... arguments) {
		return MessageFormat.format(getMessage(key), arguments);
	}

	protected ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

}
