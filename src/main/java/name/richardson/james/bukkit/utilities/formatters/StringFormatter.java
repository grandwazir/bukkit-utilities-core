/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * StringFormatter.java is part of BukkitUtilities.
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
