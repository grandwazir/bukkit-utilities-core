/*
 * Copyright (c) 2013 James Richardson.
 *
 * AbstractDatabaseMigrator.java is part of BukkitUtilities.
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

import java.util.logging.Level;
import java.util.logging.Logger;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.PluginLocalisation;
import name.richardson.james.bukkit.utilities.localisation.StrictResourceBundleLocalisation;
import name.richardson.james.bukkit.utilities.logging.PluginLoggerFactory;

/**
 * This class provides a reference implementation that takes two seperate database loaders and migrates the data from one to the other.
 * <p/>
 * It does this by attempting to isSchemaValid the database against the current schema. If isSchemaValid fails it then attempts to load the old database schema, allowing for
 * getting the data out by implementing {@link #beforeUpgrade(com.avaje.ebean.EbeanServer)} and then rebuild the database with the new schema. Once the schema has
 * been regenerated you can reinsert the data by implementing {@link #afterUpgrade(com.avaje.ebean.EbeanServer)}.
 * <p/>
 * As the class implements DatabaseLoader is is possible to use this as a drop in replacement for other DatabaseLoaders when upgrading.
 *
 * @author James Richardson
 * @version 7.0.0
 */
public abstract class AbstractDatabaseMigrator implements DatabaseMigrator {

	private final DatabaseLoader newDatabaseLoader;
	private final DatabaseLoader oldDatabaseLoader;
	private final Logger logger = PluginLoggerFactory.getLogger(AbstractDatabaseMigrator.class);
	private final Localisation localisation = new StrictResourceBundleLocalisation();

	public AbstractDatabaseMigrator(DatabaseLoader oldDatabaseLoader, DatabaseLoader newDatabaseLoader) {
		this.oldDatabaseLoader = oldDatabaseLoader;
		this.newDatabaseLoader = newDatabaseLoader;
	}

	/**
	 * Gets the current {@link name.richardson.james.bukkit.utilities.persistence.database.DatabaseLoader}.
	 *
	 * @return The current database loader
	 */
	@Override
	public final DatabaseLoader getNewDatabaseLoader() {
		return newDatabaseLoader;
	}

	/**
	 * Gets the old {@link name.richardson.james.bukkit.utilities.persistence.database.DatabaseLoader}.
	 *
	 * This will most likely be invalid once the new loader has been initalised.
	 *
	 * @return The old database loader.
	 */
	@Override
	public final DatabaseLoader getOldDatabaseLoader() {
		return oldDatabaseLoader;
	}

	@Override
	public void initalise() {
		this.newDatabaseLoader.load();
		if (this.newDatabaseLoader.isSchemaValid() == false) {
			logger.log(Level.WARNING, PluginLocalisation.DATABASE_UPGRADE_REQUIRED);
			this.oldDatabaseLoader.initalise();
			this.beforeUpgrade(this.oldDatabaseLoader.getEbeanServer());
			this.oldDatabaseLoader.drop();
			this.newDatabaseLoader.initalise();
			this.afterUpgrade(this.newDatabaseLoader.getEbeanServer());
		}
	}

}
