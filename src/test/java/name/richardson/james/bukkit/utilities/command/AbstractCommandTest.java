package name.richardson.james.bukkit.utilities.command;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class AbstractCommandTest extends TestCase {

	private TestCommand command;
	private CommandSender sender;

	@Before
	public void setUp() {
		command = new TestCommand();
		sender = EasyMock.createNiceMock(CommandSender.class);
	}

	@Test
	public void testGetDescription()
	throws Exception {
		Assert.assertTrue(command.getDescription().contentEquals("test command"));
	}

	@Test
	public void testGetName()
	throws Exception {
		Assert.assertTrue(command.getName().contentEquals("test"));
	}

	@Test
	public void testGetPermissionManager()
	throws Exception {
		Assert.assertNull(command.getPermissionManager());
	}

	@Test
	public void testGetResourceBundle()
	throws Exception {
		Assert.assertNotNull(command.getResourceBundle());
	}

	@Test
	public void testGetUsage()
	throws Exception {
		Assert.assertTrue(command.getUsage().contentEquals(""));
	}

	@Test
	public void testIsAuthorized()
	throws Exception {
	 	Assert.assertTrue(command.isAuthorized(sender));
	}

	@Test
	public void testOnTabComplete()
	throws Exception {
		Assert.assertEquals(command.onTabComplete(Collections.EMPTY_LIST), Collections.emptyList());
	}

	public class TestCommand extends AbstractCommand {


	}

}
