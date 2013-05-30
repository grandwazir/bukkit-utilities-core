/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ConsoleLogger.java is part of BukkitUtilities.
 * 
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.bukkit.Bukkit;

import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;

public final class Logger extends java.util.logging.Logger {

	private static final ResourceBundles DEFAULT_BUNDLE = ResourceBundles.MESSAGES;

	private static String prefix;

	public static Logger getLogger(final Class<?> owner) {
		return Logger.getLogger(owner, DEFAULT_BUNDLE);
	}

	public static Logger getLogger(final Class<?> owner, final ResourceBundles bundle) {
		final String name = owner.getPackage().getName();
		final java.util.logging.Logger logger = LogManager.getLogManager().getLogger(name);
		if (logger == null) {
			return new Logger(name, bundle);
		} else {
			return (Logger) logger;
		}
	}

	public static void setPrefix(final String prefix) {
		Logger.prefix = prefix;
	}

	private final String debugPrefix = "<" + this.getName() + "> ";

	private Logger(final Class<?> owner) {
		this(owner.getPackage().getName(), Logger.DEFAULT_BUNDLE);
	}

	private Logger(final Class<?> owner, final ResourceBundles bundle) {
		this(owner.getPackage().getName(), bundle);
	}

	private Logger(final String name, final ResourceBundles bundle) {
		super(name, bundle.getBundleName());
		LogManager.getLogManager().addLogger(this);
		if ((this.getParent() == null) || this.getParent().getName().isEmpty()) {
			this.setLevel(Level.INFO);
			this.setUseParentHandlers(true);
			for (final Handler handler : Bukkit.getLogger().getHandlers()) {
				handler.setLevel(Level.ALL);
			}
		}
	}

	// private java.util.logging.Logger identifyParent() {
	// List<String> packages = new
	// LinkedList<String>(Arrays.asList(this.getName().split(".")));
	// java.util.logging.Logger logger;
	// while (logger == null || !packages.isEmpty()) {
	// packages.remove(packages.size() - 1);
	// final String name = StringFormatter.combineString(packages, ".");
	// logger = LogManager.getLogManager().getLogger(name);
	// }
	// return logger;
	// }

	@Override
	public void log(final LogRecord record) {
		// Normally the localisation is handled by the formatter, however due to the
		// fact that we want to add a prefix to the start of the message we have to
		// do it here. If we leave it to the formatter to do the localising the
		// message key will be invalid after adding the prefix. In a ideal world the
		// formatter itself should add the prefix rather than us doing it here.
		// Rather than override the formatter for all plugins I settled for this
		// work around.
		String message = record.getMessage();
		if (record.getResourceBundle().containsKey(record.getMessage())) {
			message = record.getResourceBundle().getString(record.getMessage());
		}
		message = String.format(message, record.getParameters());
		if (this.isLoggable(Level.FINE)) {
			record.setMessage(this.debugPrefix + message);
		} else {
			record.setMessage(Logger.prefix + message);
		}
		super.log(record);
	}

	// private void summary() {
	// System.out.append("Logger name: " + this.getName());
	// if (this.getParent() != null) {
	// System.out.append("Logger parent: " + this.getParent().getName());
	// }
	// Level level;
	// if (this.getLevel() == null) {
	// level = this.getParent().getLevel();
	// System.out.append("Parent logger level: " + level);
	// } else {
	// level = this.getLevel();
	// System.out.append("Logger level: " + level);
	// }
	// }

}
