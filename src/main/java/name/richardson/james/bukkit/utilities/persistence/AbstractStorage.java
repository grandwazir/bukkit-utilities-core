/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * AbstractStorage.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.persistence;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.logging.Logger;
import name.richardson.james.bukkit.utilities.plugin.Plugin;

public abstract class AbstractStorage implements Storage {

  private final Localisation localisation;

  private final Logger logger;

  public AbstractStorage(final Plugin plugin) {
    this.localisation = plugin.getLocalisation();
    this.logger = plugin.getCustomLogger();
  }

  public Localisation getLocalisation() {
    return this.localisation;
  }

  public Logger getLogger() {
    return this.logger;
  }

}
