/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 * 
 * AbstractCommand.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.command;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.matchers.Matcher;
import name.richardson.james.bukkit.utilities.permissions.BukkitPermissionManager;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

@SuppressWarnings("HardCodedStringLiteral")
public abstract class AbstractCommand implements Command {

	protected final ResourceBundle localisation = PluginResourceBundle.getBundle(this.getClass());

	private final String description;
	private final List<Matcher> matchers = new ArrayList<Matcher>();
	private final String name;
	private final String usage;

	private BukkitPermissionManager permissionManager;

	public AbstractCommand() {
		this.name = localisation.getString("name");
		this.description = localisation.getString("description");
		this.usage = localisation.getString("usage");
		if (this.getClass().isAnnotationPresent(CommandPermissions.class)) {
			this.setPermissions();
		}
		if (this.getClass().isAnnotationPresent(CommandMatchers.class)) {
			this.setMatchers();
		}
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	public String getUsage() {
		return this.usage;
	}

	public boolean isAuthorized(final Permissible permissible) {
		if (this.permissionManager == null) { return true; }
		for (final Permission permission : this.permissionManager.listPermissions()) {
			if (permissible.hasPermission(permission)) { return true; }
		}
		return false;
	}

	public List<String> onTabComplete(final List<String> arguments, final CommandSender sender) {
		final List<String> results = new ArrayList<String>();
		if (this.getClass().isAnnotationPresent(CommandMatchers.class) && !arguments.isEmpty()) {
			if (this.matchers.size() >= (arguments.size())) {
				final int index = arguments.size() - 1;
				final Matcher matcher = this.matchers.get(index);
				results.addAll(matcher.getMatches(arguments.get(index)));
			}
		}
		return results;
	}

	protected List<Matcher> getMatchers() {
		return this.matchers;
	}

	protected PermissionManager getPermissionManager() {
		return this.permissionManager;
	}

	protected void setMatchers() {
		final CommandMatchers annotation = this.getClass().getAnnotation(CommandMatchers.class);
		for (final Class<? extends Matcher> matcherClass : annotation.matchers()) {
			try {
				this.matchers.add(matcherClass.getConstructor().newInstance());
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setPermissions() {
		final CommandPermissions annotation = this.getClass().getAnnotation(CommandPermissions.class);
		this.permissionManager = new BukkitPermissionManager();
		this.permissionManager.createPermissions(annotation.permissions());
	}

}
