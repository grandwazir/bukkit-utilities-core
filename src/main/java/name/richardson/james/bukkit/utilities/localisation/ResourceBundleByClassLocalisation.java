package name.richardson.james.bukkit.utilities.localisation;

import org.apache.commons.lang.Validate;

/**
 * Retrieve keys from a ResourceBundle dynamically using a class name. Will throw an unchecked exception if the bundle does not exist.
 * <p/>
 * Internally looks for resource bundles on the class path by prefixing the simple class name with 'localisation'. For example the class "Test" would resolve to
 * the path "localisation/Test.properties" in the resource folder. It then delegates methods to an internal {@link ResourceBundleLocalisation} for lookup.
 */
public final class ResourceBundleByClassLocalisation implements Localisation {

	private static final String PATH_PREFIX = "localisation/";

	private final Localisation localisation;

	/**
	 * Construct a ResourceBundleByClassLocalisation using the provided class.
	 *
	 * @param classz the class to generate the resource bundle path from.
	 */
	public ResourceBundleByClassLocalisation(Class classz) {
		Validate.notNull(classz, "Class can not be null!");
		this.localisation = new ResourceBundleLocalisation(getResourceBundleName(classz));
	}

	public static final String getResourceBundleName(Class classz) {
		return PATH_PREFIX + classz.getSimpleName();
	}

	@Override
	public String getMessage(String key) {
		return localisation.getMessage(key);
	}

	@Override
	public String getMessage(String key, Object... arguments) {
		return localisation.getMessage(key, arguments);
	}

}
