package name.richardson.james.bukkit.utilities.localisation;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;

public class ResourceBundleLocalisation extends AbstractLocalisation {
  
  private ResourceBundle[] bundles;
  
  public ResourceBundleLocalisation(ResourceBundle...bundles) {
    super();
    this.bundles = bundles;
  }

  public String getMessage(Object object, String key) {
    return ColourFormatter.replace(this.getRawMessage(object, key));
  }

  public String getMessage(Object object, String key, Object... elements) {
    final MessageFormat formatter = new MessageFormat(this.getMessage(object, key));
    formatter.setLocale(this.getLocale());
    final String message = formatter.format(elements);
    return ColourFormatter.replace(message);
  }
  
  private String getRawMessage(Object object, String key) {
    key = this.getClassName(object) + "." + key;
    for (ResourceBundle bundle : bundles) {
      if (bundle.containsKey(key)) {
        return bundle.getString(key);
      }
    }
    return key.toUpperCase();
  }
  
  private String getClassName(Object object) {
    if (object instanceof Class) {
      Class<?> c = (Class<?>) object;
      return c.getSimpleName().toLowerCase();
    } else {
      return object.getClass().getSimpleName().toLowerCase();
    }
  }
  
}
