package name.richardson.james.bukkit.utilities.localisation;

import org.apache.commons.lang.Validate;

public final class ResourceBundleByClassLocalisation implements Localisation {

	private final Localisation localisation;

	private static final String getResourceBundleName(Class classz) {
		return "localisation" + "/" + classz.getSimpleName();
	}

	public ResourceBundleByClassLocalisation(Class classz) {
		Validate.notNull(classz, "Class can not be null!");
		this.localisation = new ResourceBundleLocalisation(getResourceBundleName(classz));
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
