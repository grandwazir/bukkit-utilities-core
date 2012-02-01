package name.richardson.james.bukkit.util;

import org.bukkit.ChatColor;

public class Colour {

  public static String replace(String prefix, String message) {
    for (ChatColor colour : ChatColor.values() ) {
      final String pattern = "(?i)" + prefix + colour.name();
      message = message.replaceAll(pattern, colour.toString());
    }
    return message;
  }
  
}
