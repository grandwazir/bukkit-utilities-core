package name.richardson.james.bukkit.utilities.command.argument;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;


public class SentenceArgumentTest extends TestCase {

    private SentenceArgument argument;

    @Before
    public void setUp() throws Exception {
        argument = new SentenceArgument();
    }

    @Test
    public void testGetValue() throws Exception {
        argument.parseValue("this is a sentence");
        String sentence = argument.getValue();
        Assert.assertTrue("Sentence does not start with captial letter!: " + sentence, sentence.startsWith("T"));
        Assert.assertTrue("Sentence does not end with a full stop!: " + sentence , sentence.endsWith("."));
    }
}
