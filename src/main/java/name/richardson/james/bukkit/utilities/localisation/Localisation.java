package name.richardson.james.bukkit.utilities.localisation;

import java.util.Locale;

import name.richardson.james.bukkit.utilities.formatters.ChoiceFormatter;

public interface Localisation {
  
  public Locale getLocale();
  
  public String getMessage(Object object, String key);
  
  public String getMessage(Object object, String key, Object...elements); 
  
  public ChoiceFormatter getChoiceFormatter();

}
