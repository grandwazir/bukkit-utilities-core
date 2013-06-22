/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 LocalisedCommandSender.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.localisation;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import org.bukkit.command.CommandSender;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class LocalisedCommandSender {

    private final CommandSender sender;
    private final ResourceBundle bundle;

    public LocalisedCommandSender(final CommandSender sender, final ResourceBundle bundle) {
        this.bundle = bundle;
        this.sender = sender;
    }

    public

}
