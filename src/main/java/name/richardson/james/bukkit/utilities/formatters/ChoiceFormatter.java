/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ChoiceFormatter.java is part of BukkitUtilities.
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

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;

public final class ChoiceFormatter {

	private Object[] arguments;

	private String[] formats;

	private double[] limits;

	private String message = "{0}";

	private final ResourceBundle localisation;

	public ChoiceFormatter(final ResourceBundles bundleName) {
		this.localisation = ResourceBundle.getBundle(bundleName.getBundleName());
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

	public void setFormats(final String... formats) {
		this.formats = formats;
	}

	public void setLimits(final double... limits) {
		this.limits = limits;
	}

	public void setMessage(final String key) {
		this.message = this.localisation.getString(key);
	}

}
