/*
 * Copyright (c) 2013 James Richardson.
 *
 * PluginLocalisation.java is part of BukkitUtilities.
 *
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.localisation;

public interface PluginLocalisation {

	public static String PLUGIN_NAME = "plugin.name";
	public static String PLUGIN_VERSION = "plugin.version";
	public static String PLUGIN_DESCRIPTION = "plugin.description";

	public static String COMMAND_INVALID_ARGUMENT = "command.invalid-argument-exception";

	public static String CONFIGURATION_LOADING = "configuration.loading";
	public static String CONFIGURATION_SAVING_DEFAULT = "configuration.saving-default";
	public static String CONFIGURATION_USING_PATH = "configuration.using-path";
	public static String CONFIGURATION_SAVING = "configuration.saving";
	public static String CONFIGURATION_INVALID_VALUE = "configuration.invalid-value";
	public static String CONFIGURATION_OVERRIDE_VALUE = "configuration.override-value";

	public static String HELPCOMMAND_HEADER = "helpcommand.header";
	public static String HELPCOMMAND_HINT = "helpcommand.hint";
	public static String HELPCOMMAND_COMMAND_DESC = "helpcommand.command-description";

	public static String DATABASE_REBUILT_SCHEMA = "database.rebuilt-schema";
	public static String DATABASE_CREATING = "database.creating";
	public static String DATABASE_DROPPING_TABLES = "database.dropping-tables";
	public static String DATABASE_LOADING = "database.loading";
	public static String DATABASE_INVALID_SCHEMA = "database.invalid-schema";
	public static String DATABASE_VALID_SCHEMA = "database.schema-is-valid";
	public static String DATABASE_UPGRADE_REQUIRED = "database.upgrade-is-required";

	public static String UPDATER_MANUAL_UPDATE_REQUIRED = "updater.manual-update-required";
	public static String UPDATER_DOWNLOADING = "updater.downloading";
	public static String UPDATER_NEW_VERSION_AVAILABLE = "updater.new-version-available";
	public static String UPDATER_ENCOUNTERED_EXCEPTION = "updater.unable-to-update";

	public static String COMMAND_NO_PERMISSION = "command.no-permission";
	public static String COMMAND_MUST_SPECIFY_PLAYER = "command.must-specify-player";

}
