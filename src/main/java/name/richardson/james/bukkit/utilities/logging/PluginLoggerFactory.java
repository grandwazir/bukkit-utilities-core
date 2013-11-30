/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PluginLoggerFactory.java is part of BukkitUtilities.

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

import java.util.logging.LogManager;
import java.util.logging.Logger;

import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;

/**
 * Constructs appropriate loggers based on the class provided. At the moment this will always return {@link PermissivePrefixedLogger} with an attached resource
 * bundle if available.
 */
public final class PluginLoggerFactory {

	public final static Logger getLogger(Class classz) {
		final String name = classz.getPackage().getName();
		final java.util.logging.Logger logger = LogManager.getLogManager().getLogger(name);
		if (logger != null) return logger;
		String resourceBundleName = ResourceBundleByClassLocalisation.getResourceBundleName(classz);
		return new PermissivePrefixedLogger(name, null);
	}


}
