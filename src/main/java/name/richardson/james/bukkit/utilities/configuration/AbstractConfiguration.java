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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.logging.PluginLoggerFactory;

import static name.richardson.james.bukkit.utilities.localisation.BukkitUtilities.*;

/**
 * AbstractConfiguration is responsible for creating YAML configuration files, setting defaults from a provided {@link InputStream} and handling any exceptions
 * when the file is saved.
 */
public abstract class AbstractConfiguration implements Configuration {

	private final YamlConfiguration defaults;
	private final File file;
	private final Logger logger = PluginLoggerFactory.getLogger(this.getClass());
	private YamlConfiguration configuration;

	/**
	 * Construct an AbstractConfiguration
	 *
	 * @param file the file where this configuration is stored
	 * @param defaults the defaults that should be used for this configuration
	 * @param useRuntimeDefaults {@code true} if the defaults should be applied if the value is missing in the configuration; {@code false} otherwise.
	 * @throws IOException
	 */
	public AbstractConfiguration(final File file, final InputStream defaults)
	throws IOException {
		Validate.notNull(file, "File can not be null!");
		Validate.notNull(file, "Defaults can not be null!");
		this.file = file;
		this.defaults = YamlConfiguration.loadConfiguration(defaults);
		this.load();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("AbstractConfiguration{");
		sb.append("configuration=").append(configuration);
		sb.append(", defaults=").append(defaults);
		sb.append(", file=").append(file);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public final void load()
	throws IOException {
		getLogger().log(Level.CONFIG, CONFIGURATION_LOADING.asMessage(this.getClass().getSimpleName()));
		getLogger().log(Level.CONFIG, CONFIGURATION_USING_PATH.asMessage(this.getFile().getAbsolutePath()));
		if (!this.getFile().exists() || this.getFile().length() == 0) {
			this.getDefaults().options().copyHeader(true);
			this.getDefaults().options().copyDefaults(true);
			getLogger().log(Level.WARNING, CONFIGURATION_SAVING_DEFAULT.asMessage(this.getFile().getName()));
			getDefaults().save(this.getFile());
		}
		this.setConfiguration(YamlConfiguration.loadConfiguration(this.getFile()));
		this.getConfiguration().options().copyDefaults(false);
	}

	protected final YamlConfiguration getDefaults() {
		return defaults;
	}

	protected final File getFile() {
		return file;
	}

	protected final YamlConfiguration getConfiguration() {
		if (this.configuration == null) throw new IllegalStateException("Configuration has not yet been loaded!");
		return this.configuration;
	}

	protected void setConfiguration(final YamlConfiguration configuration) {
		this.configuration = configuration;
	}

	protected final Logger getLogger() {
		return logger;
	}

	@Override
	public final void save()
	throws IOException {
		getLogger().log(Level.CONFIG, CONFIGURATION_SAVING.asMessage(this.getFile().getName()));
		this.getConfiguration().save(this.getFile());
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
		this.getConfiguration().setDefaults(this.getDefaults());
	}

}
