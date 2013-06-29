/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 LocalisedChoiceFormatter.java is part of bukkit-utilities.

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

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import name.richardson.james.bukkit.utilities.formatters.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.formatters.colours.CoreColourScheme;

public class LocalisedChoiceFormatter implements Localised {

	private static final String RESOURCE_BUNDLE_NAME = ResourceBundles.MESSAGES.getBundleName();
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);

	private final ColourScheme colourScheme = new CoreColourScheme();

	private Object[] arguments;
	private String[] formats;
	private double[] limits;
	private String message = "{0}";

	public String getColouredMessage(ColourScheme.Style style) {
		final MessageFormat formatter = new MessageFormat(colourScheme.format(style, this.message));
		final ChoiceFormat cFormatter = new ChoiceFormat(this.limits, this.formats);
		formatter.setFormatByArgumentIndex(0, cFormatter);
		return formatter.format(arguments);
	}

	public String getMessage() {
		final MessageFormat formatter = new MessageFormat(this.message);
		final ChoiceFormat cFormatter = new ChoiceFormat(this.limits, this.formats);
		formatter.setFormatByArgumentIndex(0, cFormatter);
		return formatter.format(arguments);
	}

	public void setMessage(final String key) {
		this.message = getMessage(key);
	}

	@Override
	public String getMessage(String key, Object... arguments) {
		return MessageFormat.format(RESOURCE_BUNDLE.getString(key), arguments);
	}

	@Override
	public ResourceBundle getResourceBundle() {
		return RESOURCE_BUNDLE;
	}

	public void setArguments(final Object... arguments) {
		this.arguments = arguments;
	}

	public void setFormats(final String... args) {
		List<String> formats = new LinkedList<String>();
		for (String key : args) {
			formats.add(getMessage(key));
		}
		this.formats = formats.toArray(new String[formats.size()]);
	}

	public void setLimits(final double... limits) {
		this.limits = limits;
	}

}
