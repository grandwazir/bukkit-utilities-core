/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * Logger.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.logging;

import java.util.logging.Level;

public interface Logger {

  public final static Level DEBUG_LEVEL = Level.ALL;

  public final static Level DEFAULT_LEVEL = Level.INFO;

  public void config(Object object, String message, Object... elements);

  public void debug(Object object, String message, Object... elements);

  public String getName();

  public String getPrefix();

  public void info(Object object, String message, Object... elements);

  public boolean isDebugging();

  public void setDebugging(boolean value);

  public void setPrefix(String prefix);

  public void severe(Object object, String message, Object... elements);

  public void warning(Object object, String message, Object... elements);

}
