/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PluginResourceBundle.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.localisation;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class PluginResourceBundle {

	public static final String PACKAGE_PREFIX = "name.richardson.james.bukkit.";
 	public static final String RESOURCE_PREFIX = "localisation.";

	public static ResourceBundle getBundle(Class<?> owner) {
		return ResourceBundle.getBundle(getBundleName(owner));
	}

	public static String getBundleName(Class<?> owner) {
		String name = owner.getPackage().getName().replace(PACKAGE_PREFIX, "") + "." + owner.getSimpleName();
		if (name.contains("utilities")) {
			return RESOURCE_PREFIX + name.replaceFirst("\\w+.", "");
		} else {
			return RESOURCE_PREFIX  + name;
		}
	}

    public static boolean exists(Object object) {
        return exists(object.getClass());
    }

    public static boolean exists(Class<?> owner) {
        String bundleName = getBundleName(owner).replace(".", "/") + ".properties";
        if (owner.getClassLoader().getResource(bundleName) == null) {
            // System.out.append("ResourceBundle not found! " + bundleName);
        } else {
            // System.out.append("ResourceBundle found!"  + bundleName);
        }
        return owner.getClassLoader().getResource(bundleName) != null;
    }

}
