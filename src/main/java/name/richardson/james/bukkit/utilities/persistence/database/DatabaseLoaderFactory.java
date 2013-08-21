/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 DatabaseLoaderFactory.java is part of BukkitUtilities.

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

/**
 * Create and return a suitable database loader depending on the database configuration provided. This is used to abstract away the implementation requirement
 * to have a different database loader for SQLite due to a bug in the schema generation when using the Ebean versions shipped with Bukkit.
 */
public final class DatabaseLoaderFactory {

	/**
	 * Returns a database loader configured with the provided database configuration.
	 *
	 * @param databaseConfiguration the configuration to use for the database loader
	 * @return the database loader
	 */
	public final static DatabaseLoader getDatabaseLoader(DatabaseConfiguration databaseConfiguration) {
		if (databaseConfiguration.getDataSourceConfig().getDriver().contains("sqlite")) {
			return new SQLiteDatabaseLoader(databaseConfiguration);
		} else {
			return new DefaultDatabaseLoader(databaseConfiguration);
		}
	}

}
