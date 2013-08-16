package name.richardson.james.bukkit.utilities.updater;

import org.bukkit.plugin.PluginDescriptionFile;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA. User: james Date: 13/07/13 Time: 15:48 To change this template use File | Settings | File Templates.
 */
public class AbstractPluginUpdaterTest extends TestCase {

	private AbstractPluginUpdaterTestClass updater;

	@Before
	public void setUp()
	throws Exception {
		PluginDescriptionFile pluginDescriptionFile = new PluginDescriptionFile("Test", "1.0.0", null);
		updater =  new AbstractPluginUpdaterTestClass(pluginDescriptionFile, PluginUpdater.Branch.STABLE, PluginUpdater.State.NOTIFY);
	}

	@Test
	public void testGetBranch()
	throws Exception {
		assertEquals("Branch has not been set correctly!", PluginUpdater.Branch.STABLE, updater.getBranch());
	}

	@Test
	public void testGetLocalVersion()
	throws Exception {
		assertEquals("Version has not been set correctly!", "1.0.0", updater.getLocalVersion());
	}

	@Test
	public void testGetName()
	throws Exception {
		assertEquals("Name has not been set correctly!", "Test", updater.getName());
	}

	@Test
	public void testGetState()
	throws Exception {
		assertEquals("State has not been set correctly!", PluginUpdater.State.NOTIFY, updater.getState());
	}

	public class AbstractPluginUpdaterTestClass extends AbstractPluginUpdater {

		public AbstractPluginUpdaterTestClass(PluginDescriptionFile pluginDescriptionFile, Branch branch, State state) {
			super(pluginDescriptionFile, branch, state);
		}

		public String getRemoteVersion() {
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public boolean isNewVersionAvailable() {
			return false;  //To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public void run() {
			//To change body of implemented methods use File | Settings | File Templates.
		}
	}

}
