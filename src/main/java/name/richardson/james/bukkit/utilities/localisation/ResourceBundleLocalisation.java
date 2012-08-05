package name.richardson.james.bukkit.utilities.localisation;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ResourceBundleLocalisation extends AbstractLocalisation {
  
  private ResourceBundle[] bundles;
  
  public ResourceBundleLocalisation(ResourceBundle...bundles) {
    super();
    this.bundles = bundles;
  }

  public String getMessage(Object object, String key) {
    key = object.getClass().getSimpleName() + "." + key;
    for (ResourceBundle bundle : bundles) {
      if (bundle.containsKey(key)) {
        return bundle.getString(key);
      }
    }
    return key.toUpperCase();
  }

  public String getMessage(Object object, String key, Object... elements) {
    final MessageFormat formatter = new MessageFormat(this.getMessage(object, key));
    formatter.setLocale(this.getLocale());
    return formatter.format(elements);
  }
  
}
