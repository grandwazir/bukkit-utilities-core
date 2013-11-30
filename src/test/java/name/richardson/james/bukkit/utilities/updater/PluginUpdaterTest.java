package name.richardson.james.bukkit.utilities.updater;

import org.bukkit.plugin.PluginDescriptionFile;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public abstract class PluginUpdaterTest extends TestCase {

	private PluginUpdater updater;

	@Test
	public void returnsSuppliedBranch()
	throws Exception {
		assertEquals("Branch has not been set correctly!", PluginUpdater.Branch.STABLE, getUpdater().getBranch());
	}

	@Test
	public void returnsSuppliedLocalVersion()
	throws Exception {
		assertEquals("Version has not been set correctly!", "1.0.0", getUpdater().getLocalVersion());
	}

	@Test
	public void returnsSuppliedPluginName()
	throws Exception {
		assertEquals("Name has not been set correctly!", "Test", getUpdater().getName());
	}

	@Test
	public void returnsSuppliedState()
	throws Exception {
		assertEquals("State has not been set correctly!", PluginUpdater.State.NOTIFY, getUpdater().getState());
	}

	protected PluginUpdater getUpdater() {
		return updater;
	}

	protected void setUpdater(PluginUpdater updater) {
		this.updater = updater;
	}

}
