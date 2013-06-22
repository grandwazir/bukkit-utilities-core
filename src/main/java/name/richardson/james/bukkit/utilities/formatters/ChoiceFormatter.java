/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ChoiceFormatter.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.formatters;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;

public final class ChoiceFormatter {

	private Object[] arguments;

	private String[] formats;

	private double[] limits;

	private String message = "{0}";

	private final ResourceBundle localisation;

	public ChoiceFormatter(Class<?> owner) {
		this.localisation = PluginResourceBundle.getBundle(owner);
	}

	public String getMessage() {
		final MessageFormat formatter = new MessageFormat(this.message);
		final ChoiceFormat cFormatter = new ChoiceFormat(this.limits, this.formats);
		formatter.setFormatByArgumentIndex(0, cFormatter);
		return ColourFormatter.replace(formatter.format(this.arguments));
	}

	public void setArguments(final Object... arguments) {
		this.arguments = arguments;
	}

	public void setFormats(final String... args) {
        List<String> formats = new LinkedList<String>();
        for (String key : args) {
            formats.add(this.localisation.getString(key));
        }
		this.formats = formats.toArray(new String[formats.size()]);
	}

	public void setLimits(final double... limits) {
		this.limits = limits;
	}

	public void setMessage(final String key) {
		this.message = this.localisation.getString(key);
	}

    public void setLocalisedMessage(final String message) {
        this.message = message;
    }

}
