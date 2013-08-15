package name.richardson.james.bukkit.utilities.logging;

import java.io.File;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.*;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;

public final class PrefixedLogger extends Logger {

	private final Localisation localisation = new ResourceBundleByClassLocalisation(PrefixedLogger.class);

	protected PrefixedLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
		registerLogger();
	}

	private void registerLogger() {
		LogManager.getLogManager().addLogger(this);
		for (final Handler handler : getParent().getHandlers()) {
			handler.setLevel(Level.ALL);
		}
	}

	public static Logger getLogger(Class<?> classz) {
		final String name = classz.getPackage().getName();
		final java.util.logging.Logger logger = LogManager.getLogManager().getLogger(name);
		if (logger == null) {
			String resourceBundleName = getResourceBundleName(classz);
			if (resourceExists(resourceBundleName)) {
				return new PrefixedLogger(name, getResourceBundleName(classz));
			} else {
				return new PrefixedLogger(name, null);
			}
		} else {
			return logger;
		}
	}

	private static final String getResourceBundleName(Class classz) {
		return "localisation" + "/" + classz.getSimpleName();
	}

	private static final boolean resourceExists(String resourcePath) {
		File f = new File(resourcePath);
		return f.exists();
	}

	@Override
	public void log(final LogRecord record) {
		if (this.getResourceBundle() != null) {
			ResourceBundle bundle = this.getResourceBundle();
			String key = record.getMessage();
			if (bundle.containsKey(key)) {
				record.setMessage(MessageFormat.format(bundle.getString(key), record.getParameters()));
			} else if (record.getParameters() != null) {
				record.setMessage(MessageFormat.format(key, record.getParameters()));
			} else {
				record.setMessage(key);
			}
		}
		if (this.isLoggable(Level.FINEST) || isLoggable(Level.FINE) || isLoggable(Level.FINER) || isLoggable(Level.ALL)) {
			record.setMessage("<" + this.getName() + "> " + record.getMessage());
		} else {
			record.setMessage(localisation.getMessage("prefix") + record.getMessage());
		}
		super.log(record);
	}

}
