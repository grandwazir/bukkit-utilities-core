package name.richardson.james.bukkit.utilities.command;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;

public final class DefaultCommandMetadata implements CommandMetadata {

	private final Localisation localisation;

	public DefaultCommandMetadata(Class classz) {
		Validate.notNull(classz, "You have no class! Haha");
		this.localisation = new ResourceBundleByClassLocalisation(classz);
	}

	@Override
	public final String getName() {
		return localisation.getMessage("name");
	}

	@Override
	public final String getDescription() {
		return localisation.getMessage("description");
	}

	@Override
	public final String getUsage() {
		return localisation.getMessage("usage");
	}

}
