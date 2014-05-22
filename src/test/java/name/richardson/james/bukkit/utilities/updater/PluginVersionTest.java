package name.richardson.james.bukkit.utilities.updater;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PluginVersionTest {

	private Version version;

	@Test
	public void correctlyParsePatchVersion()
	throws Exception {
		Assert.assertEquals("Version has not been parsed correctly!", 1, version.getPatchVersion());
	}

	@Test
	public void correctlyParseMinorVersion()
	throws Exception {
		Assert.assertEquals("Version has not been parsed correctly!", 0, version.getMinorVersion());
	}

	@Test
	public void correctlyParseMajorVersion()
	throws Exception {
		Assert.assertEquals("Version has not been parsed correctly!", 2, version.getMajorVersion());
	}

	@Before
	public void setUp()
	throws Exception {
		version = new PluginVersion("2.0.1");
	}
}
