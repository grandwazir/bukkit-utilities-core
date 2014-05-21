/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractConfiguration.java is part of BukkitUtilities.

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

package name.richardson.james.bukkit.utilities.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

import org.apache.commons.lang.Validate;

/**
 * AbstractConfiguration is responsible for creating YAML configuration files, setting defaults from a provided {@link InputStream} and handling any exceptions
 * when the file is saved.
 */
public abstract class AbstractConfiguration implements Configuration {

	private final YamlConfiguration defaults;
	private final File file;
	private YamlConfiguration configuration;

	/**
	 * Construct an AbstractConfiguration
	 *
	 * @param file  the file where this configuration is stored
	 * @param defaults  the defaults that should be used for this configuration
	 * @throws IOException
	 */
	public AbstractConfiguration(final File file, final InputStream defaults)
	throws IOException {
		Validate.notNull(file, "File can not be null!");
		Validate.notNull(file, "Defaults can not be null!");
		this.file = file;
		this.defaults = YamlConfiguration.loadConfiguration(defaults);
		load();
	}

	@Override
	public final void load()
	throws IOException {
		File storage = getFile();
		YamlConfiguration defaults = getDefaults();
		YamlConfigurationOptions options = defaults.options();
		if (!storage.exists() || storage.length() == 0) {
			options.copyHeader(true);
			options.copyDefaults(true);
			defaults.save(getFile());
		}
		setConfiguration(YamlConfiguration.loadConfiguration(getFile()));
		options.copyDefaults(false);
	}

	@Override
	public final void save()
	throws IOException {
		YamlConfiguration configuration = getConfiguration();
		configuration.save(getFile());
	}

	/**
	 * Set this configuration to use runtime defaults.
	 *
	 * This means that the default configuration values specified when creating the configuration will apply in the absence of any missing values in the actual
	 * configuration. This is useful mainly for important configuration files which have values which must be set. This however does not write back these missing
	 * values to file on saving.
	 */
	@Override
	public final void useRuntimeDefaults() {
		YamlConfiguration configuration = getConfiguration();
		configuration.setDefaults(getDefaults());
	}

	protected final YamlConfiguration getConfiguration() {
		Validate.notNull(configuration, "Configuration has not yet been loaded!");
		return configuration;
	}

	protected final YamlConfiguration getDefaults() {
		return defaults;
	}

	protected final File getFile() {
		return file;
	}

	protected final void setConfiguration(final YamlConfiguration configuration) {
		this.configuration = configuration;
	}

}
