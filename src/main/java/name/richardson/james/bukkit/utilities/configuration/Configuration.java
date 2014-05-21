/*
 * Copyright (c) 2014 James Richardson.
 *
 * Configuration.java is part of BukkitUtilities.
 *
 * bukkit-utilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * bukkit-utilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * bukkit-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.configuration;

import java.io.IOException;

public interface Configuration {

	/**
	 * Attempt to load the configuration from disk.
	 *
	 * @throws IOException if the configuration could not be found or created.
	 */
	public void load()
	throws IOException;

	/**
	 * Attempt to save the configuration to disk.
	 *
	 * @throws IOException if the configuration could not be saved.
	 */
	public void save()
	throws IOException;

	/**
	 * Set this configuration to use runtime defaults.
	 *
	 * This means that the default configuration values specified when creating the configuration will apply in the absence of any missing values in the actual
	 * configuration. This is useful mainly for important configuration files which have values which must be set. This however does not write back these missing
	 * values to file on saving.
	 */
	public void useRuntimeDefaults();

}
