package name.richardson.james.bukkit.utilities.command;

import name.richardson.james.bukkit.utilities.formatters.Localisation;
import name.richardson.james.bukkit.utilities.formatters.ResourceBundleLocalisation;

public final class DefaultCommandMetadata implements CommandMetadata {

	private final String keyPrefix;
	private final Localisation localisation;

	public DefaultCommandMetadata(Class classz) {
		this.keyPrefix = classz.getSimpleName();
		this.localisation = new ResourceBundleLocalisation("ted");
	}

	@Override
	public String getName() {
		return localisation.getMessage(keyPrefix + ".name");
	}

	@Override
	public String getDescription() {
		return localisation.getMessage(keyPrefix + ".description");
	}

	@Override
	public String getUsage() {
		return localisation.getMessage(keyPrefix + ".usage");
	}

}
