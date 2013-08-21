package name.richardson.james.bukkit.utilities.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;

/**
 * Provides final methods implementing PrefixedLogger. This class should be used as a base for implementing Loggers which care about how the actual messages are
 * localised.
 * <p/>
 * Internally will get the prefix for this logger by using {@link ResourceBundleByClassLocalisation} using this class name as the class.
 */
public class AbstractPrefixedLogger extends Logger implements PrefixedLogger {

	private static final String PREFIX_KEY = "prefix";

	private final String debuggingPrefix;
	private final String prefix;

	/**
	 * Returns a logger using the name of the class provided and a resource bundle name.
	 *
	 * @param name the name of the class that this logger belongs to.
	 * @param resourceBundleName the resource bundle the class should use.
	 */
	protected AbstractPrefixedLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
		LogManager.getLogManager().addLogger(this);
		for (final Handler handler : getParent().getHandlers()) {
			handler.setLevel(Level.ALL);
		}
		Localisation localisation = new ResourceBundleByClassLocalisation(PrefixedLogger.class);
		prefix = localisation.getMessage(PREFIX_KEY) + " ";
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
