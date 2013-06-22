package name.richardson.james.bukkit.utilities.colours;

import junit.framework.Assert;
import name.richardson.james.bukkit.utilities.localisation.LocalisedCoreColourScheme;
import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;
import org.junit.Before;
import org.junit.Test;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalisedCoreColourSchemeTest extends CoreColourSchemeTest {

    private LocalisedCoreColourScheme colourScheme;

    @Before
    public void setUp() throws Exception {
        ResourceBundle bundle = PluginResourceBundle.getBundle(this);
        this.colourScheme = new LocalisedCoreColourScheme(bundle);
    }

    @Test
    public void testFormat() throws Exception {
        for (ColourScheme.Style style : ColourScheme.Style.values()) {
            String message = this.colourScheme.format(style, "test-without-arguments");
            Assert.assertTrue("String does not contain a colour", message.contains("§"));
            Assert.assertTrue("String not translated:" + message, message.replaceAll("§.{1}", "").contentEquals("Hello!"));
        }
        Pattern p = Pattern.compile("(§.{1}).*(§.{1}).*(§.{1})");
        for (ColourScheme.Style style : ColourScheme.Style.values()) {
            String message = this.colourScheme.format(style, "test-with-arguments", "grandwazir");
            Matcher matcher = p.matcher(message);
            matcher.find();
            Assert.assertTrue("String does not contain three colour types: " + message, matcher.groupCount() == 3);
            Assert.assertFalse("Arguments are not being coloured", matcher.group(2).contentEquals(matcher.group(1)));
            Assert.assertTrue("String not translated:" + message, message.replaceAll("§.{1}", "").contentEquals("Hello grandwazir!"));
        }
    }

    @Test
    public void testGetResourceBundle() throws Exception {
        Assert.assertNotNull(this.colourScheme.getResourceBundle());
    }

    @Test
    public void testGetMessage() {
        Assert.assertEquals(this.colourScheme.getMessage("test-without-arguments"), "Hello!");
    }
}
