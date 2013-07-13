package name.richardson.james.bukkit.utilities.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.avaje.ebean.EbeanServer;

public abstract class AbstractDatabasePlugin extends AbstractPlugin {

	private EbeanServer database;

	@Override
	public EbeanServer getDatabase() {
		return this.database;
	}

	/**
	 * Attempt to load a {@link name.richardson.james.bukkit.utilities.persistence.database.SimpleDatabaseLoader} using the information from {@link DatabaseConfiguration},
	 * failing that using the settings in bukkit.yml and initalise it.
	 */
	protected void loadDatabase()
	throws IOException {
		final File file = new File(this.getDataFolder().getPath() + File.separatorChar + AbstractPlugin.DATABASE_CONFIG_NAME);
		final InputStream defaults = this.getResource(DATABASE_CONFIG_NAME);
		// final SimpleDatabaseConfiguration configuration = new SimpleDatabaseConfiguration(file, defaults, this.getName());
		// final SimpleDatabaseLoader loader = new SimpleDatabaseLoader(configuration, this.getDatabaseClasses(), this.getName(), this.getClassLoader());
		//loader.initalise();
		// this.database = loader.getEbeanServer();
	}

}
