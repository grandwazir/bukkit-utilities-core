package name.richardson.james.bukkit.utilities.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.PluginDescriptionFile;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class CommandManagerTest extends TestCase {

	private org.bukkit.command.Command command;
	private HelpCommand helpCommand;
	private CommandManager manager;
	private CommandSender sender;

	@Test
	public void testOnTabComplete()
	throws Exception {
		List<String> matches = new ArrayList<String>(Arrays.asList("1", "2", "3"));
		EasyMock.expect(helpCommand.onTabComplete((List<String>) EasyMock.anyObject(), (CommandSender) EasyMock.anyObject())).andReturn(matches).atLeastOnce();
		EasyMock.expect(helpCommand.getName()).andReturn("help").atLeastOnce();
		EasyMock.replay(helpCommand);
		String[] args = {""};
		// Assert.assertTrue(manager.onTabComplete(sender, command, "label", args).size() == 0);
		manager.addCommand(helpCommand);
		Assert.assertTrue(manager.onTabComplete(sender, command, "label", new String[] {"hel"}).size() == 1);
	}

	@Test
	public void testOnCommand()
	throws Exception {
		EasyMock.expect(helpCommand.getName()).andReturn("help").atLeastOnce();
		EasyMock.replay(helpCommand);
		String[] args = {""};
		Assert.assertTrue(manager.onCommand(sender, command, "label", args));
	}

	@Test
	public void testAddCommand()
	throws Exception {
		EasyMock.expect(helpCommand.getName()).andReturn("help").atLeastOnce();
		EasyMock.replay(helpCommand);
		manager.addCommand(helpCommand);
		EasyMock.verify(helpCommand);
	}

	@Before
	public void setUp()
	throws Exception {
		command = EasyMock.createNiceMock(org.bukkit.command.Command.class);
		helpCommand = EasyMock.createNiceMock(HelpCommand.class);
		sender = EasyMock.createNiceMock(CommandSender.class);
		manager = new CommandManager(helpCommand);
	}
}
