/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 YAMLStorage.java is part of BukkitUtilities.

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

package name.richardson.james.bukkit.utilities.persistence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;

import name.richardson.james.bukkit.utilities.logging.LocalisedLogger;

/**
 * YAMLStorage is responsible for creating YAML configuration files, setting defaults from a provided {@link
 * InputStream} and handling any exceptions when the file is saved.
 */
public class YAMLStorage {

	private static final Logger logger = LocalisedLogger.getLogger(YAMLStorage.class);

	private final YamlConfiguration defaultConfiguration;
	private final File file;

	private YamlConfiguration configuration;

	public YAMLStorage(final File file, final InputStream defaults)
	throws IOException {
		this.file = file;
		this.defaultConfiguration = YamlConfiguration.loadConfiguration(defaults);
		defaults.close();
		this.load();
		this.setDefaults();
	}

	public YAMLStorage(final String filePath, final InputStream defaults)
	throws IOException {
		this.file = new File(filePath);
		this.defaultConfiguration = YamlConfiguration.loadConfiguration(defaults);
		defaults.close();
		this.load();
		this.setDefaults();
	}

	protected YamlConfiguration getConfiguration() {
		return this.configuration;
	}

	protected void save() {
		try {
			logger.log(Level.CONFIG, "Saving configuration: " + this.file.getName());
			this.configuration.save(this.file);
		} catch (final IOException e) {
			logger.log(Level.SEVERE, "unable-to-save");
		}
	}

	protected void setDefaults() {
		this.configuration.setDefaults(this.defaultConfiguration);
		this.configuration.options().copyDefaults(true);
		if (!this.file.exists()) {
			logger.log(Level.CONFIG, "Saving default configuration.");
			this.save();
			this.load();
		}
	}

	private void load() {
		final String className = this.getClass().getSimpleName();
		logger.log(Level.CONFIG, "Loading configuration: " + className);
		final String path = this.file.getAbsolutePath();
		logger.log(Level.CONFIG, "Using path: " + path);
		this.configuration = YamlConfiguration.loadConfiguration(this.file);
	}
}
