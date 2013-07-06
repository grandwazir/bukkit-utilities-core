package name.richardson.james.bukkit.utilities.logging;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.*;

import org.bukkit.Bukkit;

import name.richardson.james.bukkit.utilities.formatters.localisation.ResourceBundles;

public class PrefixedLogger extends Logger {

	private static final String RESOURCE_BUNDLE_NAME = ResourceBundles.MESSAGES.getBundleName();

	private static String PREFIX;

	public static Logger getRootLogger() {
		if (Bukkit.getServer() == null) return Logger.getLogger("");
		return Bukkit.getLogger();
	}

	protected PrefixedLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
		if ((this.getParent() == null) || this.getParent().getName().isEmpty()) {
			this.setLevel(Level.INFO);
			for (final Handler handler : getRootLogger().getHandlers()) {
				handler.setLevel(Level.ALL);
			}
			this.setUseParentHandlers(true);
			this.setParent(getRootLogger());
		}
	}

	public static Logger getLogger(Class<?> object) {
		final String name = object.getPackage().getName();
		final java.util.logging.Logger logger = LogManager.getLogManager().getLogger(name);
		if (logger == null) {
			return new PrefixedLogger(name, RESOURCE_BUNDLE_NAME);
		} else {
			return logger;
		}
	}

	public static String getPrefix() {
		return PREFIX;
	}

	public static void setPrefix(final String prefix) {
		PREFIX = prefix;
	}

	@Override
	public void log(final LogRecord record) {
		if (this.getResourceBundle() != null) {
			ResourceBundle bundle = this.getResourceBundle();
			String key = record.getMessage();
			if (bundle.containsKey(key)) {
				record.setMessage(MessageFormat.format(bundle.getString(key), record.getParameters()));
			} else {
				record.setMessage(MessageFormat.format(key, record.getParameters()));
			}
		}
		if (this.getLevel() == Level.FINEST) {
			record.setMessage("<" + this.getName() + "> " + record.getMessage());
		} else {
			record.setMessage(PREFIX + record.getMessage());
		}
		super.log(record);
	}

}
