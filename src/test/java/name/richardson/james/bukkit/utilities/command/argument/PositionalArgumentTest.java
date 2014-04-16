package name.richardson.james.bukkit.utilities.command.argument;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositionalArgumentTest {

	private static final int POSITION = 1;

	private Argument argument;

	@Test
	public void shouldBeTheLastArgument() {
		assertTrue("Argument is the last one supplied and so should be true.", argument.isLastArgument("one two"));
	}

	@Test
	public void shouldNotBeTheLastArgument() {
		assertFalse("Argument is not the last one supplied and so should be false.", argument.isLastArgument("one"));
	}

	@Test
	public void shouldBeNullWhenArgumentIsNotPresent() {
		argument.parseValue("");
		assertNull("Returned value should be null when argument not present.", argument.getString());
	}

	@Test
	public void shouldBeNullWhenArgumentIsPresentButBlank() {
		argument.parseValue("one ");
		assertNull("Returned value should be null when argument is present but without data.", argument.getString());
	}

	@Test
	public void shouldRemoveExtraArgumentsCorrectly() {
		argument.parseValue("-p one -s argument -p:frank");
		assertTrue("Returned value should be 'argument' when other arguments have been stripped.", argument.getString().equals("argument"));
	}

	@Test
	public void shouldReturnAllDataWhenArgumentIsPresentWithData() {
		final String name = "frank";
		argument.parseValue("one two,three,four five");
		assertTrue("Returned value should equal passed data when argument is present.", argument.getStrings().contains("two"));
		assertTrue("Returned value should equal passed data when argument is present.", argument.getStrings().contains("three"));
		assertTrue("Returned value should equal passed data when argument is present.", argument.getStrings().contains("four"));
	}

	@Before
	public void setup() {
		ArgumentMetadata metadata = mock(ArgumentMetadata.class);
		argument = new PositionalArgument(metadata, null, POSITION);
	}

}
