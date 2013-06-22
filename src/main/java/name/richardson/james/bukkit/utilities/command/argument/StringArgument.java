/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 StringArgument.java is part of bukkit-utilities.

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

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

public class StringArgument implements Argument {

	private String string;
	private boolean caseInsensitive;
	private boolean required;

	@Override
	public String getValue()
	throws InvalidArgumentException {
		if (this.string == null && this.isRequired()) throw new InvalidArgumentException(null, null);
		return string;
	}

	@Override
	public void parseValue(Object argument)
	throws InvalidArgumentException {
		if (argument == null) {
			if (this.isRequired()) throw new InvalidArgumentException(null, null);
			this.string = null;
		} else {
			this.string = (String) argument;
			if (caseInsensitive) string = string.toLowerCase(Locale.ENGLISH);
		}
	}

	public void setCaseInsensitive(boolean value) {
		this.caseInsensitive = value;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean value) {
		this.required = value;
	}

	@Override
	public Set<String> getMatches(String argument) {
		return Collections.emptySet();
	}
}
