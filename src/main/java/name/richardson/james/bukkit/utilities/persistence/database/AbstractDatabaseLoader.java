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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.LogLevel;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.PluginLocalisation;
import name.richardson.james.bukkit.utilities.localisation.StrictResourceBundleLocalisation;
import name.richardson.james.bukkit.utilities.logging.PluginLoggerFactory;
import name.richardson.james.bukkit.utilities.persistence.configuration.DatabaseConfiguration;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public abstract class AbstractDatabaseLoader implements DatabaseLoader {

	private final ClassLoader classLoader;
	private final List<Class<?>> classes;
	private final DataSourceConfig datasourceConfig;
	private final Localisation localisation = new StrictResourceBundleLocalisation();
	private final Logger logger = PluginLoggerFactory.getLogger(AbstractDatabaseLoader.class);
	private final boolean rebuild = false;
	private final ServerConfig serverConfig;
	private final Logger globalLogger = Logger.getLogger("");
	private final Level globalLoggerInitialLevel = globalLogger.getLevel();
	private final PrintStream out = System.out;
	private EbeanServer ebeanserver;
	private PrintStream err = System.err;
	private DdlGenerator generator;

	public AbstractDatabaseLoader(DatabaseConfiguration configuration) {
		Validate.notEmpty(configuration.getServerConfig().getClasses(), "Database classes must be provided!");
		Validate.notNull(configuration, "A configuration is required!");
		this.classes = configuration.getServerConfig().getClasses();
		this.serverConfig = configuration.getServerConfig();
		this.datasourceConfig = configuration.getDataSourceConfig();
		this.classLoader = configuration.getClass().getClassLoader();
	}

	@Override
	public final EbeanServer getEbeanServer() {
		return ebeanserver;
	}

	synchronized public final void initalise() {
		this.load();
		if (!this.isSchemaValid() || this.rebuild) {
			final SpiEbeanServer server = (SpiEbeanServer) this.ebeanserver;
			generator = server.getDdlGenerator();
			if (!this.logger.isLoggable(Level.FINEST)) setGeneratorDebug(generator, false);
			this.drop();
			this.create();
			if (!this.isSchemaValid()) {
				throw new RuntimeException("Unable to initalise database!");
			}
		}
	}

	protected abstract void afterDatabaseCreate();

	protected abstract void beforeDatabaseCreate();

	protected abstract void beforeDatabaseDrop();

	protected String getDeleteDLLScript() {
		final SpiEbeanServer server = (SpiEbeanServer) getEbeanServer();
		final DdlGenerator generator = server.getDdlGenerator();
		setGeneratorDebug(generator, false);
		return generator.generateDropDdl();
	}

	protected String getGenerateDDLScript() {
		final SpiEbeanServer server = (SpiEbeanServer) getEbeanServer();
		final DdlGenerator generator = server.getDdlGenerator();
		setGeneratorDebug(generator, false);
		return generator.generateCreateDdl();
	}

	@Override
	public final void create() {
		logger.log(Level.INFO, localisation.getMessage(PluginLocalisation.DATABASE_CREATING));
		this.beforeDatabaseCreate();
		// reload the database this allows for removing classes
		String script = getGenerateDDLScript();
		this.load();
		try {
			this.setSuppressMessages(true);
			generator.runScript(false, script);
		} finally {
			this.setSuppressMessages(false);
		}
		this.afterDatabaseCreate();
	}

	@Override
	public final void drop() {
		logger.log(Level.FINER, localisation.getMessage(PluginLocalisation.DATABASE_DROPPING_TABLES));
		this.beforeDatabaseDrop();
		String script = this.getDeleteDLLScript();
		try {
			this.setSuppressMessages(true);
			generator.runScript(true, script);
		} finally {
			this.setSuppressMessages(false);
		}
	}

	@Override
	public final void load() {
		logger.log(Level.FINE, localisation.getMessage(PluginLocalisation.DATABASE_LOADING));
		if (logger.isLoggable(Level.ALL)) {
			this.serverConfig.setLoggingToJavaLogger(true);
			this.serverConfig.setLoggingLevel(LogLevel.SQL);
		}
		ClassLoader currentClassLoader = null;
		try {
			this.serverConfig.setClasses(this.classes);
			currentClassLoader = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(this.classLoader);
			this.setSuppressMessages(true);
			this.ebeanserver = EbeanServerFactory.create(this.serverConfig);
		} finally {
			this.setSuppressMessages(false);
			if (currentClassLoader != null) {
				Thread.currentThread().setContextClassLoader(currentClassLoader);
			}
		}
	}

	/**
	 * This is not an elegant function. It is used to suppress the warning output printed to system out and err by Ebean during the DDL generation.
	 * Why someone would choice to use this two, especially when there is a logger available in that class is beyond me.
	 *
	 * @param b
	 */
	private void setSuppressMessages(final boolean b) {
		if (b) {
			globalLogger.setLevel(Level.OFF);
			System.setOut(new PrintStream(new OutputStream() {
				@Override public void write(int b) throws IOException {}
			}));
			System.setErr(new PrintStream(new OutputStream() {
				@Override public void write(int b) throws IOException {
				}
			}));
		} else {
			globalLogger.setLevel(globalLoggerInitialLevel);
			System.setOut(out);
			System.setErr(err);
		}
	}

	@Override
	public final boolean isSchemaValid() {
		boolean valid = true;
		for (final Class<?> ebean : this.classes) {
			try {
				this.setSuppressMessages(true);
				this.ebeanserver.find(ebean).findRowCount();
			} catch (final Exception e) {
				valid = false;
				break;
			} finally {
				this.setSuppressMessages(false);
			}
		}
		if (valid) {
			logger.log(Level.FINER, localisation.getMessage(PluginLocalisation.DATABASE_VALID_SCHEMA));
		} else {
			logger.log(Level.WARNING, localisation.getMessage(PluginLocalisation.DATABASE_INVALID_SCHEMA));
		}
		return valid;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("AbstractDatabaseLoader{");
		sb.append("classes=").append(classes);
		sb.append(", classLoader=").append(classLoader);
		sb.append(", datasourceConfig=").append(datasourceConfig);
		sb.append(", ebeanserver=").append(ebeanserver);
		sb.append(", generator=").append(generator);
		sb.append(", globalLoggerInitialLevel=").append(globalLoggerInitialLevel);
		sb.append(", localisation=").append(localisation);
		sb.append(", rebuild=").append(rebuild);
		sb.append(", serverConfig=").append(serverConfig);
		sb.append('}');
		return sb.toString();
	}
}
