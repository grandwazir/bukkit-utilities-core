/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 DatabaseLoader.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.persistence.database;

import com.avaje.ebean.EbeanServer;

/**
 * Loads and initalises an Ebean server. Once the database is initalised the loader holds an instance of the database for plugins to use.
 */
public interface DatabaseLoader {

	/**
	 * Initalise the database.
	 */
	public void initalise();

	/**
	 * Returns the Ebean server created by this database loader.
	 *
	 * @return the EBean server.
	 */
	public EbeanServer getEbeanServer();

}
