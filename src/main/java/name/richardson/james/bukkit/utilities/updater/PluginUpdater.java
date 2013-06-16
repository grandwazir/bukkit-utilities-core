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
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 16/06/13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public interface PluginUpdater extends Runnable {

	String getLocalVersion();

	String getRemoteVersion();

	AbstractPluginUpdater.State getState();

	boolean isNewVersionAvailable();

	void run();

}
