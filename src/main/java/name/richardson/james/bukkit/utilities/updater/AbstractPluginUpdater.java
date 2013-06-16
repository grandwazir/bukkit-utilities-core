/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractPluginUpdater.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.updater;

import org.bukkit.plugin.Plugin;

public abstract class AbstractPluginUpdater implements PluginUpdater {

	public enum Branch {
		DEVELOPMENT, STABLE
	}

	public enum State {
		UPDATE, NOTIFY, OFF
	}

	private final String version;
	private final State state;

	public AbstractPluginUpdater(Plugin plugin, State state) {
		this.version = plugin.getName();
		this.state = state;
	}

	@Override
	public String getLocalVersion() {
		return this.version;
	}


	@Override
	public State getState() {
		return this.state;
	}

}
