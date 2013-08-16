package name.richardson.james.bukkit.utilities.formatters;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleLocalisation;

@RunWith(JUnit4.class)
public class ResourceBundleLocalisationTest extends TestCase {

	private Localisation localisation;

	@Test
	public final void getMessage_LookupValidMessage_ReturnMessageWithArguments()
	throws Exception {
		Assert.assertEquals(getLocalisation().getMessage("test-message-with-arguments", "James"), "Hello James!");
	}

	@Test
	public final void getMessage_LookupValidMessage_ReturnTranslatedMessage()
	throws Exception {
		Assert.assertEquals(getLocalisation().getMessage("test-message"), "Hello!");
	}

	@Before
	public void setUp()
	throws Exception {
		setLocalisation(new ResourceBundleLocalisation("ResourceBundleLocalisationTest"));
	}

	protected final Localisation getLocalisation() {
		return localisation;
	}

	protected final void setLocalisation(Localisation localisation) {
		this.localisation = localisation;
	}
}
