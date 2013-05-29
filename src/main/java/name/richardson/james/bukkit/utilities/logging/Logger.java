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

	public Logger(final Class<?> owner) {
		super(owner.getPackage().getName(), Logger.DEFAULT_BUNDLE.getBundleName());
		LogManager.getLogManager().addLogger(this);
		if ((this.getParent() == null) || this.getParent().getName().isEmpty()) {
			this.setLevel(Level.INFO);
			for (final Handler handler : Bukkit.getLogger().getHandlers()) {
				handler.setLevel(Level.ALL);
			}
		}
		this.prefix = this.getResourceBundle().getString("logger.prefix");
	}

	public Logger(final Class<?> owner, final ResourceBundles bundle) {
		super(owner.getPackage().getName(), bundle.getBundleName());
		LogManager.getLogManager().addLogger(this);
		if ((this.getParent() == null) || this.getParent().getName().isEmpty()) {
			this.setLevel(Level.INFO);
			this.setUseParentHandlers(false);
			for (final Handler handler : Bukkit.getLogger().getHandlers()) {
				handler.setLevel(Level.ALL);
			}
		}
		this.prefix = this.getResourceBundle().getString("logger.prefix");
	}

	public Logger(final Object owner) {
		super(owner.getClass().getPackage().getName(), Logger.DEFAULT_BUNDLE.getBundleName());
		LogManager.getLogManager().addLogger(this);
		if ((this.getParent() == null) || this.getParent().getName().isEmpty()) {
			this.setLevel(Level.INFO);
			for (final Handler handler : Bukkit.getLogger().getHandlers()) {
				handler.setLevel(Level.ALL);
			}
		}
		this.prefix = this.getResourceBundle().getString("logger.prefix");
	}

	public Logger(final Object owner, final ResourceBundles bundle) {
		super(owner.getClass().getPackage().getName(), bundle.getBundleName());
		LogManager.getLogManager().addLogger(this);
		if ((this.getParent() == null) || this.getParent().getName().isEmpty()) {
			this.setLevel(Level.INFO);
			this.setUseParentHandlers(false);
			for (final Handler handler : Bukkit.getLogger().getHandlers()) {
				handler.setLevel(Level.ALL);
			}
		}
		this.prefix = this.getResourceBundle().getString("logger.prefix");
	}

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
			record.setMessage(this.prefix + message);
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
	// } else {
	// level = this.getLevel();
	// }
	// System.out.append("Logger level: " + level);
	// }

}
