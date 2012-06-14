package name.richardson.james.bukkit.utilities.updater;

import java.net.URL;
import java.util.List;

public interface Updatable {
  
  /**
   * Gets the group id of the Maven artifact.
   *
   * @return the group id
   */
  public String getGroupID();
  
  /**
   * Gets the artifact id of the Maven project.
   *
   * @return the artifact id
   */
  public String getArtifactID();
  
  /**
   * Gets the url of the repository that contains this plugin.
   *
   * @return the repository url
   */
  public URL getRepositoryURL();
  
  
}
