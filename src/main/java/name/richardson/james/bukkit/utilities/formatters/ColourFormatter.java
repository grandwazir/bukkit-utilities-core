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

	public static final ChatColor WARNING = ChatColor.YELLOW;
	public static final ChatColor WARNING_HIGHLIGHT = ChatColor.GREEN;
	public static final ChatColor INFO = ChatColor.GREEN;
	public static final ChatColor INFO_HIGHLIGHT = ChatColor.AQUA;
	public static final ChatColor ERROR = ChatColor.RED;
	public static final ChatColor ERROR_HIGHLIGHT = ChatColor.YELLOW;
	public static final ChatColor HEADER = ChatColor.LIGHT_PURPLE;
	public static final ChatColor HEADER_HIGHLIGHT = ChatColor.AQUA;


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

	public static String warning(final String message) {
		return WARNING + message.replaceAll("\\{", WARNING_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + WARNING);
	}

	public static String info(final String message) {
		return INFO + message.replaceAll("\\{", INFO_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + INFO);
	}

	public static String error(final String message) {
		return ERROR + message.replaceAll("\\{", ERROR_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + ERROR);
	}

	public static String header(final String message) {
		return HEADER + message.replaceAll("\\{", HEADER_HIGHLIGHT + "\\{").replaceAll("\\}", "\\}" + HEADER);
	}


}

