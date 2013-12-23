/*
 * Copyright (c) 2013 James Richardson.
 *
 * SimpleDatabaseMigrator.java is part of BukkitUtilities.
 *
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.persistence.database;

import com.avaje.ebean.EbeanServer;

/**
 * This simple implementation of {@link name.richardson.james.bukkit.utilities.persistence.database.DatabaseMigrator} drops the previous version of a schema and
 * replaces it with an upgraded version. It does not attempt to transfer across any records.
 *
 * @author James Richardson
 * @version 7.0.0
 */
public class SimpleDatabaseMigrator extends AbstractDatabaseMigrator {

	public SimpleDatabaseMigrator(DatabaseLoader oldDatabaseLoader, DatabaseLoader newDatabaseLoader) {
		super(oldDatabaseLoader, newDatabaseLoader);
	}

	/**
	 * This method is called after the new database has been loaded.
	 * <p/>
	 * You should implement this method to insert any records you want from the old database.
	 *
	 * @param database the old database
	 * @since 7.0.0
	 */
	@Override
	public void afterUpgrade(EbeanServer database) {
		return;
	}

	/**
	 * This method is called after the old database has been loaded but before the new one has been created.
	 * <p/>
	 * You should implement this method to get any records out of the previous database before it is dropped. It is essential that you store all the records you
	 * want in an Collection as once past this stage you can not make any queries on the old database.
	 *
	 * @param database the old database
	 * @since 7.0.0
	 */
	@Override
	public void beforeUpgrade(EbeanServer database) {
		return;
	}
}
