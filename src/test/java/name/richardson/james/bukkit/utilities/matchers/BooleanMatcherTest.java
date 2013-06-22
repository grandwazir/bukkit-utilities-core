package name.richardson.james.bukkit.utilities.matchers;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class BooleanMatcherTest extends TestCase {

    private BooleanMatcher matcher;

    @Before
    public void setUp() throws Exception {
        this.matcher = new BooleanMatcher();
    }

    @Test
    public void testGetMatches() throws Exception {
        List<String> matches = matcher.getMatches("tru");
        Assert.assertTrue("List does not contain 'true'", matches.contains("true"));
        Assert.assertTrue(matches.size() == 1);
    }

    @Test
    public void testGetBlankMatches() throws Exception {
        List<String> matches = matcher.getMatches("");
        Assert.assertTrue("List does not contain 'true'", matches.contains("true"));
        Assert.assertTrue("List does not contain 'false'", matches.contains("false"));
        Assert.assertTrue(matches.size() == 2);
    }

    @Test
    public void testCaseInsensitiveMatch() throws Exception {
        List<String> matches = matcher.getMatches("FAL");
        Assert.assertTrue("List does not contain 'false'", matches.contains("false"));
        Assert.assertTrue(matches.size() == 1);
    }
}
