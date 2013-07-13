/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 Command.java is part of bukkit-utilities.

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

import java.util.Set;

import org.bukkit.permissions.Permissible;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.formatters.localisation.Localised;

public interface Command extends Localised {

	/**
	 * Attempt to execute this command using the arguments contained within the {@link name.richardson.james.bukkit.utilities.command.CommandContext}. Any feedback regarding success or failure will be handled
	 * within this command.
	 *
	 * @param commandContext
	 */
	public void execute(CommandContext commandContext);

	/**
	 * Return a {@link Set<String>} of possible matches for the last argument provided within the {@link name.richardson.james.bukkit.utilities.command.CommandContext}.
	 *
	 * @param commandContext
	 * @return Set of possible matches, an empty Set if no matches are found.
	 */
	public Set<String> getArgumentMatches(CommandContext commandContext);

	public String getDescription();

	public String getName();

	public String getUsage();

	/**
	 * Check to see if the {@link Permissible} has access to use or retrieve additional information about this Command.
	 * @param permissible
	 * @return true if the Permissible has any of the permissions registered to this Command, otherwise false.
	 */
	public boolean isAuthorised(Permissible permissible);

}
