package name.richardson.james.bukkit.utilities;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

import name.richardson.james.bukkit.utilities.configuration.SimplePluginConfiguration;

public class PluginLogger extends Logger {

	private static String prefix = "";

	private PluginLogger(Class<?> owner) {
		super(owner.getCanonicalName(), null);
		setParent(Bukkit.getServer().getLogger());
	}

	public static Logger getLogger(final Class<?> owner) {
		return new PluginLogger(owner);
	}

	public static String getPrefix() {
		return prefix;
	}

	public static void setPrefix(String prefix) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(prefix);
		sb.append("]" );
		PluginLogger.prefix = sb.toString();
	}

	public String getDebuggingPrefix() {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		sb.append(getName());
		sb.append(">" );
		return sb.toString();
	}

	@Override
	public final void log(LogRecord record) {
		if (isLoggable(Level.FINE)) {
			record.setMessage(getDebuggingPrefix() + record.getMessage());

		} else {
			record.setMessage(getPrefix() + record.getMessage());
		}
		super.log(record);
	}

}
