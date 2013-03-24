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
import java.util.Locale;
import java.util.ResourceBundle;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;

// TODO: Auto-generated Javadoc
/**
 * The Class ResourceBundleLocalisation.
 */
public class ResourceBundleLocalisation implements Localizable {

  /** The resource bundles that form this localisation. */
  private final ResourceBundle[] bundles;

  /**
   * Instantiates a new resource bundle localisation package.
   *
   * @param bundles the bundles
   */
  public ResourceBundleLocalisation(final ResourceBundle... bundles) {
    super();
    this.bundles = bundles;
  }

  /**
   * Gets the locale.
   *
   * @return the locale
   */
  public Locale getLocale() {
    return Locale.getDefault();
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.localisation.Localisation#getMessage(java.lang.Object, java.lang.String)
   */
  public String getMessage(final Object object, final String subkey) {
    String key = this.getPrefix(object) + "." + subkey;
    String message = ColourFormatter.replace(this.getRawMessage(key));
    return message;
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.localisation.Localisation#getMessage(java.lang.Object, java.lang.String, java.lang.Object[])
   */
  public String getMessage(final Object object, final String subkey, final Object... elements) {
    String key = this.getPrefix(object) + "." + subkey;
    MessageFormat formatter = new MessageFormat(this.getRawMessage(key));
    formatter.setLocale(this.getLocale());  
    String message = formatter.format(elements);
    message = ColourFormatter.replace(message);
    return message;
  }
  
  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.localisation.Localizable#getMessage(java.lang.String)
   */
  public String getMessage(String key) {
    String message = this.getRawMessage(key);
    return message;
  }
  
  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.localisation.Localizable#getMessage(java.lang.String, java.lang.Object[])
   */
  public String getMessage(String key, Object... elements) {
    MessageFormat formatter = new MessageFormat(this.getRawMessage(key));
    formatter.setLocale(this.getLocale());  
    String message = formatter.format(elements);
    message = ColourFormatter.replace(message);
    return message;
  }

  /**
   * Gets the prefix for the message.
   * 
   * Unless a string is provided it will determine the class of the object provided and return the SimpleName as a prefix.
   *
   * @param object the object
   * @return prefix
   */
  private String getPrefix(final Object object) {
    if (object instanceof Class) {
      final Class<?> c = (Class<?>) object;
      return c.getSimpleName().toLowerCase();
    } else if (object instanceof String) {
      return object.toString();
    } else {
      return object.getClass().getSimpleName().toLowerCase();
    }
  }

  /**
   * Get a raw message from a localisation bundle.
   *
   * @param key the key
   * @return message
   */
  private String getRawMessage(String key) {
    for (final ResourceBundle bundle : this.bundles) {
      if (bundle.containsKey(key)) {
        return bundle.getString(key);
      }
    }
    return key.toUpperCase() + " NOT TRANSLATED!";
  }

}
