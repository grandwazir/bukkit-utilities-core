package name.richardson.james.bukkit.utilities.colours;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoreColourSchemeTest extends TestCase {

    private CoreColourScheme colourScheme;

    @Before
    public void setUp() throws Exception {
        this.colourScheme = new CoreColourScheme();
    }

    @Test
    public void testFormat() throws Exception {
        for (ColourScheme.Style style : ColourScheme.Style.values()) {
            String message = this.colourScheme.format(style, "Hello!");
            Assert.assertTrue("String does not contain a colour", message.contains("§"));
        }
        Pattern p = Pattern.compile("(§.{1}).*(§.{1}).*(§.{1})");
        for (ColourScheme.Style style : ColourScheme.Style.values()) {
            String message = this.colourScheme.format(style, "Hello {0}!", "grandwazir");
            Matcher matcher = p.matcher(message);
            matcher.find();
            Assert.assertTrue("String does not contain three colour types: " + message, matcher.groupCount() == 3);
            Assert.assertFalse("Arguments are not being coloured", matcher.group(2).contentEquals(matcher.group(1)));
        }
    }

}
