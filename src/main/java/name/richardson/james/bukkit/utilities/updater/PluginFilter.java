/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * PluginFilter.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.updater;

import java.io.File;
import java.io.FilenameFilter;

import name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin;

public class PluginFilter implements FilenameFilter {

  private final String artifact;
  private final String name;

  public PluginFilter(final SkeletonPlugin plugin) {
    this.name = plugin.getName();
    this.artifact = plugin.getArtifactID();
  }

  public boolean accept(final File directory, final String name) {
    final File file = new File(directory.getPath() + File.separatorChar + name);
    if (file.isDirectory()) {
      return false;
    }
    if (name.contains(this.name)) {
      return true;
    }
    if (name.contains(this.name.toLowerCase())) {
      return true;
    }
    if (name.contains(this.artifact.toLowerCase())) {
      return true;
    }
    return false;
  }

}
