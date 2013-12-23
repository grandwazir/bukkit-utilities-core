/*
 * Copyright (c) 2013 James Richardson.
 *
 * AbstractColouredLocalisation.java is part of BukkitUtilities.
 *
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.localisation;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.formatters.DefaultColourFormatter;

public class ResourceBundleLocalisation implements ColouredLocalisation, PluginLocalisation {

	private final ColourFormatter colourFormatter;
	private final ResourceBundle resourceBundle;

	public ResourceBundleLocalisation() {
		this(Localisation.RESOURCE_BUNDLE_NAME, new DefaultColourFormatter());
	}

	public ResourceBundleLocalisation(String bundleName) {
		this(bundleName, new DefaultColourFormatter());
	}

	public ResourceBundleLocalisation(String bundleName, ColourFormatter colourFormatter) {
		this.resourceBundle = ResourceBundle.getBundle(bundleName);
		this.colourFormatter = colourFormatter;
	}

	/**
	 * Returns a String formatted using a FormatStyle.
	 *
	 * @param message     the message to be formatted
	 * @param formatStyle the formatting style to apply
	 * @return the formatted message
	 */
	@Override
	public final String getMessage(String message, FormatStyle formatStyle) {
		return colourFormatter.getMessage(getMessage(message), formatStyle);
	}

	/**
	 * Returns a String formatted with arguments using a FormatStyle.
	 *
	 * @param message     the message to be formatted in MessageFormat style.
	 * @param formatStyle the formatting style to apply
	 * @param arguments   the arguments to insert into the message
	 * @return the formatted message
	 */
	@Override
	public final String getMessage(String message, FormatStyle formatStyle, Object... arguments) {
		return colourFormatter.getMessage(getMessage(message), formatStyle, arguments);
	}

	/**
	 * Return the localised message matching the key.
	 *
	 * @param key the key to use when looking up the message
	 * @return the localised message
	 */
	@Override
	public final String getMessage(String key) {
		return getResourceBundle().getString(key);
	}

	/**
	 * Return the localised message with the arguments provided.
	 *
	 * @param key       the key to use when looking up the message
	 * @param arguments the arguments to insert into the string
	 * @return the localised message
	 */
	@Override
	public final String getMessage(String key, Object... arguments) {
		return MessageFormat.format(getMessage(key), arguments);
	}

	public final ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

}
