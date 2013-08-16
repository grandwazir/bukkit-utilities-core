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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.richardson.james.bukkit.utilities.logging.AbstractPrefixedLogger;

import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.permissions.Permissions;
import name.richardson.james.bukkit.utilities.persistence.configuration.PluginConfiguration;
import name.richardson.james.bukkit.utilities.persistence.configuration.SimplePluginConfiguration;
import name.richardson.james.bukkit.utilities.permissions.BukkitPermissionManager;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.plugin.updater.MavenPluginUpdater;
import name.richardson.james.bukkit.utilities.plugin.updater.PluginUpdater;

public abstract class AbstractPlugin extends JavaPlugin {

	public static final String CONFIG_NAME = "config.yml";
	public static final String DATABASE_CONFIG_NAME = "database.yml";

	private final Logger logger = AbstractPrefixedLogger.getLogger(this.getClass());

	private PluginConfiguration configuration;
	private PermissionManager permissionManager;

	public String getGroupID() {
		return "name.richardson.james.bukkit";
	}

	public String getArtifactId() {
		return "";
	}

	public Logger getLocalisedLogger() {
		return this.logger;
	}

	public PermissionManager getPermissionManager() {
		return permissionManager;
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
		final File file = new File(this.getDataFolder().getPath(), AbstractPlugin.CONFIG_NAME);
		final InputStream defaults = this.getResource(CONFIG_NAME);
		this.configuration = new SimplePluginConfiguration(file, defaults);
		this.logger.setLevel(this.configuration.getLogLevel());
		this.logger.log(Level.CONFIG, "localisation locale: {0}", Locale.getDefault());
	}

	/**
	 * Attempt to check for an update for this plugin using the settings from the {@link PluginConfiguration}.
	 */
	private void updatePlugin() {
		if (this.configuration.getAutomaticUpdaterState() != PluginUpdater.State.OFF) {
			final PluginUpdater updater = new MavenPluginUpdater(getArtifactId(), getGroupID(), getDescription(), this.configuration.getAutomaticUpdaterBranch(), this.configuration.getAutomaticUpdaterState());
			this.getServer().getScheduler().runTaskAsynchronously(this, updater);
		}
	}

	private void setPermissions() {
		if (this.getClass().isAnnotationPresent(Permissions.class)) {
			final Permissions annotation = this.getClass().getAnnotation(Permissions.class);
			this.permissionManager = new BukkitPermissionManager(this.getServer().getPluginManager());
			this.permissionManager.createPermissions(annotation.permissions());
		}
	}

}
