/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 SentenceArgument.java is part of bukkit-utilities.

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

public class SentenceArgument extends StringArgument {

	public SentenceArgument() {
		this.setCaseInsensitive(false);
	}

	public String getValue()
	throws InvalidArgumentException {
		String message = super.getValue();
		StringBuilder builder = new StringBuilder();
		builder.append(message.substring(0, 1).toUpperCase(Locale.getDefault()));
		builder.append(message.substring(1));
		if (!builder.substring(builder.length() - 1).contentEquals(".")) builder.append(".");
		return builder.toString();
	}

	@Override
	public Set<String> getMatches(String argument) {
		return Collections.emptySet();
	}

}
