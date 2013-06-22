package name.richardson.james.bukkit.utilities.localisation;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ResourceBundle;


public class PluginResourceBundleTest extends TestCase {

    @Test
    public void testGetBundle() throws Exception {
        ResourceBundle bundle = PluginResourceBundle.getBundle(this);
        Assert.assertNotNull(bundle);
    }

    @Test
    public void testGetBundleName() throws Exception {
        String bundleName = PluginResourceBundle.getBundleName(this);
        Assert.assertTrue("ResourceBundle name is inconsistent: " + bundleName, bundleName.contentEquals("localisation.localisation.PluginResourceBundleTest"));
    }

    @Test
    public void testExists() throws Exception {
        Assert.assertTrue(PluginResourceBundleTest.class.getName() + " should exist!", PluginResourceBundle.exists(PluginResourceBundleTest.class));
    }

    @Test
    public void testDoesNotExists() throws Exception {
        Assert.assertFalse(PluginResourceBundle.class.getName() + " should not exist!", PluginResourceBundle.exists(PluginResourceBundle.class));
    }
}
