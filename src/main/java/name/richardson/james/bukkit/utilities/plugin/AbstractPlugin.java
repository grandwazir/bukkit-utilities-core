/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * SkeletonPlugin.java is part of BukkitUtilities.
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
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.Random;
import java.util.ResourceBundle;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.configuration.PluginConfiguration;
import name.richardson.james.bukkit.utilities.internals.Logger;
import name.richardson.james.bukkit.utilities.localisation.Localisable;
import name.richardson.james.bukkit.utilities.permissions.PermissionsHolder;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater;
import name.richardson.james.bukkit.utilities.updater.State;
import name.richardson.james.bukkit.utilities.updater.Updatable;

public abstract class AbstractPlugin extends JavaPlugin implements Updatable {

  /* A list of resource bundles used by the plugin */
  private final List<ResourceBundle> bundles = new LinkedList<ResourceBundle>();

  /* The configuration file for this plugin */
  private PluginConfiguration configuration;

  /* The locale of the system the plugin is running on */
  private final Locale locale = Locale.getDefault();

  /* The logger that belongs to this plugin */
  private final name.richardson.james.bukkit.utilities.logging.Logger logger;

  /** A list of permissions owned by this plugin */
  private final List<Permission> permissions = new LinkedList<Permission>();

  public String getGroupID() {
    return "name.richardson.james.bukkit";
  }

  public Locale getLocale() {
    return this.locale;
  }

  public final String getLoggerPrefix() {
    return this.logger.getPrefix();
  }

  public URL getRepositoryURL() {
    try {
      switch (this.configuration.) {
      case DEVELOPMENT:
        return new URL("http://repository.james.richardson.name/snapshots");
      default:
        return new URL("http://repository.james.richardson.name/releases");
      }
    } catch (final MalformedURLException e) {
      return null;
    }
  }

  public boolean isDebugging() {
    return Logger.isDebugging(this);
  }

  @Override
  public void onDisable() {
    this.getServer().getScheduler().cancelTasks(this);
    this.logger.info(this.getSimpleFormattedMessage("plugin-disabled", this.getName()));
  }

  @Override
  public final void onEnable() {
    // set the prefix of the logger for this plugin
    // all other classes attached to this plugin should use the same prefix
    this.logger.setPrefix("[" + this.getName() + "] ");

    // attempt to load the resource bundles for the plugin
    try {
      this.loadResourceBundles();
      this.loadConfiguration();
      this.setPermissions();
      this.setupPersistence();
      this.registerEvents();
      this.setupMetrics();
      this.registerPermissions();
      this.registerCommands();
      this.updatePlugin();
    } catch (final IOException e) {
      this.logger.severe(this.getMessage("panic"));
      e.printStackTrace();
      this.setEnabled(false);
    } catch (final SQLException e) {
      this.logger.severe(this.getMessage("panic"));
      e.printStackTrace();
      this.setEnabled(false);
    } catch (final Exception e) {
      this.logger.severe(this.getMessage("panic"));
      e.printStackTrace();
      this.setEnabled(false);
    } finally {
      if (!this.isEnabled()) {
        return;
      }
    }

    this.logger.info(this.getSimpleFormattedMessage("plugin-enabled", this.getDescription().getFullName()));

  }

  public void setDebugging(final boolean value) {
    Logger.setDebugging(this, value);
  }

  protected void loadConfiguration() throws IOException {
    this.logger.debug("Loading initial configuration.");
    this.configuration = new PluginConfiguration(this);
    if (this.configuration.isDebugging()) {
      this.setDebugging(true);
    }
  }

  protected void registerCommands() {
    this.logger.debug("Skipping registering commands.");
  }

  protected void registerEvents() {
    this.logger.debug("Skipping registering events and listeners.");
  }

  protected void registerPermissions() {
    this.logger.debug("Skipping registering permissions.");
  }

  /**
   * Sets the root permission for this object.
   * 
   * The root permission is the parent permission that all permissions
   * associated to this object should share. This is automatically determined
   * based on the name of the plugin.
   */
  protected void setRootPermission() {
    final String node = this.getDescription().getName().toLowerCase() + ".*";
    final String description = this.getSimpleFormattedMessage("plugin-wildcard-description", this.getDescription().getName());
    final Permission permission = new Permission(node, description, PermissionDefault.OP);
    this.addPermission(permission);
  }

  protected void setupMetrics() throws IOException {
    this.logger.debug("Skipping setting up metrics.");
  }

  protected void setupPersistence() throws SQLException {
    this.logger.debug("Skipping setting up persistence.");
  }

  private void loadResourceBundles() throws IOException {
    this.logger.debug("Loading resource bundles...");
    this.setPluginResourceBundle();
    this.setCoreResourceBundle();
  }

  private void setCoreResourceBundle() {
    final ResourceBundle bundle = ResourceBundle.getBundle("bukkitutilities-localisation", this.locale, this.getClassLoader());
    this.bundles.add(bundle);
    this.logger.debug("Using default BukkitUtilities localisation.");
  }

  private void setPermissions() {
    this.logger.debug("Setting permissions");
    final String node = this.getDescription().getName().toLowerCase() + ".*";
    final String description = this.getSimpleFormattedMessage("plugin-wildcard-description", this.getDescription().getName());
    final Permission permission = new Permission(node, description, PermissionDefault.OP);
    this.addPermission(permission);
  }

  private void setPluginResourceBundle() throws IOException {
    final String path = this.getDataFolder().getAbsolutePath() + File.separator + "localisation.properties";
    final File customBundle = new File(path);

    // check for any overrides placed in the plugin data directory
    if (customBundle.exists()) {
      final FileInputStream stream = new FileInputStream(customBundle);
      this.bundles.add(new PropertyResourceBundle(stream));
      this.logger.debug("Using plugin localisation override located at: " + path);
      // if no override is present, use built in localisation.
    } else {
      final ResourceBundle bundle = ResourceBundle.getBundle(this.getName().toLowerCase() + "-localisation", this.locale, this.getClassLoader());
      this.bundles.add(bundle);
      if (bundle.getLocale() != null) {
        this.logger.debug(String.format("Using plugin localisation: %s_%s.", this.locale.getLanguage(), this.locale.getCountry()));
      } else {
        this.logger.debug("Using default plugin localisation.");
      }
    }

  }

  private void updatePlugin() {
    if (this.configuration.getAutomaticUpdaterState() != State.OFF) {
      final long delay = new Random().nextInt(20) * 20;
      this.getServer().getScheduler().scheduleAsyncDelayedTask(this, new PluginUpdater(this, this.configuration.getAutomaticUpdaterState()), delay);
    }
  }

}
