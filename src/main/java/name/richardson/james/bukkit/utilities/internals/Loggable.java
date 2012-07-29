package name.richardson.james.bukkit.utilities.internals;

public interface Loggable {

  public Logger getLogger();

  public String getLoggerPrefix();

  public boolean isDebugging();

  public void setDebugging();

  public void setLoggerPrefix();

}
