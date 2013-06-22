/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 OfflinePlayerArgumentTest.java is part of bukkit-utilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.command.argument;

import org.bukkit.Server;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class OfflinePlayerArgumentTest extends TestCase {

	private OfflinePlayerArgument argument;
	private Server server;

	@Before
	public void setUp()
	throws Exception {
		server = ServerFixture.getServer();
		argument = new OfflinePlayerArgument();
	}

	@Test
	public void testParseValue()
	throws Exception {
		this.testSetServer();
		this.argument.parseValue("grandwazir");
		Assert.assertTrue("Returned value is not the correct player!", argument.getValue().getName().contentEquals("grandwazir"));
	}

	@Test
	public void testSetServer()
	throws Exception {
		OfflinePlayerArgument.setServer(server);
	}

	@Test
	public void testGetServer()
	throws Exception {
		this.testSetServer();
		Assert.assertEquals(server, OfflinePlayerArgument.getServer());
	}

	@Test(expected = InvalidArgumentException.class)
	public void testIsRequired()
	throws Exception {
		this.testSetRequired();
		this.testParseValue();
	}

	@Test
	public void testSetRequired()
	throws Exception {
		argument.setRequired(true);
	}

	@Test
	public void testMatches()
	throws Exception {
		this.testSetServer();
		Assert.assertTrue("List does not contain grandwazir!", argument.getMatches("gra").contains("grandwazir"));
		Assert.assertTrue("List does not contain Sergeant_Subtle!", argument.getMatches("sergeant_S").contains("Sergeant_Subtle"));
		Assert.assertTrue("List is not complete!", argument.getMatches("").size() == 2);
	}

}
