/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 Command.java is part of BukkitUtilities.

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

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;

public interface Command {

	public void execute(List<String> arguments, CommandSender sender);

	public String getDescription();

	public String getName();

	public String getUsage();

	public boolean isAuthorized(Permissible permissible);

	public List<String> onTabComplete(List<String> arguments, CommandSender sender);

}
