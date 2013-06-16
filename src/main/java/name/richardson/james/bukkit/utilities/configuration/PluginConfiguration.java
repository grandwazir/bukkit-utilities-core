/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * PluginConfiguration.java is part of BukkitUtilities.
 * 
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.configuration;

import java.util.logging.Level;

import name.richardson.james.bukkit.utilities.updater.PluginUpdater;

public interface PluginConfiguration {

	public PluginUpdater.Branch getAutomaticUpdaterBranch();

	public PluginUpdater.State getAutomaticUpdaterState();

	public Level getLogLevel();

	public boolean isCollectingStats();

	public void setAutomaticUpdaterBranch(final PluginUpdater.Branch branch);

	public void setAutomaticUpdaterState(final PluginUpdater.State state);

	public void setCollectingStats(final boolean value);

	public void setLogLevel(Level level);

}
