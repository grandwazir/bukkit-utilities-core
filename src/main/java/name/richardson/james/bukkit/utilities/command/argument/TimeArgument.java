/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TimeArgument.java is part of bukkit-utilities.

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
import java.util.Set;

import name.richardson.james.bukkit.utilities.formatters.TimeFormatter;

public class TimeArgument implements Argument {

	private final StringArgument stringArgument;
	private long time;
	private boolean required;

	public TimeArgument() {
		this.stringArgument = new StringArgument();
		this.stringArgument.setCaseInsensitive(true);
		this.setRequired(true);
	}

	protected StringArgument getStringArgument() {
		return stringArgument;
	}

	@Override
	public Long getValue()
	throws InvalidArgumentException {
		return time;
	}

	@Override
	public void parseValue(Object argument)
	throws InvalidArgumentException {
		this.getStringArgument().parseValue(argument);
		String timeString = this.getStringArgument().getValue();
		if (timeString == null || timeString.isEmpty()) {
			this.time = 0;
		} else {
			this.time = TimeFormatter.parseTime(timeString);
		}
	}

	@Override
	public boolean isRequired() {
		return required;

	}

	@Override
	public void setRequired(boolean value) {
		this.required = value;
		this.getStringArgument().setRequired(value);
	}

	@Override
	public Set<String> getMatches(String argument) {
		return Collections.emptySet();
	}
}
