package name.richardson.james.bukkit.utilities.matchers;

import junit.framework.TestCase;
import name.richardson.james.bukkit.utilities.command.Command;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class CommandMatcherTest extends TestCase {

    private static final String[] COMMAND_NAMES = {"help", "check", "test"};
    private final Map<String, Command> commands = new HashMap<String, Command>();

    @Before
    public void setUp() throws Exception {
        this.commands.clear();
        for (String name : COMMAND_NAMES) {
            Command command = EasyMock.createNiceMock(Command.class);
            EasyMock.expect(command.getName()).andStubReturn(name);
            EasyMock.replay(command);
            this.commands.put(name, command);
        }
    }

    @Test
    public void testGetMatches() throws Exception {
        CommandMatcher matcher = new CommandMatcher(commands);
        List<String> matches = matcher.getMatches("hel");
        Assert.assertTrue("List does not contain 'help'", matches.contains("help"));
        Assert.assertTrue(matches.size() == 1);
    }

    @Test
    public void testGetBlankMatches() throws Exception {
        CommandMatcher matcher = new CommandMatcher(commands);
        List<String> matches = matcher.getMatches("");
        Assert.assertTrue("List does not contain 'help'", matches.contains("help"));
        Assert.assertTrue("List does not contain 'check'", matches.contains("check"));
        Assert.assertTrue("List does not contain 'test'", matches.contains("test"));
        Assert.assertTrue(matches.size() == 3);
    }

    public void testCaseInsensitiveMatch() throws Exception {
        CommandMatcher matcher = new CommandMatcher(commands);
        List<String> matches = matcher.getMatches("HEL");
        Assert.assertTrue("List does not contain 'help'", matches.contains("help"));
        Assert.assertTrue(matches.size() == 1);
    }

    public void testAlphabeticalOrder() {
        CommandMatcher matcher = new CommandMatcher(commands);
        List<String> matches = matcher.getMatches("");
        Assert.assertTrue("First element is not 'check'", matches.get(0).equalsIgnoreCase("check"));
    }


    @Test(expected=IllegalArgumentException.class)
    public void testCommandsMustNotBeNull() throws Exception {
        new CommandMatcher(null);
    }
}
