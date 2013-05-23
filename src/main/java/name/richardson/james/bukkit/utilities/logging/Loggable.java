package name.richardson.james.bukkit.utilities.logging;

public interface Loggable {

  /**
   * Record a informative message to the log.
   * 
   * @param message
   */
  public void info(String message);

  /**
   * Record a error to the log.
   *
   * @param message
   */
  public void severe(String message);

  /**
   * Record a warning to the log.
   * 
   * @param message
   */
  public void warning(String message);
  
  

}
