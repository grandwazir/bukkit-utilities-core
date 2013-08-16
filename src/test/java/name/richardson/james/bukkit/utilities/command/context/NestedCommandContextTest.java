/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 NestedCommandContextTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.command.context;

import junit.framework.TestCase;
import org.junit.Before;

public class NestedCommandContextTest extends AbstractCommandContextTest {

	@Override
	public void getJoinedArguments_WhenMixedContextPassed_ReturnedStringOnlyIncludesArguments() {
		assertEquals("is a test argument", getCommandContext().getJoinedArguments(0));
	}

	@Before
	public void setUp()
	throws Exception {
		CommandContext commandContext = new NestedCommandContext(getArguments(), getCommandSender());
		setCommandContext(commandContext);
	}

	@Override
	public void size_WhenArgumentsPassed_ReturnCorrectSize() {
		assertEquals(4, getCommandContext().size());
	}
}
