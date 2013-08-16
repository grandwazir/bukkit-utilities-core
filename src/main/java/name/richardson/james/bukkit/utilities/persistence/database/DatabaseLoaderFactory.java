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

import java.util.List;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;

public final class DatabaseLoaderFactory {

	public static DatabaseLoader getDatabaseLoader(List<Class<?>> databaseClassList, DatabaseConfiguration databaseConfiguration) {
		ClassLoader classLoader = databaseConfiguration.getClass().getClassLoader();
		if (databaseConfiguration.getDataSourceConfig().getDriver().contains("sqlite")) {
			return new SQLiteDatabaseLoader(classLoader, databaseClassList, databaseConfiguration);
		} else {
			return new DefaultDatabaseLoader(classLoader, databaseClassList, databaseConfiguration);
		}
	}

}
