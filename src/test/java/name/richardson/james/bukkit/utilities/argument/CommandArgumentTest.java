package name.richardson.james.bukkit.utilities.argument;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class CommandArgumentTest extends TestCase {

    private CommandArgument argument;

    @Before
    public void setUp() throws Exception {
        argument = new CommandArgument();
    }

    @Test(expected = InvalidArgumentException.class)
    public void testGetValue() throws Exception {
        this.testSetCommands();
        argument.parseValue("check");
        Assert.assertTrue(argument.getValue().equals("check"));
        argument.setRequired(true);
        argument.parseValue("che");
        argument.getValue();
    }

    @Test
    public void testSetCommands() throws Exception {
        Set<String> commands = new TreeSet<String>(Arrays.asList("check", "ban", "fred"));
        CommandArgument.setCommands(commands);
    }

    @Test
    public void testGetMatches() throws Exception {
        this.testSetCommands();
        junit.framework.Assert.assertTrue("List does not contain check!", argument.getMatches("check").contains("check"));
        junit.framework.Assert.assertTrue("List does not contain ban!", argument.getMatches("BaN").contains("ban"));
        junit.framework.Assert.assertTrue("List is not complete!", argument.getMatches("").size() == 3);
    }

}
