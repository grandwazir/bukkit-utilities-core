/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * AbstractPlugin.java is part of BukkitUtilities.
 * 
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

import com.avaje.ebean.EbeanServer;

import name.richardson.james.bukkit.utilities.configuration.PluginConfiguration;
import name.richardson.james.bukkit.utilities.configuration.SimpleDatabaseConfiguration;
import name.richardson.james.bukkit.utilities.configuration.SimplePluginConfiguration;
import name.richardson.james.bukkit.utilities.logging.Logger;
import name.richardson.james.bukkit.utilities.metrics.MetricsListener;
import name.richardson.james.bukkit.utilities.permissions.BukkitPermissionManager;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.persistence.SQLStorage;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater.State;
import name.richardson.james.bukkit.utilities.updater.Updatable;

public abstract class AbstractPlugin extends JavaPlugin implements Updatable {

	/* The configuration file for this plugin */
	private PluginConfiguration configuration;
	/* The custom logger that belongs to this plugin */
	private final Logger logger = new Logger(this);
	/* The database that belongs to this plugin */
	private EbeanServer database;
	private PermissionManager permissionManager;

	@Override
	public EbeanServer getDatabase() {
		return this.database;
	}

	public String getGroupID() {
		return "name.richardson.james.bukkit";
	}

	public URL getRepositoryURL() {
		try {
			switch (this.configuration.getAutomaticUpdaterBranch()) {
				case DEVELOPMENT:
					return new URL("http://repository.james.richardson.name/snapshots");
				default:
					return new URL("http://repository.james.richardson.name/releases");
			}
		} catch (final MalformedURLException e) {
			return null;
		}
	}

	protected Logger getCustomLogger() {
		return this.logger;
	}

	protected void loadConfiguration() throws IOException {
		final File file = new File(this.getDataFolder().getPath() + File.separatorChar + "config.yml");
		final InputStream defaults = this.getResource("config.yml");
		this.configuration = new SimplePluginConfiguration(file, defaults);
		this.logger.setLevel(this.configuration.getLogLevel());
	}

	protected void loadDatabase() throws IOException {
		final File file = new File(this.getDataFolder().getPath() + File.separatorChar + "database.yml");
		final InputStream defaults = this.getResource("database.yml");
		final SimpleDatabaseConfiguration configuration = new SimpleDatabaseConfiguration(file, defaults, this.getName());
		final SQLStorage loader = new SQLStorage(configuration, this.getDatabaseClasses(), this.getName(), this.getClassLoader());
		loader.initalise();
		this.database = loader.getEbeanServer();
	}

	protected void setPermissions() {
		if (this.getClass().isAnnotationPresent(PluginPermissions.class)) {
			final PluginPermissions annotation = this.getClass().getAnnotation(PluginPermissions.class);
			this.permissionManager = new BukkitPermissionManager();
			this.permissionManager.createPermissions(annotation.permissions());
		}
	}

	protected void setupMetrics() throws IOException {
		if (this.configuration.isCollectingStats()) {
			new MetricsListener(this);
		}
	}

	protected void updatePlugin() {
		if (this.configuration.getAutomaticUpdaterState() != State.OFF) {
			final PluginUpdater updater = new PluginUpdater(this);
			this.getServer().getScheduler().runTaskLaterAsynchronously(this, updater, new Random().nextInt(20) * 20);
		}
	}
}
