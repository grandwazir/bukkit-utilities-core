package name.richardson.james.bukkit.utilities.logging;

import java.util.logging.*;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.PermissiveResourceBundleLocalisation;

/**
 * Provides final methods implementing PrefixedLogger. This class should be used as a base for implementing Loggers which care about how the actual messages are
 * localised. <p/> Internally will get the prefix for this logger by using {@link ResourceBundleByClassLocalisation} using this class name as the class.
 */
public class PrefixedLogger extends Logger {

	private static final Localisation LOCALISATION = new PermissiveResourceBundleLocalisation();
	private static String PREFIX;
	private final String debuggingPrefix;

	/**
	 * Returns a logger using the name of the class provided and a resource bundle name.
	 *
	 * @param name the name of the class that this logger belongs to.
	 * @param resourceBundleName the resource bundle the class should use.
	 */
	protected PrefixedLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
		debuggingPrefix = "<" + this.getName() + "> ";

	}

	@Override
	public void log(final LogRecord record) {
		if (this.isLoggable(Level.FINEST) || isLoggable(Level.FINE) || isLoggable(Level.FINER) || isLoggable(Level.ALL)) {
			record.setMessage(getDebuggingPrefix() + record.getMessage());
		} else {
			record.setMessage(getPrefix() + record.getMessage());
		}
		super.log(record);
	}

	public static String getPrefix() {
		return PREFIX;
	}

	public static void setPrefix(final String prefix) {
		PrefixedLogger.PREFIX = "[" + prefix + "]";
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PrefixedLogger{");
		sb.append("debuggingPrefix='").append(getDebuggingPrefix()).append('\'');
		sb.append(", ").append(super.toString());
		sb.append('}');
		return sb.toString();
	}

	protected String getDebuggingPrefix() {
		return debuggingPrefix;
	}

}
