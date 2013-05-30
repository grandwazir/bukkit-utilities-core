/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * AbstractYAMLStorage.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.persistence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

import name.richardson.james.bukkit.utilities.logging.Logger;

public class YAMLStorage {

	private YamlConfiguration configuration;
	private final YamlConfiguration defaultConfiguration;
	private final File file;
	private final Logger logger = Logger.getLogger(this.getClass());

	public YAMLStorage(final File file, final InputStream defaults) throws IOException {
		this.file = file;
		this.defaultConfiguration = YamlConfiguration.loadConfiguration(defaults);
		defaults.close();
		this.load();
		this.setDefaults();
	}

	public YAMLStorage(final String filePath, final InputStream defaults) throws IOException {
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
			this.logger.log(Level.CONFIG, "Saving configuration: " + this.file.getName());
			this.configuration.save(this.file);
		} catch (final IOException e) {
			this.logger.severe("Unable to save configuration!");
		}
	}

	protected void setDefaults() throws IOException {
		this.logger.log(Level.CONFIG, "Saving default configuration.");
		this.configuration.setDefaults(this.defaultConfiguration);
		this.configuration.options().copyDefaults(true);
		if (!this.file.exists()) {
			this.save();
			this.load();
		}
	}

	private void load() {
		final String className = this.getClass().getSimpleName();
		this.logger.log(Level.CONFIG, "Loading configuration: " + className);
		final String path = this.file.getAbsolutePath();
		this.logger.log(Level.CONFIG, "Using path: " + path);
		this.configuration = YamlConfiguration.loadConfiguration(this.file);
	}
}
