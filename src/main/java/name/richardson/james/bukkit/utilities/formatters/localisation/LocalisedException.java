/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 LocalisedException.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.formatters.localisation;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public abstract class LocalisedException extends Exception implements Localised {

	private static final String RESOURCE_BUNDLE_NAME = ResourceBundles.MESSAGES.getBundleName();
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);

	private final String message;

	public LocalisedException(String key, Object... arguments) {
		this.message = getMessage(key, arguments);
	}

	public ResourceBundle getResourceBundle() {
		return LocalisedException.RESOURCE_BUNDLE;
	}

	public String getMessage(String key, Object... arguments) {
		return MessageFormat.format(LocalisedException.RESOURCE_BUNDLE.getString(key), arguments);
	}

	@Override
	public String getMessage() {
		return getLocalizedMessage();
	}

	@Override
	public String getLocalizedMessage() {
		return message;
	}


}
