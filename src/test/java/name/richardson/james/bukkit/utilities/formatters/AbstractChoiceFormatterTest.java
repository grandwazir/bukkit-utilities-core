package name.richardson.james.bukkit.utilities.formatters;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AbstractChoiceFormatterTest extends TestCase {

	private AbstractChoiceFormatterTestClass formatter;

	private class AbstractChoiceFormatterTestClass extends AbstractChoiceFormatter {

	}

	@Test
	public void getMessage_WhenSetupCorrectly_ProvideFormattedMessage()
	throws Exception {
		formatter.setLimits(0,1,2);
		formatter.setFormats("zero", "one", "many");
		formatter.setArguments(2);
		formatter.setMessage("We have {0}.");
		assertEquals("We have 2 bananas.", formatter.getMessage());
	}

	@Before
	public void setUp()
	throws Exception {
		this.formatter = new AbstractChoiceFormatterTestClass();
	}
}
