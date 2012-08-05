package name.richardson.james.bukkit.utilities.logging;

public abstract class AbstractLogger implements Logger {

  private String prefix;

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
  
  public String getPrefix() {
    return this.prefix;
  }
  
}
