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

import java.text.MessageFormat;
import java.util.ResourceBundle;

import name.richardson.james.bukkit.utilities.formatters.DefaultMessageFormatter;
import name.richardson.james.bukkit.utilities.formatters.MessageFormatter;

public enum BukkitUtilities implements Localised, MessageFormatter {

	PLUGIN_NAME ("plugin.name"),
	PLUGIN_VERSION ("plugin.version"),
	PLUGIN_DESCRIPTION ("plugin.description"),

	INVOKER_NO_PERMISSION("invoker.no-permission"),
	INVOKER_INVALID_ARGUMENT("invoker.invalid-argument-exception"),

	CONFIGURATION_LOADING ("configuration.loading"),
	CONFIGURATION_SAVING_DEFAULT ("configuration.saving-default"),
	CONFIGURATION_USING_PATH ("configuration.using-path"),
	CONFIGURATION_SAVING ("configuration.saving"),
	CONFIGURATION_INVALID_VALUE ("configuration.invalid-value"),
	CONFIGURATION_OVERRIDE_VALUE ("configuration.override-value"),

	HELPCOMMAND_HEADER ("command.help.header"),
	HELPCOMMAND_HINT ("command.help.hint"),
	HELPCOMMAND_NAME ("command.help.name"),
	HELPCOMMAND_DESC ("command.help.desc"),
	HELPCOMMAND_COMMAND_USAGE_HEADER ("command.help.command-usage-header"),
	HELPCOMMAND_USAGE ("command.help.command-usage"),

	HELPCOMMAND_ARGUMENT_ID ("argument.command.id"),
	HELPCOMMAND_ARGUMENT_NAME ("argument.command.name"),
	HELPCOMMAND_ARGUMENT_DESC ("argument.command.desc"),

	DATABASE_REBUILT_SCHEMA ("database.rebuilt-schema"),
	DATABASE_CREATING ("database.creating"),
	DATABASE_DROPPING_TABLES ("database.dropping-tables"),
	DATABASE_LOADING ("database.loading"),
	DATABASE_INVALID_SCHEMA ("database.invalid-schema"),
	DATABASE_VALID_SCHEMA ("database.schema-is-valid"),
	DATABASE_UPGRADE_REQUIRED ("database.upgrade-is-required"),

	UPDATER_MANUAL_UPDATE_REQUIRED ("updater.manual-update-required"),
	UPDATER_DOWNLOADING ("updater.downloading"),
	UPDATER_NEW_VERSION_AVAILABLE ("updater.new-version-available"),
	UPDATER_ENCOUNTERED_EXCEPTION ("updater.unable-to-update");

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("localisation/Messages");
	private static final MessageFormatter FORMATTER = new DefaultMessageFormatter();
	private final String key;

	BukkitUtilities(final String key) {
		this.key = key;
	}

	private static String formatMessage(String message, Object... arguments) {
		return MessageFormat.format(message, arguments);
	}

	public String asErrorMessage(final Object... arguments) {
		String message = FORMATTER.asErrorMessage(toString());
		return formatMessage(message, arguments);
	}

	public String asHeaderMessage(final Object... arguments) {
		String message = FORMATTER.asHeaderMessage(toString());
		return formatMessage(message, arguments);
	}

	public String asInfoMessage(final Object... arguments) {
		String message = FORMATTER.asInfoMessage(toString());
		return formatMessage(message, arguments);
	}

	public String asMessage(final Object... arguments) {
		return formatMessage(toString(), arguments);
	}

	public String asWarningMessage(final Object... arguments) {
		String message = FORMATTER.asWarningMessage(toString());
		return formatMessage(message, arguments);
	}

	public String getKey() {
		return this.key;
	}

	public String toString() {
		return RESOURCE_BUNDLE.getString(getKey());
	}

}
