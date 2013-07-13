package name.richardson.james.bukkit.utilities.plugin.updater;

import org.bukkit.plugin.PluginDescriptionFile;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA. User: james Date: 13/07/13 Time: 15:53 To change this template use File | Settings | File Templates.
 */
public class MavenPluginUpdaterTest extends TestCase {

	private PluginDescriptionFile pluginDescriptionFile;
	private MavenPluginUpdater updater;

	@Test
	public void testUpdater()
	throws Exception {
		updater =  new MavenPluginUpdater("bukkit-utilities", "name.richardson.james.bukkit", pluginDescriptionFile, PluginUpdater.Branch.STABLE, PluginUpdater.State.NOTIFY);
		updater.run();
		Assert.assertTrue("A new version should be available!", updater.isNewVersionAvailable());
		Assert.assertEquals("Updater has not returned the latest version!", "5.1.0", updater.getRemoteVersion());
	}

	@Test
	public void testUpdaterPolicyRestriction() {
		updater =  new MavenPluginUpdater("bukkit-utilities", "name.richardson.james.bukkit", pluginDescriptionFile, PluginUpdater.Branch.STABLE, PluginUpdater.State.UPDATE);
		updater.run();
	}

	@Test
	public void testUpdaterLinkFailure() {
		updater =  new MavenPluginUpdater("bukkit-utilities", "name.richardson.james.bukkit.joe", pluginDescriptionFile, PluginUpdater.Branch.STABLE, PluginUpdater.State.NOTIFY);
		updater.run();
	}

	@Test
	public void testUpdaterVersionUnavailable() {
		pluginDescriptionFile = new PluginDescriptionFile("Test", "99.0.0", null);
		updater =  new MavenPluginUpdater("bukkit-utilities", "name.richardson.james.bukkit", pluginDescriptionFile, PluginUpdater.Branch.STABLE, PluginUpdater.State.NOTIFY);
		updater.run();
	}

	@Before
	public void setUp()
	throws Exception {
		pluginDescriptionFile = new PluginDescriptionFile("Test", "1.0.0", null);
	}

}
