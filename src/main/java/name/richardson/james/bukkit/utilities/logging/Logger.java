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

	private final String prefix;
	private final String debugPrefix = "<" + this.getName() + "> ";
	private static final ResourceBundles DEFAULT_BUNDLE = ResourceBundles.MESSAGES;

	public Logger(Object owner, ResourceBundles bundle) {
		super(owner.getClass().getPackage().getName(), bundle.getBundleName());
		LogManager.getLogManager().addLogger(this);
		if (this.getParent().getName().isEmpty()) {
			this.setLevel(Level.INFO);
			this.setUseParentHandlers(false);
			for (Handler handler : Bukkit.getLogger().getHandlers()) {
				handler.setLevel(Level.ALL);
			}
		}
		this.prefix = this.getResourceBundle().getString("logger.prefix");
		this.test();
		this.testL();
	}

	public Logger(Object owner) {
		super(owner.getClass().getPackage().getName(), Logger.DEFAULT_BUNDLE.getBundleName());
		LogManager.getLogManager().addLogger(this);
		if (this.getParent().getName().isEmpty()) {
			this.setLevel(Level.INFO);
			for (Handler handler : Bukkit.getLogger().getHandlers()) {
				handler.setLevel(Level.ALL);
			}
		}
		this.prefix = this.getResourceBundle().getString("logger.prefix");
		this.test();
		this.testL();
	}

	@Override
	public void log(LogRecord record) {
		// Normally the localisation is handled by the formatter, however due to the
		// fact that we want to add a prefix to the start of the message we have to
		// do it here. If we leave it to the formatter to do the localising the
		// message key will be invalid after adding the prefix. In a ideal world the
		// formatter itself should add the prefix rather than us doing it here.
		// Rather than override the formatter for all plugins I settled for this
		// work around.
		String message = record.getResourceBundle().getString(record.getMessage());
		message = String.format(message, record.getParameters());
		if (this.isLoggable(Level.FINE)) {
			record.setMessage(debugPrefix + message);
		} else {
			record.setMessage(prefix + message);
		}
		super.log(record);
	}

	// private void setHandlers() {
	// if (this.getParent().getName().isEmpty()) {
	// this.setLevel(Level.INFO);
	// for (Handler handler : Bukkit.getLogger().getHandlers()) {
	// this.addHandler(handler);
	// }
	// } else {
	// this.setUseParentHandlers(true);
	// }
	// }
	private void test() {
		String levels[] = {"ALL", "INFO", "CONFIG", "WARNING", "SEVERE", "FINE", "FINEST"};
		for (String level : levels) {
			this.log(Level.parse(level), "Test message");
		}
	}

	private void testL() {
		for (String key : this.getResourceBundle().keySet()) {
			this.log(Level.FINEST, key);
			this.log(Level.FINEST, this.getResourceBundle().getString(key));
		}
	}
	// }
	// private void testAll() {
	// for (final String string : this.getResourceBundle().keySet()) {
	// Object[] params = {string, this.getResourceBundle().getString(string)};
	// this.log(Level.FINEST, "{0}: {1}", params);
	// }
	// }
}
