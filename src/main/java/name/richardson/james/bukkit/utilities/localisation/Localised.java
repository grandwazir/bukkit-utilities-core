package name.richardson.james.bukkit.utilities.localisation;

import java.util.Locale;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;

/**
 * A base abstract class to be used by objects which intend to localise messages
 * through a plugin's resource bundles.
 */
public class Localised implements Localisable {

  /** The base localisation class */
  private final Localisable plugin;

  /** The key prefix to apply to all lookups through this class */
  private final String prefix;

  /**
   * Instantiates a new localised class
   * 
   * @param plugin the base localisation object, usually a plugin.
   */
  public Localised(final Localisable plugin) {
    this.prefix = this.getClass().getSimpleName().toLowerCase() + ".";
    this.plugin = plugin;
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.plugin.Localisable#
   * getChoiceFormattedMessage(java.lang.String, java.lang.Object[],
   * java.lang.String[], double[])
   */
  public String getChoiceFormattedMessage(String key, final Object[] arguments, final String[] formats, final double[] limits) {
    key = this.prefix + key;
    return ColourFormatter.replace("&", this.plugin.getChoiceFormattedMessage(key, arguments, formats, limits));
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.plugin.Localisable#getLocale()
   */
  public Locale getLocale() {
    return this.plugin.getLocale();
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.plugin.Localisable#getMessage(java
   * .lang.String)
   */
  public String getMessage(String key) {
    key = this.prefix + key;
    return ColourFormatter.replace("&", this.plugin.getMessage(key));
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.plugin.Localisable#
   * getSimpleFormattedMessage(java.lang.String, java.lang.Object)
   */
  public String getSimpleFormattedMessage(String key, final Object argument) {
    key = this.prefix + key;
    return ColourFormatter.replace("&", this.plugin.getSimpleFormattedMessage(key, argument));
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.plugin.Localisable#
   * getSimpleFormattedMessage(java.lang.String, java.lang.Object[])
   */
  public String getSimpleFormattedMessage(String key, final Object[] arguments) {
    key = this.prefix + key;
    return ColourFormatter.replace("&", this.plugin.getSimpleFormattedMessage(key, arguments));
  }

}
