/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ColourFormatter.java is part of BukkitUtilities.
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

import org.bukkit.ChatColor;

public class ColourFormatter {

  public static String replace(String message) {
    for (final ChatColor colour : ChatColor.values()) {
      final String pattern = "(?i)&" + colour.name();
      message = message.replaceAll(pattern, colour.toString());
    }
    return message;
  }
  
  public static String replace(final String prefix, String message) {
    for (final ChatColor colour : ChatColor.values()) {
      final String pattern = "(?i)" + prefix + colour.name();
      message = message.replaceAll(pattern, colour.toString());
    }
    return message;
  }

}
