/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * Localisable.java is part of BukkitUtilities.
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

import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Localisable interface represents an object which is able to localise
 * strings for output to the user.
 */
public interface Localisable {

  /**
   * Gets the choice formatted message.
   * 
   * This method returns a localised message replacing place holder in the
   * string with the arguments provided. Additionally it alters the formatting
   * of the message depending on the formats and limits provided. This can
   * return messages such as "1 ban" or "2 bans".
   * 
   * @param key Specifies the key of the message to look up in the
   *          ResourceBundle.
   * @param formats The formats to use when matched to a limit. For example
   *          {"no bans", "one ban"}.
   * @param limits The limits as Doubles. For example {0,1}.
   * @param choice The format to use.
   * @return The localised message matching the key supplied and with the
   *         placeholder replaced.
   */
  public String getChoiceFormattedMessage(String key, Object[] arguments, String[] formats, double[] limits);

  /**
   * Gets the current locale.
   * 
   * This should return the locale of the messages returned by the class
   * implementing this interface.
   * 
   * @return the locale
   */
  public Locale getLocale();

  /**
   * Gets a localised message by providing a message key.
   * 
   * This returns a string from the ResourceBundle, providing that it exists.
   * This method does not do any additional formatting beyond returning the
   * localised message.
   * 
   * @param key Specifies the key of the message to look up in the
   *          ResourceBundle.
   * @return The localised message matching the key supplied.
   */
  public String getMessage(String key);

  /**
   * Gets the simple formatted message.
   * 
   * This method returns a localised message replacing place holder in the
   * string with the argument provided. It is a convenience method to avoid
   * creating an array of a single String.
   * 
   * @param key Specifies the key of the message to look up in the
   *          ResourceBundle.
   * @param argument The argument to use to format the message and replace the
   *          place holder.
   * @return The localised message matching the key supplied and with the
   *         placeholder replaced.
   */
  public String getSimpleFormattedMessage(String key, Object argument);

  /**
   * Gets the simple formatted message.
   * 
   * This method returns a localised message replacing place holders in the
   * string with the arguments provided.
   * 
   * @param key Specifies the key of the message to look up in the
   *          ResourceBundle.
   * @param arguments The arguments to use to format the message and replace the
   *          place holders.
   * @return The localised message matching the key supplied and with the
   *         placeholders replaced.
   */
  public String getSimpleFormattedMessage(String key, Object[] arguments);

}
