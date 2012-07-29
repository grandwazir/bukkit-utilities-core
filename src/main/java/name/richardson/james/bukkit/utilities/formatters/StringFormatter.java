package name.richardson.james.bukkit.utilities.formatters;

import java.util.List;

public class StringFormatter {

  public static String combineString(final List<String> arguments, final String seperator) {
    return StringFormatter.combineString(arguments.toArray(new String[arguments.size()]), seperator);
  }

  public static String combineString(final String[] arguments, final String seperator) {
    if (arguments.length == 0) {
      return null;
    }
    final StringBuilder message = new StringBuilder();
    for (final String argument : arguments) {
      message.append(argument);
      message.append(seperator);
    }
    // remove trailing seperator.
    message.deleteCharAt(message.length() - seperator.length());
    return message.toString();
  }

}
