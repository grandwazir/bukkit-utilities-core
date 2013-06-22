/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractCommand.java is part of BukkitUtilities.

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

import java.util.*;

import name.richardson.james.bukkit.utilities.argument.Argument;
import name.richardson.james.bukkit.utilities.argument.InvalidArgumentException;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;
import name.richardson.james.bukkit.utilities.permissions.BukkitPermissionManager;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

@SuppressWarnings("HardCodedStringLiteral")
public abstract class AbstractCommand implements Command {

	private final ResourceBundle resourceBundle = PluginResourceBundle.getBundle(this.getClass());
	private final String description;
    private final List<Argument> arguments = new ArrayList<Argument>();
	private final String name;
	private final String usage;

	private BukkitPermissionManager permissionManager;

	public AbstractCommand() {
		this.name = resourceBundle.getString("name");
		this.description = resourceBundle.getString("description");
		this.usage = resourceBundle.getString("usage");
		if (this.getClass().isAnnotationPresent(CommandPermissions.class)) {
			this.setPermissions();
		}
        if (this.getClass().isAnnotationPresent(CommandArguments.class)) {
            this.setArguments();
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
		if (this.getClass().isAnnotationPresent(CommandArguments.class) && !arguments.isEmpty()) {
			if (this.arguments.size() >= (arguments.size())) {
				final int index = arguments.size() - 1;
				final Argument argument = this.arguments.get(index);
				results.addAll(argument.getMatches(arguments.get(index)));
			}
		}
		return results;
	}

    protected ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }


    protected List<Argument> getArguments() {
        return Collections.unmodifiableList(this.arguments);
    }

	protected PermissionManager getPermissionManager() {
		return this.permissionManager;
	}

    protected void setArguments() {
        final CommandArguments annotation = this.getClass().getAnnotation(CommandArguments.class);
        for (final Class<? extends Argument> argumentClass : annotation.arguments()) {
            try {
                this.arguments.add(argumentClass.getConstructor().newInstance());
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

    protected void parseArguments(List<String> arguments) throws InvalidArgumentException {
        if (this.getClass().isAnnotationPresent(CommandArguments.class) && !arguments.isEmpty()) {
            if (this.arguments.size() >= (arguments.size())) {
                final int index = arguments.size() - 1;
                final Argument argument = this.arguments.get(index);
                argument.parseValue(arguments.get(index));
            }
        }
    }

}
