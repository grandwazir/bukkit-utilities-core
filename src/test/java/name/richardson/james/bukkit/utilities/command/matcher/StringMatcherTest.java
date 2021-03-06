package name.richardson.james.bukkit.utilities.command.matcher;

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

	private StringMatcher matcher;

	@Test
	public void matches_WhenExecuted_ReturnNoMatches() {
		Set<String> results = matcher.matches("fr");
		assertTrue("A match should not have been returned!", results.size() == 0);
	}

	@Test
	public void matches_WhenExecuted_ReturnMatches() {
		Set<String> results = matcher.matches("kiC");
		assertTrue("A match should have been returned!", results.contains("kick"));
		assertTrue("Two matches should have been returned!", results.size() == 2);
	}

	@Before
	public void setUp()
	throws Exception {
		List<String> commandNames = Arrays.asList("ban", "kick", "remove", "kicked");
		this.matcher = new StringMatcher(commandNames);
	}

}