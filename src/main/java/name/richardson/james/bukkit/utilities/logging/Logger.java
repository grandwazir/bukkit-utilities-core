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
import java.util.logging.LogRecord;

import org.bukkit.Bukkit;

import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;

public final class Logger extends java.util.logging.Logger {

	private final String debugPrefix;
	private String prefix;

	public Logger(final String name) {
		super(name, ResourceBundles.MESSAGES.getBundleName());
		this.prefix = this.getResourceBundle().getString("logger.prefix");
		this.debugPrefix = "<" + this.getName() + "> ";
		this.setParent(Bukkit.getServer().getLogger());
		this.getParent().setLevel(Level.ALL);
		for (final Handler handler : Bukkit.getLogger().getParent().getHandlers()) {
			handler.setLevel(Level.ALL);
		}
	}

	@Override
	public void log(final LogRecord logRecord) {
		if (this.isLoggable(Level.FINE)) {
			logRecord.setMessage(this.debugPrefix + logRecord.getMessage());
		} else {
			logRecord.setMessage(this.prefix + logRecord.getMessage());
		}
		super.log(logRecord);
	}

	public void setPrefix(final String string) {
		this.prefix = string;
	}

	// private void test() {
	// String levels[] = {"ALL", "INFO", "CONFIG", "WARNING", "SEVERE", "FINE",
	// "FINEST"};
	// for (String level : levels) {
	// this.log(Level.parse(level), "Test message");
	// }
	// }
	private void testAll() {
		for (final String string : this.getResourceBundle().keySet()) {
			this.info(this.getResourceBundle().getString(string));
		}
	}
}
