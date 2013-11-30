/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PermissivePrefixedLogger.java is part of BukkitUtilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Logs all messages regardless of if they can be localised or not. Messages which can not be localised are logged without modification.
 */
public class PermissivePrefixedLogger extends AbstractPrefixedLogger {

	protected PermissivePrefixedLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
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

}
