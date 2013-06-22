/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 BooleanArgument.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.argument;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;

public class BooleanArgument implements Argument {

	private static final ResourceBundle RESOURCE_BUNDLE = PluginResourceBundle.getBundle(BooleanArgument.class);
	private static final String TRUE = RESOURCE_BUNDLE.getString("true");
	private static final String FALSE = RESOURCE_BUNDLE.getString("false");
	private static final String[] VALUES = {TRUE, FALSE};

	private boolean value;
	private boolean required;

	@Override
	public Boolean getValue()
	throws InvalidArgumentException {
		return value;
	}

	@Override
	public void parseValue(Object argument)
	throws InvalidArgumentException {
		String value = (String) argument;
		if (value.contentEquals(TRUE)) this.value = true;
		if (value.contentEquals(FALSE)) this.value = false;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void setRequired(boolean value) {
		required = value;
	}

	@Override
	public Set<String> getMatches(String argument) {
		argument = argument.toLowerCase(Locale.ENGLISH);
		TreeSet<String> results = new TreeSet<String>();
		for (String value : VALUES) {
			if (!value.startsWith(argument)) continue;
			results.add(value);
		}
		return results;
	}

}
