/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractDatabaseLoader.java is part of bukkit-utilities.

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

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.LogLevel;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.logging.PrefixedLogger;
import name.richardson.james.bukkit.utilities.persistence.configuration.SimpleDatabaseConfiguration;

public abstract class AbstractDatabaseLoader implements DatabaseLoader {

	private final ClassLoader classLoader;
	private final List<Class<?>> classes;
	private final DataSourceConfig datasourceConfig;
	private final Logger logger = PrefixedLogger.getLogger(this.getClass());
	private final boolean rebuild = false;
	private final ServerConfig serverConfig;

	private EbeanServer ebeanserver;
	private DdlGenerator generator;

	public AbstractDatabaseLoader(ClassLoader classLoader, List<Class<?>> classes, SimpleDatabaseConfiguration configuration) {
		Validate.notEmpty(classes, "Database classes must be provided!");
		Validate.notNull(configuration, "A configuration is required!");
		Validate.notNull(classLoader, "A classloader is required!");
		this.classes = classes;
		this.serverConfig = configuration.getServerConfig();
		this.datasourceConfig = configuration.getDataSourceConfig();
		this.classLoader = classLoader;
	}

	@Override
	public String getDeleteDLLScript() {
		final SpiEbeanServer server = (SpiEbeanServer) getEbeanServer();
		final DdlGenerator generator = server.getDdlGenerator();
		return generator.generateDropDdl();
	}

	@Override
	public final EbeanServer getEbeanServer() {
		return ebeanserver;
	}

	@Override
	public String getGenerateDDLScript() {
		final SpiEbeanServer server = (SpiEbeanServer) getEbeanServer();
		final DdlGenerator generator = server.getDdlGenerator();
		return generator.generateCreateDdl();
	}

	synchronized public final void initalise() {
		if (this.ebeanserver != null) throw new IllegalStateException("Database is already initalised!");
		this.load();
		if (!this.validate() || this.rebuild) {
			final SpiEbeanServer server = (SpiEbeanServer) this.ebeanserver;
			generator = server.getDdlGenerator();
			if (!this.logger.isLoggable(Level.FINEST)) setGeneratorDebug(generator, false);
			this.drop();
			this.create();
			logger.log(Level.INFO, "rebuilt-schema");
		}
	}

	private void create() {
		logger.log(Level.INFO, "creating-database");
		this.beforeDatabaseCreate();
		// reload the database this allows for removing classes
		String script = getGenerateDDLScript();
		this.load();
		generator.runScript(false, script);
		this.afterDatabaseCreate();
	}

	private void drop() {
		logger.log(Level.FINER, "Dropping and destroying database.");
		this.beforeDatabaseDrop();
		generator.runScript(true, this.getDeleteDLLScript());
	}

	private void load() {
		logger.log(Level.FINE, "Loading database.");
		final Level level = java.util.logging.Logger.getLogger("").getLevel();
		ClassLoader currentClassLoader = null;
		try {
			this.serverConfig.setClasses(this.classes);
			if (logger.isLoggable(Level.ALL)) {
				this.serverConfig.setLoggingToJavaLogger(true);
				this.serverConfig.setLoggingLevel(LogLevel.SQL);
			}
			// suppress normal ebean warnings and notifications
			java.util.logging.Logger.getLogger("").setLevel(Level.OFF);
			currentClassLoader = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(this.classLoader);
			this.ebeanserver = EbeanServerFactory.create(this.serverConfig);
			System.out.print(ebeanserver.getName());
		} finally {
			// re-enable logging
			java.util.logging.Logger.getLogger("").setLevel(level);
			if (currentClassLoader != null) {
				Thread.currentThread().setContextClassLoader(currentClassLoader);
			}
		}
	}

	/**
	 * Ebean by default outputs a ton of exception data to the console which is ignored by the generator itself. This makes it difficult to actually find real
	 * exceptions. This method disables this using reflection.
	 *
	 * @param generator
	 * @param value
	 */
	private void setGeneratorDebug(DdlGenerator generator, boolean value) {
		try {
			Field field = generator.getClass().getDeclaredField("debug");
			field.setAccessible(true);
			field.set(generator, value);
		} catch (Exception e) {
			logger.warning("Unable to supress generator messages!");
		}
	}

	private boolean validate() {
		for (final Class<?> ebean : this.classes) {
			try {
				this.ebeanserver.find(ebean).findRowCount();
			} catch (final Exception exception) {
				logger.log(Level.WARNING, "schema-invalid");
				return false;
			}
		}
		logger.log(Level.FINER, "Database schema is valid.");
		return true;
	}

}
