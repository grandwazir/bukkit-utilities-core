/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * SimplePlugin.java is part of BukkitUtilities.
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.internals.Logger;
import name.richardson.james.bukkit.utilities.permissions.PermissionsHolder;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater;
import name.richardson.james.bukkit.utilities.updater.Updatable;

/**
 * This class provides a basic implementation of the Debuggable, Localisable and
 * Permissions interface for a standard plugin. It is provided as a convenience
 * to avoid duplicating the same code across numerous plugins.
 */
public abstract class SimplePlugin extends JavaPlugin implements Debuggable, Localisable, PermissionsHolder, Updatable {

  /** The console logger. */
  protected final Logger logger = new Logger(this.getClass());
  
  /** A plugin updater for this plugin **/
  private PluginUpdater updater;

  /** A list of permissions owned by this plugin */
  private final List<Permission> permissions = new LinkedList<Permission>();

  /** The current locale. */
  private final Locale locale = Locale.getDefault();

  /** The ResourceBundle containing messages for this plugin. */
  private ResourceBundle messages;

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.util.Permissions#setPermission(org.bukkit.
   * permissions.Permission)
   */
  public void addPermission(final Permission permission) {
    final PluginManager pm = this.getServer().getPluginManager();
    pm.addPermission(permission);
    this.permissions.add(permission);
    this.logger.debug(String.format("Adding permission: %s (default: %s)", permission.getName(), permission.getDefault()));
  }

  public String getChoiceFormattedMessage(final String key, final Object[] arguments, final String[] formats, final double[] limits) {
    final MessageFormat formatter = new MessageFormat(this.messages.getString(key));
    final ChoiceFormat cFormatter = new ChoiceFormat(limits, formats);
    formatter.setFormatByArgumentIndex(0, cFormatter);
    return ColourFormatter.replace("&", formatter.format(arguments));
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.util.plugin.Localisable#getLocale()
   */
  public Locale getLocale() {
    return this.locale;
  }

  public String getMessage(final String key) {
    return this.messages.getString(key);
  }

  public Permission getPermission(final int index) {
    if (this.permissions.size() > index) {
      return this.permissions.get(index);
    } else {
      return null;
    }
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.util.Permissions#getPermission(java.lang.String
   * )
   */
  public Permission getPermission(final String permission) {
    final PluginManager pm = this.getServer().getPluginManager();
    return pm.getPermission(permission);
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.util.Permissions#getPermissions()
   */
  public List<Permission> getPermissions() {
    return Collections.unmodifiableList(this.permissions);
  }

  /*
   * Get the root permission associated with this plugin.
   * The root permission should be a wildcard style permission (eg.
   * banhammer.*), usually with
   * other permissions attached through setting this permission as their parent.
   * It should be implement so if a player is given this permission they are
   * allowed to use all features of the plugin without restriction.
   * @return permission
   */
  public Permission getRootPermission() {
    return this.permissions.get(0);
  }

  public String getSimpleFormattedMessage(final String key, final Object argument) {
    final Object[] arguments = { argument };
    return this.getSimpleFormattedMessage(key, arguments);
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.util.plugin.Localisable#getMessage(java.lang
   * .String)
   */
  public String getSimpleFormattedMessage(final String key, final Object[] arguments) {
    final MessageFormat formatter = new MessageFormat(this.messages.getString(key));
    formatter.setLocale(this.locale);
    return ColourFormatter.replace("&", formatter.format(arguments));
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.util.plugin.Debuggable#isDebugging()
   */
  public boolean isDebugging() {
    return Logger.isDebugging(this);
  }

  /*
   * (non-Javadoc)
   * @see org.bukkit.plugin.Plugin#onDisable()
   */
  @Override
  public void onDisable() {
    final String[] args = { this.getDescription().getName() };
    this.logger.info(this.getSimpleFormattedMessage("plugin-disabled", args));
  }

  /*
   * (non-Javadoc)
   * @see org.bukkit.plugin.Plugin#onEnable()
   */
  @Override
  public void onEnable() {
    try {
      this.setLoggerPrefix();
      this.setRootPermission();
      this.setResourceBundle();
    } catch (final IOException e) {
      e.printStackTrace();
    }
    final String[] args = { this.getDescription().getFullName() };
    this.logger.info(this.getSimpleFormattedMessage("plugin-enabled", args));
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.util.plugin.Debuggable#setDebugging(boolean)
   */
  public void setDebugging(final boolean debugging) {
    Logger.setDebugging(this, debugging);
  }

  private FileInputStream getResourceBundleOverride(final String path) {
    try {
      return new FileInputStream(path);
    } catch (final FileNotFoundException e) {
      return null;
    }
  }

  /**
   * Sets the prefix of the console logger based on the name of the plugin.
   * 
   * For example if the name of the plugin is 'BanHammer' the resulting prefix
   * will be '[BanHammer] '.
   */
  protected void setLoggerPrefix() {
    this.logger.setPrefix("[" + this.getDescription().getName() + "] ");
  }

  /**
   * Sets the resource bundle for this plugin.
   * 
   * It first attempts to check if the user has created their own
   * localisation.properties file in the plugin's datafolder. If they have it
   * load those strings and ignore the built in ResourceBundles.
   * 
   * If no override exists it will search for an appropriate ResourceBundle in
   * the plugin.jar in the usual way.
   * 
   * @throws IOException
   */
  protected void setResourceBundle() throws IOException {
    final String path = this.getDataFolder().getAbsolutePath() + "/localisation.properties";
    // check for any overrides placed in the plugin data directory
    final FileInputStream stream = this.getResourceBundleOverride(path);
    if (stream != null) {
      try {
        this.messages = new PropertyResourceBundle(stream);
        this.logger.debug(String.format("Using localisation override located at: %s.", path));
      } finally {
        stream.close();
      }
    }

    // if no override is present, use built in localisation.
    if (this.messages == null) {
      // to get around a bug where we might end up loading localisation files
      // from other jars.
      this.messages = ResourceBundle.getBundle(this.getDescription().getName().toLowerCase() + "-localisation", this.locale, this.getClassLoader());
      if (this.messages.getLocale() != null) {
        this.logger.debug(String.format("Using built in localisation: %s_%s.", this.locale.getLanguage(), this.locale.getCountry()));
      } else {
        this.logger.debug("Using default localisation.");
      }

    }

  }

  /**
   * Sets the root permission for this object.
   * 
   * The root permission is the parent permission that all permissions
   * associated to this object should share. This is automatically determined
   * based on the name of the plugin.
   */
  protected void setRootPermission() {
    // final PluginManager pm = this.getServer().getPluginManager();
    final String node = this.getDescription().getName().toLowerCase() + ".*";
    final String description = this.getSimpleFormattedMessage("plugin-wildcard-description", this.getDescription().getName());
    final Permission permission = new Permission(node, description, PermissionDefault.OP);
    this.addPermission(permission);
  }
  
  protected void update(boolean installUpdates) {
    if (this.updater == null) this.updater = new PluginUpdater(this, installUpdates);
    Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(this, this.updater);
  }
  
  public URL getRepositoryURL() {
    try {
      return new URL("http://repository.james.richardson.name/releases");
    } catch (MalformedURLException e) {
      return null;
    }
  }

}
