package name.richardson.james.bukkit.utilities.formatters;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;

@RunWith(JUnit4.class)
public class ResourceBundleByClassLocalisationTest extends ResourceBundleLocalisationTest {

	@Before
	public void setUp()
	throws Exception {
		this.setLocalisation(new ResourceBundleByClassLocalisation(ResourceBundleByClassLocalisationTest.class));
	}

}
