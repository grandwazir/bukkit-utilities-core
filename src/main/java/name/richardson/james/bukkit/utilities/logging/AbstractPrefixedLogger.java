package name.richardson.james.bukkit.utilities.logging;

import java.util.logging.*;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;

public class AbstractPrefixedLogger extends Logger implements PrefixedLogger {

	private final String debuggingPrefix;
	private final String prefix;

	protected AbstractPrefixedLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
		LogManager.getLogManager().addLogger(this);
		for (final Handler handler : getParent().getHandlers()) {
			handler.setLevel(Level.ALL);
		}
		Localisation localisation = new ResourceBundleByClassLocalisation(PrefixedLogger.class);
		prefix = localisation.getMessage("prefix") + " ";
		debuggingPrefix = "<" + this.getName() + "> ";
	}

	@Override
	public final String getDebuggingPrefix() {
		return debuggingPrefix;
	}

	@Override
	public final String getPrefix() {
		return prefix;
	}

}
