/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ReloadCommand.java is part of bukkit-utilities.

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

import name.richardson.james.bukkit.utilities.command.context.Context;
import name.richardson.james.bukkit.utilities.formatters.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.plugin.Reloadable;

public class ReloadCommand extends AbstractCommand {

	private final Reloadable reloadable;

	public ReloadCommand(Reloadable reloadable) {
		this.reloadable = reloadable;
	}

	@Override
	public void execute(Context context) {
		if (reloadable.reload()) {
			context.getCommandSender().sendMessage(getColouredMessage(ColourScheme.Style.INFO, "success"));
		} else {
			context.getCommandSender().sendMessage(getColouredMessage(ColourScheme.Style.ERROR, "failed"));
		}
	}

}
