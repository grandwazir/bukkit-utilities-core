/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractPlugin.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.richardson.james.bukkit.utilities.command.DefaultCommandInvoker;
import name.richardson.james.bukkit.utilities.command.HelpCommand;
import name.richardson.james.bukkit.utilities.formatters.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.logging.PrefixedLogger;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import com.avaje.ebean.EbeanServer;
import org.mcstats.Metrics;

import name.richardson.james.bukkit.utilities.permissions.Permissions;
import name.richardson.james.bukkit.utilities.persistence.configuration.PluginConfiguration;
import name.richardson.james.bukkit.utilities.persistence.configuration.SimpleDatabaseConfiguration;
import name.richardson.james.bukkit.utilities.persistence.configuration.SimplePluginConfiguration;
import name.richardson.james.bukkit.utilities.permissions.BukkitPermissionManager;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.persistence.SQLStorage;
import name.richardson.james.bukkit.utilities.plugin.updater.MavenPluginUpdater;
import name.richardson.james.bukkit.utilities.plugin.updater.PluginUpdater;
import name.richardson.james.bukkit.utilities.plugin.updater.Updatable;

public abstract class AbstractPlugin extends JavaPlugin implements Updatable {

	public static final String CONFIG_NAME = "config.yml";
	public static final String DATABASE_CONFIG_NAME = "database.yml";

	private final Logger logger = PrefixedLogger.getLogger(this.getClass());

	private PluginConfiguration configuration;
	private PermissionManager permissionManager;

	public String getGroupID() {
		return "name.richardson.james.bukkit";
	}

	public Logger getLocalisedLogger() {
		return this.logger;
	}

	public PermissionManager getPermissionManager() {
		return permissionManager;
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

	public void onEnable() {
		try {
			this.loadConfiguration();
			this.setPermissions();
			this.updatePlugin();
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	/**
	 * Attempt to load a {@link SimplePluginConfiguration} from disk in this plugin's data folder.
	 */
	private void loadConfiguration()
	throws IOException {
		PrefixedLogger.setPrefix("[" + this.getName() + "] ");
		final File file = new File(this.getDataFolder().getPath() + File.separatorChar + AbstractPlugin.CONFIG_NAME);
		final InputStream defaults = this.getResource(CONFIG_NAME);
		this.configuration = new SimplePluginConfiguration(file, defaults);
		this.logger.setLevel(this.configuration.getLogLevel());
		this.logger.log(Level.CONFIG, "Localisation locale: {0}", Locale.getDefault());
	}

	/**
	 * Attempt to check for an update for this plugin using the settings from the {@link PluginConfiguration}.
	 *
	 * Will attempt an update check once within the next 20 seconds. The time is randomised to avoid multiple plugins
	 * all making a check at the same time.
	 */
	private void updatePlugin() {
		if (this.configuration.getAutomaticUpdaterState() != PluginUpdater.State.OFF) {
			final PluginUpdater updater = new MavenPluginUpdater(this, this.configuration.getAutomaticUpdaterState());
			this.getServer().getScheduler().runTaskLaterAsynchronously(this, updater, new Random().nextInt(20) * 20);
		}
	}

	private void setPermissions() {
		if (this.getClass().isAnnotationPresent(Permissions.class)) {
			final Permissions annotation = this.getClass().getAnnotation(Permissions.class);
			this.permissionManager = new BukkitPermissionManager();
			this.permissionManager.createPermissions(annotation.permissions());
		}
	}

}
