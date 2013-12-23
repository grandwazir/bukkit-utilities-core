/*
 * Copyright (c) 2013 James Richardson.
 *
 * AbstractDatabaseMigratorTest.java is part of BukkitUtilities.
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

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public abstract class AbstractDatabaseMigratorTest extends TestCase {

	private DatabaseMigrator databaseMigrator;

	public DatabaseMigrator getDatabaseMigrator() {
		return databaseMigrator;
	}

	public void setDatabaseMigrator(DatabaseMigrator databaseMigrator) {
		this.databaseMigrator = databaseMigrator;
	}

	@Test
	public void migrateSuccessfullyWithoutExceptions() {
		databaseMigrator.initalise();
	}

	@Test
	public void setDatabaseLoadersCorrectly() {
		assertNotNull(databaseMigrator.getNewDatabaseLoader());
		assertNotNull(databaseMigrator.getOldDatabaseLoader());
	}



}
