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

public interface Command extends CommandMetadata {

	public void execute(CommandContext commandContext);

	public Set<String> getArgumentMatches(CommandContext commandContext);

	public boolean isAuthorised(Permissible permissible);

}
