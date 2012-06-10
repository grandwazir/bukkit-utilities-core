package name.richardson.james.bukkit.utilities.updater;

import java.net.URL;
import java.util.List;

public interface Updatable {

  /**
   * Update the plugin if required. 
   *
   * @param version the version to update to. If null will attempt to update to the latest version.
   * @return true, if successful
   */
  public boolean update(String version);
  
  /**
   * Gets a list containing all the versions managed by Maven ordered by release date in ascending order.
   *
   * @return the version list
   */
  public List<String> getVersionList();
  
  /**
   * Checks if is new version available.
   *
   * @return true, if is new version available
   */
  public boolean isNewVersionAvailable();
  
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
