package name.richardson.james.bukkit.utilities.localisation;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.lang.Validate;

/**
 * Retrives keys from a ResourceBundle. Will throw an unchecked exception if the bundle does not exist.
 *
 * Internally this class uses {@code ResourceBundle.getBundle} to obtain the resource bundle for the path specified.
 */
public final class ResourceBundleLocalisation implements Localisation {

	private final ResourceBundle resourceBundle;

	/**
	 * Construct a ResourceBundleLocalisation from a name.
	 *
	 * @param name the name of the resource bundle.
	 */
	public ResourceBundleLocalisation(String name) {
		this(ResourceBundle.getBundle(name));
	}

	/**
	 * Construct a ResourceBundleLocalisation from a ResourceBundle.
	 *
	 * @param resourceBundle the resource bundle
	 */
	public ResourceBundleLocalisation(ResourceBundle resourceBundle) {
		Validate.notNull(resourceBundle, "resourceBundle can not be null!");
		this.resourceBundle = resourceBundle;
	}

	public final String getMessage(String key) {
		return this.getResourceBundle().getString(key);
	}

	@Override
	public final String getMessage(String key, Object... arguments) {
		return MessageFormat.format(getMessage(key), arguments);
	}

	protected final ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

}
