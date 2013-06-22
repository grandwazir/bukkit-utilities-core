/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PlayerArgument.java is part of bukkit-utilities.

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

import org.bukkit.Server;

public abstract class PlayerArgument implements Argument {

	private static Server server;

	private final StringArgument stringArgument;

	private boolean required;

	public PlayerArgument() {
		this.stringArgument = new StringArgument();
		this.stringArgument.setCaseInsensitive(true);
	}

	protected static Server getServer() {
		return PlayerArgument.server;
	}

	public static void setServer(Server server) {
		PlayerArgument.server = server;
	}

	protected StringArgument getStringArgument() {
		return stringArgument;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean value) {
		this.required = value;
	}

}
