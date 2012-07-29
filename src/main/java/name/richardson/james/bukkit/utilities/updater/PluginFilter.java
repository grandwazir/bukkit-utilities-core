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
