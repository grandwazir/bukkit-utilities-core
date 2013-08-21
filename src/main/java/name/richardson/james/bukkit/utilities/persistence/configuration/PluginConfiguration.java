/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PluginConfiguration.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.persistence.configuration;

import java.util.logging.Level;

import name.richardson.james.bukkit.utilities.updater.PluginUpdater;

/**
 * Represents the basic configuration required for a plugin.
 */
public interface PluginConfiguration {

	/**
	 * Return the branch that the updater should use.
	 *
	 * @return the branch the updater should use.
	 */
	public PluginUpdater.Branch getAutomaticUpdaterBranch();

	/**
	 * Return the state that the updater should use.
	 *
	 * @return the state the updater should use.
	 */
	public PluginUpdater.State getAutomaticUpdaterState();

	/**
	 * Return the level that tje plugin logger should be set to.
	 *
	 * @return the logging level
	 */
	public Level getLogLevel();

	/**
	 * Return if this plugin should collect and send anonymous usage statistics
	 *
	 * @return {@code true} if the plugin should collect stats; {@code false} otherwise.
	 */
	public boolean isCollectingStats();

}
