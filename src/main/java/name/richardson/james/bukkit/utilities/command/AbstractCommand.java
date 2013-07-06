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

import name.richardson.james.bukkit.utilities.command.matcher.Matcher;
import name.richardson.james.bukkit.utilities.formatters.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.formatters.colours.CoreColourScheme;
import name.richardson.james.bukkit.utilities.formatters.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.permissions.Permissions;

public abstract class AbstractCommand implements Command {

	private static final ResourceBundle MESSAGES_RESOURCE_BUNDLE = ResourceBundle.getBundle(ResourceBundles.MESSAGES.getBundleName());
	private static final ResourceBundle COMMANDS_RESOURCE_BUNDLE = ResourceBundle.getBundle(ResourceBundles.COMMANDS.getBundleName());

	private final CoreColourScheme colourScheme = new CoreColourScheme();
	private final String description;
	private final List<Matcher> matchers = new ArrayList<Matcher>();
	private final String name;
	private final PermissionManager permissionManager;
	private final String usage;

	public AbstractCommand(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
		String keyPrefix = this.getClass().getSimpleName().toLowerCase() + ".";
		name = COMMANDS_RESOURCE_BUNDLE.getString(keyPrefix + "name");
		description = COMMANDS_RESOURCE_BUNDLE.getString(keyPrefix + "description");
		usage = COMMANDS_RESOURCE_BUNDLE.getString(keyPrefix + "usage");
	}

	@Override
	public Set<String> getArgumentMatches(Context context) {
		if ((matchers.size() != 0 && context.size() != 0) && matchers.size() >= context.size()) {
			final String argument = context.getString(context.size() - 1);
			return matchers.get(context.size() - 1).matches(argument);
		} else {
			return Collections.emptySet();
		}
	}

	public ColourScheme getColourScheme() {
		return colourScheme;
	}

	public String getColouredMessage(ColourScheme.Style style, String key, Object... arguments) {
		String message = MESSAGES_RESOURCE_BUNDLE.getString(key);
		return colourScheme.format(style, message, arguments);
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getMessage(String key, Object... arguments) {
		return MessageFormat.format(MESSAGES_RESOURCE_BUNDLE.getString(key), arguments);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ResourceBundle getResourceBundle() {
		return MESSAGES_RESOURCE_BUNDLE;
	}

	@Override
	public String getUsage() {
		return usage;
	}

	/**
	 * Check to see if the {@link org.bukkit.permissions.Permissible} has access to use or retrieve additional information about this Command.
	 *
	 * @param permissible
	 * @return true if the Permissible has any of the permissions registered to this Command, otherwise false.
	 */
	@Override
	public boolean isAuthorised(Permissible permissible) {
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
