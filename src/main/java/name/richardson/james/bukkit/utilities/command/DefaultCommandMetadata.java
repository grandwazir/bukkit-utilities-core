package name.richardson.james.bukkit.utilities.command;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;

/**
 * Retrieves localised metadata for a command. Internally this class uses {@link ResourceBundleByClassLocalisation} to locate an appropriate resource bundle.
 */
public final class DefaultCommandMetadata implements CommandMetadata {

	private static final String NAME_KEY = "name";
	private static final String DESCRIPTION_KEY = "description";
	private static final String USAGE_KEY = "usage";

	private final Localisation localisation;

	public DefaultCommandMetadata(Class classz) {
		Validate.notNull(classz, "You have no class! Haha");
		this.localisation = new ResourceBundleByClassLocalisation(classz);
	}

	@Override
	public final String getName() {
		return localisation.getMessage(NAME_KEY);
	}

	@Override
	public final String getDescription() {
		return localisation.getMessage(DESCRIPTION_KEY);
	}

	@Override
	public final String getUsage() {
		return localisation.getMessage(USAGE_KEY);
	}

}
