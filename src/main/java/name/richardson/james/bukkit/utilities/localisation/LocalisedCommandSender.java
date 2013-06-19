/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 LocalisedCommandSender.java is part of BukkitUtilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.localisation;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;

public class LocalisedCommandSender {

	private final CommandSender sender;
	private final ResourceBundle bundle;

	public LocalisedCommandSender(final CommandSender sender, final ResourceBundle bundle) {
		this.bundle = bundle;
		this.sender = sender;
	}

	public void error(String key, Object... arguments) {
		String message = bundle.getString(key);
		message = ColourFormatter.error(message);
		message = MessageFormat.format(message, arguments);
		sender.sendMessage(message);
	}

	public void info(String key, Object... arguments) {
		String message = bundle.getString(key);
		message = ColourFormatter.info(message);
		message = MessageFormat.format(message, arguments);
		sender.sendMessage(message);
	}

	public void header(String key, Object... arguments) {
		String message = bundle.getString(key);
		message = ColourFormatter.header(message);
		message = MessageFormat.format(message, arguments);
		System.err.append(arguments.toString());
		System.err.append(message);
		sender.sendMessage(message);
	}

	public void warning(String key, Object... arguments) {
		String message = bundle.getString(key);
		message = ColourFormatter.warning(message);
		message = MessageFormat.format(message, arguments);
		sender.sendMessage(message);
	}

	public void send(String key, Object... arguments) {
		String message = MessageFormat.format(bundle.getString(key), arguments);
		message = ColourFormatter.replace(message);
		sender.sendMessage(message);
	}

}
