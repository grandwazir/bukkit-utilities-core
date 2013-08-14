package name.richardson.james.bukkit.utilities.formatters;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.formatters.Localisation;

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
		return this.resourceBundle.getString(key);
	}

	@Override
	public String getMessage(String key, Object... arguments) {
		return MessageFormat.format(getMessage(key), arguments);
	}

}
