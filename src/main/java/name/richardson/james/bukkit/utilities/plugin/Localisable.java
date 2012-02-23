package name.richardson.james.bukkit.utilities.plugin;

import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Localisable interface represents an object which is able to localise
 * strings for output to the user.
 */
public interface Localisable {

  /**
   * Gets the current locale.
   * 
   * This should return the locale of the messages returned by the class implementing this interface.
   * 
   * @return the locale
   */
  public Locale getLocale();

  /**
   * Gets a localised message by providing a message key.
   * 
   * This returns a string from the ResourceBundle, providing that it exists. This method does not do any additional formatting beyond returning the localised message.
   *
   * @param key Specifies the key of the message to look up in the ResourceBundle.
   * @return The localised message matching the key supplied.
   */
  public String getMessage(String key);

  /**
   * Gets the simple formatted message.
   *
   * This method returns a localised message replacing place holders in the string with the arguments provided.
   *
   * @param key Specifies the key of the message to look up in the ResourceBundle.
   * @param arguments The arguments to use to format the message and replace the place holders.
   * @return The localised message matching the key supplied and with the placeholders replaced.
   */
  public String getSimpleFormattedMessage(String key, Object[] arguments);

  /**
   * Gets the simple formatted message.
   *
   * This method returns a localised message replacing place holder in the string with the argument provided. It is a convenience method to avoid creating an array of a single String. 
   *
   * @param key Specifies the key of the message to look up in the ResourceBundle.
   * @param argument The argument to use to format the message and replace the place holder.
   * @return The localised message matching the key supplied and with the placeholder replaced.
   */
  public String getSimpleFormattedMessage(String key, String argument);

  /**
   * Gets the choice formatted message.
   *
   * This method returns a localised message replacing place holder in the string with the arguments provided. Additionally it alters the formatting of the message depending on the formats and limits provided. This can return messages such as "1 ban" or "2 bans".
   *
   * @param key Specifies the key of the message to look up in the ResourceBundle.
   * @param arguments The arguments to use to format the messages and replace the place holder.
   * @param formats The formats to use when matched to a limit. For example {"no bans", "one ban"}.
   * @param limits The limits as Doubles. For example {0,1}.
   * @return The localised message matching the key supplied and with the placeholder replaced.
   */
  public String getChoiceFormattedMessage(String key, Object[] arguments, String[] formats, Double[] limits);
  
}
