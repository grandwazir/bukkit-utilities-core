/*
 * Copyright (c) 2014 James Richardson.
 *
 * TimeMarshaller.java is part of BukkitUtilities.
 *
 * bukkit-utilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * bukkit-utilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * bukkit-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.command.argument;

import java.sql.Timestamp;

import name.richardson.james.bukkit.utilities.formatters.time.ApproximateTimeFormatter;
import name.richardson.james.bukkit.utilities.formatters.time.PreciseDurationTimeFormatter;
import name.richardson.james.bukkit.utilities.formatters.time.PreciseTimeFormatter;
import name.richardson.james.bukkit.utilities.formatters.time.TimeFormatter;

public class TimeMarshaller extends AbstractMarshaller {

	private TimeFormatter formatter = new PreciseDurationTimeFormatter();

	public TimeMarshaller(final Argument argument) {
		super(argument);
	}

	public long getDuration() {
		return formatter.getDurationInMilliseconds(getString());
	}

}
