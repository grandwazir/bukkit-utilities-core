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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.configuration.PluginConfiguration;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleLoader;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleLocalisation;
import name.richardson.james.bukkit.utilities.logging.ConsoleLogger;
import name.richardson.james.bukkit.utilities.logging.Logger;
import name.richardson.james.bukkit.utilities.metrics.MetricsListener;
import name.richardson.james.bukkit.utilities.permissions.BukkitPermissionManager;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater.State;
import name.richardson.james.bukkit.utilities.updater.Updatable;

public abstract class AbstractPlugin extends JavaPlugin implements Updatable, Debuggable {

	/* The configuration file for this plugin */
	private PluginConfiguration configuration;
	/* The resource bundle being used for localisation */
	private ResourceBundleLocalisation localisation;
	/* The custom logger that belongs to this plugin */
	private final Logger logger = new ConsoleLogger(this.getClass().getName());
	/* The permission manager currently in use by the plugin */
	private PermissionManager permissions;

	public String getGroupID() {
		return "name.richardson.james.bukkit";
	}

	public URL getRepositoryURL() {
		try {
			switch (this.configuration.getAutomaticUpdaterBranch()) {
				case DEVELOPMENT :
					return new URL("http://repository.james.richardson.name/snapshots");
				default :
					return new URL("http://repository.james.richardson.name/releases");
			}
		} catch (final MalformedURLException e) {
			return null;
		}
	}

	@Override
	public void onDisable() {
		this.getServer().getScheduler().cancelTasks(this);
	}

	@Override
	public final void onEnable() {
		try {
			this.loadLocalisation();
			this.setPermissions();
			this.loadConfiguration();
			this.establishPersistence();
			this.registerCommands();
			this.registerListeners();
			this.setupMetrics();
			this.updatePlugin();
		} catch (final IOException e) {
			this.logger.severe("panic");
			e.printStackTrace();
			this.setEnabled(false);
		} catch (final SQLException e) {
			this.logger.severe("panic");
			e.printStackTrace();
			this.setEnabled(false);
		} catch (final Exception e) {
			e.printStackTrace();
			this.setEnabled(false);
		} finally {
			if (!this.isEnabled()) {
				return;
			}
		}
	}

	protected void establishPersistence() throws SQLException {
		return;
	}

	protected void loadConfiguration() throws IOException {
		return;
	}

	protected void registerCommands() {
		return;
	}

	protected void registerListeners() {
		return;
	}

	protected void setPermissions() {
		this.permissions = new BukkitPermissionManager(this.localisation);
		// create the root permission, for example `banhammer`
		final String node = this.getDescription().getName().toLowerCase();
		final String description = this.localisation.getMessage(AbstractPlugin.class, "permission-description", this
				.getDescription().getName());
		final Permission permission = new Permission(node, description, PermissionDefault.OP);
		this.permissions.addPermission(permission);
	}

	protected void setupMetrics() throws IOException {
		if (this.configuration.isCollectingStats()) {
			new MetricsListener(this);
		}
	}

	private void loadLocalisation() throws IOException {
		final ResourceBundle[] bundles = {ResourceBundleLoader.getBundle(this.getClassLoader(), "bukkitutilities"),
				ResourceBundleLoader.getBundle(this.getClassLoader(), this.getName().toLowerCase(), this.getDataFolder())};
		this.localisation = new ResourceBundleLocalisation(bundles);
	}

	private void updatePlugin() {
		if (this.configuration.getAutomaticUpdaterState() != State.OFF) {
			final PluginUpdater updater = new PluginUpdater(this, this.localisation);
			this.getServer().getScheduler().runTaskLaterAsynchronously(this, updater, new Random().nextInt(20) * 20);
		}
	}
}
