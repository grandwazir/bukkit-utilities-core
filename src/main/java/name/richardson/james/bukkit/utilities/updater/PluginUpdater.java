/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PluginUpdater.java is part of BukkitUtilities.

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

/**
 * The PluginUpdater interface defines how updaters should interact with other parts of BukkitUtilities.
 */
public interface PluginUpdater extends Runnable {

	public static enum Branch {
		DEVELOPMENT,
		STABLE
	}

	public static enum State {
		NOTIFY,
		OFF,
		UPDATE
	}

	/**
	 * Gets the local version of the plugin.
	 *
	 * Should be in a Maven style format.
	 *
	 * @return The current version of the local plugin
	 */
	String getLocalVersion();

	/**
	 * Get the current remote version of the plugin.
	 *
	 * This should be the latest released and available version matching the branch requested.
	 *
	 * @return The current remote version of the plugin.
	 */
	String getRemoteVersion();

	/**
	 * Get the current state of the updater.
	 *
	 * @return The current remote version of the plugin.
	 */
	PluginUpdater.State getState();

	/**
	 * Check to see if a new version of the plugin is available.
	 *
	 * @return return true if there is a version available, false otherwise.
	 */
	boolean isNewVersionAvailable();

	void run();

}
