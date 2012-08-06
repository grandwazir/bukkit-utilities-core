/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ResourceBundleLocalisation.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.localisation;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;

public class ResourceBundleLocalisation extends AbstractLocalisation {

  private final ResourceBundle[] bundles;

  public ResourceBundleLocalisation(final ResourceBundle... bundles) {
    super();
    this.bundles = bundles;
  }

  public String getMessage(final Object object, final String key) {
    return ColourFormatter.replace(this.getRawMessage(object, key));
  }

  public String getMessage(final Object object, final String key, final Object... elements) {
    final MessageFormat formatter = new MessageFormat(this.getMessage(object, key));
    formatter.setLocale(this.getLocale());
    final String message = formatter.format(elements);
    return ColourFormatter.replace(message);
  }

  private String getClassName(final Object object) {
    if (object instanceof Class) {
      final Class<?> c = (Class<?>) object;
      return c.getSimpleName().toLowerCase();
    } else {
      return object.getClass().getSimpleName().toLowerCase();
    }
  }

  private String getRawMessage(final Object object, String key) {
    key = this.getClassName(object) + "." + key;
    for (final ResourceBundle bundle : this.bundles) {
      if (bundle.containsKey(key)) {
        return bundle.getString(key);
      }
    }
    return key.toUpperCase();
  }

}
