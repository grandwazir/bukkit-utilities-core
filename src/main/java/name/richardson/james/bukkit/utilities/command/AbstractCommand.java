/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractCommand.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command;

import java.text.MessageFormat;
import java.util.*;

import org.bukkit.permissions.Permissible;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.matcher.Matcher;
import name.richardson.james.bukkit.utilities.formatters.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.formatters.colours.CoreColourScheme;
import name.richardson.james.bukkit.utilities.formatters.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.permissions.Permissions;

public abstract class AbstractCommand implements Command {

	private final ResourceBundle MESSAGES_RESOURCE_BUNDLE = ResourceBundles.MESSAGES.getBundle();
	private final ResourceBundle COMMANDS_RESOURCE_BUNDLE = ResourceBundles.COMMANDS.getBundle();

	private final CoreColourScheme colourScheme = new CoreColourScheme();
	private final String description;
	private final List<Matcher> matchers = new ArrayList<Matcher>();
	private final String name;
	private final PermissionManager permissionManager;
	private final String usage;

	public AbstractCommand(PermissionManager permissionManager) {
		Validate.notNull(permissionManager, "Permission Manager can not be null!");
		this.permissionManager = permissionManager;
		String keyPrefix = this.getClass().getSimpleName().toLowerCase() + ".";
		name = COMMANDS_RESOURCE_BUNDLE.getString(keyPrefix + "name");
		description = COMMANDS_RESOURCE_BUNDLE.getString(keyPrefix + "description");
		usage = COMMANDS_RESOURCE_BUNDLE.getString(keyPrefix + "usage");
		this.setPermissions();
	}

	private void setPermissions() {
		if (this.getClass().isAnnotationPresent(Permissions.class)) {
			final Permissions annotation = this.getClass().getAnnotation(Permissions.class);
			this.permissionManager.createPermissions(annotation.permissions());
		}
	}

	@Override
	public final Set<String> getArgumentMatches(CommandContext commandContext) {
		if ((matchers.size() != 0 && commandContext.size() != 0) && matchers.size() >= commandContext.size()) {
			final String argument = commandContext.getString(commandContext.size() - 1);
			return matchers.get(commandContext.size() - 1).matches(argument);
		} else {
			return Collections.emptySet();
		}
	}

	public ColourScheme getColourScheme() {
		return colourScheme;
	}

	public final String getColouredMessage(ColourScheme.Style style, String key, Object... arguments) {
		String message = getResourceBundle().getString(key);
		return getColourScheme().format(style, message, arguments);
	}

	@Override
	public final String getDescription() {
		return description;
	}

	@Override
	public final String getMessage(String key, Object... arguments) {
		return MessageFormat.format(MESSAGES_RESOURCE_BUNDLE.getString(key), arguments);
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final ResourceBundle getResourceBundle() {
		return MESSAGES_RESOURCE_BUNDLE;
	}

	@Override
	public final String getUsage() {
		return usage;
	}

	/**
	 * Check to see if the {@link org.bukkit.permissions.Permissible} has access to use or retrieve additional information about this Command.
	 *
	 * @param permissible
	 * @return true if the Permissible has any of the permissions registered to this Command, otherwise false.
	 */
	@Override
	public final boolean isAuthorised(Permissible permissible) {
		if (this.getClass().isAnnotationPresent(Permissions.class)) {
			for (String permissionName : this.getClass().getAnnotation(Permissions.class).permissions()) {
				if (permissible.hasPermission(permissionName)) return true;
			}
		}
		return false;
	}

	protected void addMatcher(Matcher matcher) {
		matchers.add(matcher);
	}

}
