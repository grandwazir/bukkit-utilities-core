/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * Debuggable.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.plugin;

/**
 * The Debuggable interface represents a class which can output debugging
 * information and provide additional information to the server log when
 * required.
 */
public interface Debuggable {

  /**
   * Checks if this class is currently debugging.
   * 
   * @return true, if it is debugging
   */
  public boolean isDebugging();

  /**
   * Sets if this class is debugging.
   * 
   * @param value
   */
  public void setDebugging(boolean value);

}
