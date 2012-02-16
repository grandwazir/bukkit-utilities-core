package name.richardson.james.bukkit.utilities.plugin;

import java.util.Locale;

/**
 * The Localisable interface represents an object which is able to localise
 * strings for output to the user.
 */
public interface Localisable {

  /**
   * Gets a localised message by providing a message key.
   * 
   * @param key the key of the message to find
   * @return the localised message
   */
  public String getMessage(String message);

  /**
   * Gets the current locale.
   * 
   * @return the locale
   */
  public Locale getLocale();

}
