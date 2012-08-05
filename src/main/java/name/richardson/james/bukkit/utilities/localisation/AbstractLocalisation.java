package name.richardson.james.bukkit.utilities.localisation;

import java.util.Locale;

import name.richardson.james.bukkit.utilities.formatters.ChoiceFormatter;

public abstract class AbstractLocalisation implements Localisation {
  
  private Locale locale;

  public AbstractLocalisation() {
    this.locale = Locale.getDefault();
  }
  
  public AbstractLocalisation(Locale locale) {
    this.locale = locale;
  }
  
  public Locale getLocale() {
    return this.locale;
  }
  
  public ChoiceFormatter getChoiceFormatter() {
    return new ChoiceFormatter(this);
  }


}
