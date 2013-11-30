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
	 * Get the name of the current updater branch.
	 *
	 * @return The current branch that the updater is using.
	 */
	Branch getBranch();

	/**
	 * Gets the local version of the plugin.
	 *
	 * Should be in a Maven style format.
	 *
	 * @return The current version of the local plugin
	 */
	public String getLocalVersion();

	/**
	 * Get the name of the plugin being updated
	 *
	 * @return The name of the plugin being updated.
	 */
	String getName();

	/**
	 * Get the current remote version of the plugin.
	 *
	 * This should be the latest released and available version matching the branch requested.
	 *
	 * @return The current remote version of the plugin.
	 */
	public String getRemoteVersion();

	/**
	 * Get the current state of the updater.
	 *
	 * @return The current state of the updater.
	 */
	public PluginUpdater.State getState();

	/**
	 * Check to see if a new version of the plugin is available.
	 *
	 * @return return true if there is a version available, false otherwise.
	 */
	public boolean isNewVersionAvailable();

	/**
	 * Attempt to update the plugin.
	 *
	 * Will do nothing unless a new version is available and the updater is running in update mode.
	 */
	public void update();

	public void run();

}