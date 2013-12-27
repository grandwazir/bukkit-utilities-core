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

import name.richardson.james.bukkit.utilities.formatters.MessageFormatter;
import name.richardson.james.bukkit.utilities.formatters.DefaultMessageFormatter;

public abstract class AbstractResourceBundleLocalisation implements FormattedLocalisation, PluginLocalisation {

	private final MessageFormatter messageFormatter;
	private final ResourceBundle resourceBundle;

	public AbstractResourceBundleLocalisation() {
		this(Localisation.RESOURCE_BUNDLE_NAME, new DefaultMessageFormatter());
	}

	public AbstractResourceBundleLocalisation(String bundleName) {
		this(bundleName, new DefaultMessageFormatter());
	}

	public AbstractResourceBundleLocalisation(String bundleName, MessageFormatter messageFormatter) {
		this.resourceBundle = ResourceBundle.getBundle(bundleName);
		this.messageFormatter = messageFormatter;
	}

	@Override
	public String formatAsHeaderMessage(String message) {
		return messageFormatter.formatAsHeaderMessage(getMessage(message));
	}

	@Override
	public String formatAsHeaderMessage(String message, Object... arguments) {
		String s = messageFormatter.formatAsHeaderMessage(getMessage(message));
		return MessageFormat.format(s, arguments);
	}

	@Override
	public String formatAsInfoMessage(String message) {
		return messageFormatter.formatAsInfoMessage(getMessage(message));
	}

	@Override
	public String formatAsInfoMessage(String message, Object... arguments) {
		String s = messageFormatter.formatAsInfoMessage(getMessage(message));
		return MessageFormat.format(s, arguments);
	}

	@Override
	public String formatAsWarningMessage(String message, Object... arguments) {
		String s = messageFormatter.formatAsWarningMessage(getMessage(message));
		return MessageFormat.format(s, arguments);
	}

	@Override
	public String formatAsWarningMessage(String message) {
		return messageFormatter.formatAsWarningMessage(getMessage(message));

	}

	@Override
	public String formatAsErrorMessage(String message, Object... arguments) {
		String s = messageFormatter.formatAsErrorMessage(getMessage(message));
		return MessageFormat.format(s, arguments);
	}

	@Override
	public String formatAsErrorMessage(String message) {
		return messageFormatter.formatAsErrorMessage(getMessage(message));
	}

	@Override
	public String getMessage(String key) {
		return getResourceBundle().getString(key);
	}

	@Override
	public String getMessage(String key, Object... arguments) {
		return MessageFormat.format(getMessage(key), arguments);
	}

	protected final ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

}
