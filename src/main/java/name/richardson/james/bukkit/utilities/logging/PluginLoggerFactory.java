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

import java.io.File;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class PluginLoggerFactory {

	public static Logger getLogger(Class classz) {
		final String name = classz.getPackage().getName();
		final java.util.logging.Logger logger = LogManager.getLogManager().getLogger(name);
		if (logger != null) return logger;
		String resourceBundleName = getResourceBundleName(classz);
		if (resourceExists(resourceBundleName)) {
			return new PermissivePrefixedLogger(name, getResourceBundleName(classz));
		} else {
			return new PermissivePrefixedLogger(name, null);
		}

	}

	private static final String getResourceBundleName(Class classz) {
		return "localisation" + "/" + classz.getSimpleName();
	}

	private static final boolean resourceExists(String resourcePath) {
		File f = new File(resourcePath);
		return f.exists();
	}


}
