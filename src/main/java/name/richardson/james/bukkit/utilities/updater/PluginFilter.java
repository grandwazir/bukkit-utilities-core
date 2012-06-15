package name.richardson.james.bukkit.utilities.updater;

import java.io.File;
import java.io.FilenameFilter;

import name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin;

public class PluginFilter implements FilenameFilter {

  private final String name;
  
  public PluginFilter(SkeletonPlugin plugin) {
    name = plugin.getName();
  }
  
  public boolean accept(File directory, String name) {
    File file = new File(directory.getPath() + File.separatorChar + name);
    if (file.isDirectory()) return false;
    if (name.contains(this.name)) return true;
    if (name.contains(this.name.toLowerCase())) return true;
    return false;
  }

  
}
