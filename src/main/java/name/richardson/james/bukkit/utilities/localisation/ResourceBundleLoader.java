package name.richardson.james.bukkit.utilities.localisation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourceBundleLoader {

  public static ResourceBundle getBundle(String name) {
    return ResourceBundle.getBundle(name + "-localisation");
  }
  
  public static ResourceBundle getBundle(String name, File dataFolder) {
    try {
      final String path = dataFolder.getAbsolutePath() + File.separator + "localisation.properties";
      final File customBundle = new File(path);
      if (customBundle.exists()) {
        final FileInputStream stream = new FileInputStream(customBundle);
        final PropertyResourceBundle bundle = new PropertyResourceBundle(stream);
        stream.close();
        return bundle;
      } else {
        return ResourceBundle.getBundle(name + "-localisation");
      }
    } catch (IOException exception) {
      exception.printStackTrace();
      return ResourceBundle.getBundle(name + "-localisation");
    }
  }
  
}
