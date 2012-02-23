package name.richardson.james.bukkit.utilities.formatters;

import org.bukkit.ChatColor;

public class ColourFormatter {

  public static String replace(final String prefix, String message) {
    for (final ChatColor colour : ChatColor.values()) {
      final String pattern = "(?i)" + prefix + colour.name();
      message = message.replaceAll(pattern, colour.toString());
    }
    return message;
  }

}
