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
