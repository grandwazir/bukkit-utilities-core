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

import java.lang.ref.WeakReference;
import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.utilities.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.command.argument.Argument;
import name.richardson.james.bukkit.utilities.command.argument.InvalidArgumentException;
import name.richardson.james.bukkit.utilities.localisation.LocalisedCoreColourScheme;
import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;
import name.richardson.james.bukkit.utilities.permissions.BukkitPermissionManager;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

@SuppressWarnings("HardCodedStringLiteral")
public abstract class AbstractCommand implements Command {

	private final List<Argument> argumentValidators = new ArrayList<Argument>();
	private final String description;
	private final String name;
	private final ResourceBundle resourceBundle = PluginResourceBundle.getBundle(this.getClass());
	private final String usage;
	private List<String> arguments;

	private ColourScheme colourScheme;

	private WeakReference<CommandSender> commandSender;

	private PermissionManager permissionManager;

	public AbstractCommand() {
		this.name = resourceBundle.getString("name");
		this.description = resourceBundle.getString("description");
		this.usage = resourceBundle.getString("usage");
		this.colourScheme = new LocalisedCoreColourScheme(resourceBundle);
		if (this.getClass().isAnnotationPresent(CommandPermissions.class)) this.setPermissions();
		if (this.getClass().isAnnotationPresent(CommandArguments.class)) this.setArgumentValidators();
	}

	public List<String> getArguments() {
		return Collections.unmodifiableList(arguments);
	}

	protected ColourScheme getColourScheme() {
		return colourScheme;
	}

	protected void setColourScheme(ColourScheme colourScheme) {
		this.colourScheme = colourScheme;
	}

	protected CommandSender getCommandSender() {
		return this.commandSender.get();	
	}

	private void setCommandSender(CommandSender sender) {
		this.commandSender = new WeakReference<CommandSender>(sender);
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
		if (this.permissionManager == null) {
			return true;
		}
		for (final Permission permission : this.permissionManager.listPermissions()) {
			if (permissible.hasPermission(permission)) {
				return true;
			}
		}
		colourScheme.format(ColourScheme.Style.ERROR, "access-denied");
		return false;
	}

	protected void execute() {
		return;
	}

	@Override
	public final boolean onCommand(List<String> arguments, CommandSender commandSender) {
		this.arguments = arguments;
		this.setCommandSender(commandSender);
		try {
			if (!isAuthorized(commandSender)) return true;
			if (!parseArguments()) return true;
			this.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return true;
		}
	}

	@Override
	public final boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
		return this.onCommand(new LinkedList<String>(Arrays.asList(strings)), commandSender);
	}

	@Override
	public final List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
		return this.onTabComplete(Arrays.asList(strings));
	}

	public final List<String> onTabComplete(final List<String> arguments) {
		final List<String> results = new ArrayList<String>();
		if (this.getClass().isAnnotationPresent(CommandArguments.class) && !arguments.isEmpty()) {
			if (this.argumentValidators.size() >= (arguments.size())) {
				final int index = arguments.size() - 1;
				final Argument argument = this.argumentValidators.get(index);
				results.addAll(argument.getMatches(arguments.get(index)));
			}
		}
		return results;
	}

	protected List<Argument> getArgumentValidators() {
		return Collections.unmodifiableList(this.argumentValidators);
	}

	protected PermissionManager getPermissionManager() {
		return this.permissionManager;
	}

	protected ResourceBundle getResourceBundle() {
		return this.resourceBundle;
	}

	protected boolean parseArguments() {
		return true;
	}

	protected void setArgumentValidators() {
		final CommandArguments annotation = this.getClass().getAnnotation(CommandArguments.class);
		for (final Class<? extends Argument> argumentClass : annotation.arguments()) {
			try {
				this.argumentValidators.add(argumentClass.getConstructor().newInstance());
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setPermissions() {
		final CommandPermissions annotation = this.getClass().getAnnotation(CommandPermissions.class);
		this.permissionManager = new BukkitPermissionManager(Bukkit.getPluginManager());
		this.permissionManager.createPermissions(annotation.permissions());
	}

}
