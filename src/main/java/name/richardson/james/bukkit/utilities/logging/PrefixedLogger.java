package name.richardson.james.bukkit.utilities.logging;

import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.*;

import org.bukkit.Bukkit;

public class PrefixedLogger extends Logger {

    private static String PREFIX;

    public static void setPrefix(final String prefix) {
        PREFIX = prefix;
    }

    public static String getPrefix() {
        return PREFIX;
    }

    public static Logger getLogger(Class<?> object) {
        final String name = object.getPackage().getName();
        final java.util.logging.Logger logger = LogManager.getLogManager().getLogger(name);
        if (logger == null) {
            if (PluginResourceBundle.exists(object)) {
                return new PrefixedLogger(name, PluginResourceBundle.getBundleName(object));
            } else {
                return new PrefixedLogger(name, null);
            }
        } else {
            return logger;
        }
    }

    protected PrefixedLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
        LogManager.getLogManager().addLogger(this);
        if ((this.getParent() == null) || this.getParent().getName().isEmpty()) {
            this.setLevel(Level.INFO);
						// work around bukkit static methods, bit of a hack
						Logger parent;
						try {
							parent = Bukkit.getLogger();
						} catch (NullPointerException e) {
							parent = getLogger("");
						}
            for (final Handler handler : parent.getHandlers()) {
                handler.setLevel(Level.ALL);
            }
						this.setUseParentHandlers(true);
						this.setParent(parent);
				}
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
