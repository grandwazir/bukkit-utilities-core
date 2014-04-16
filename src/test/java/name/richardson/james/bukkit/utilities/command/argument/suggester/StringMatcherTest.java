package name.richardson.james.bukkit.utilities.command.argument.suggester;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class StringMatcherTest extends TestCase {

	private StringSuggester matcher;

	@Test
	public void matches_WhenExecuted_ReturnNoMatches() {
		Set<String> results = matcher.suggestValue("fr");
		assertTrue("A match should not have been returned!", results.size() == 0);
	}

	@Test
	public void matches_WhenExecuted_ReturnMatches() {
		Set<String> results = matcher.suggestValue("kiC");
		assertTrue("A match should have been returned!", results.contains("kick"));
		assertTrue("Two matches should have been returned!", results.size() == 2);
	}

	@Test
	public void checkToStringOverriden() {
		assertTrue("toString has not been overridden", matcher.toString().contains("StringSuggester"));
	}

	@Before
	public void setUp()
	throws Exception {
		List<String> commandNames = Arrays.asList("ban", "kick", "remove", "kicked");
		this.matcher = new StringSuggester(commandNames);
	}

}