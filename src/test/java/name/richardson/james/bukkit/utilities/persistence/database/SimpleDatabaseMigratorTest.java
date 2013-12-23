/*
 * Copyright (c) 2013 James Richardson.
 *
 * SimpleDatabaseMigratorTest.java is part of BukkitUtilities.
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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.persistence.database.AbstractDatabaseMigratorTest;
import name.richardson.james.bukkit.utilities.persistence.database.SimpleDatabaseMigrator;
import name.richardson.james.bukkit.utilities.persistence.database.support.NewEntity;
import name.richardson.james.bukkit.utilities.persistence.database.support.OldEntity;
import name.richardson.james.bukkit.utilities.persistence.database.support.TestDatabaseLoader;

@RunWith(JUnit4.class)
public class SimpleDatabaseMigratorTest extends AbstractDatabaseMigratorTest {

	@Before
	public void setup() {
		List<Class<?>> classList = new LinkedList<Class<?>>();
		classList.add(OldEntity.class);
		TestDatabaseLoader oldLoader = new TestDatabaseLoader(classList);
		classList.clear();
		classList.add(NewEntity.class);
		TestDatabaseLoader newLoader = new TestDatabaseLoader(classList);
		setDatabaseMigrator(new SimpleDatabaseMigrator(oldLoader.getDatabaseLoader(), newLoader.getDatabaseLoader()));
	}

}
