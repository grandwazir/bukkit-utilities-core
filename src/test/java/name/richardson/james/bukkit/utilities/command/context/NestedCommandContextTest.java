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

import org.bukkit.command.CommandSender;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class NestedCommandContextTest extends AbstractCommandContextTest {

	@Override
	public void getJoinedArgumentsWhenMixedContextPassedReturnedStringOnlyIncludesArguments() {
		String joinedArguments = StringUtils.join(ARGUMENTS, " ", 1, ARGUMENTS.length - 2);
		assertEquals(joinedArguments, getCommandContext().getJoinedArguments(0));
	}

	@Override
	public void sizeWhenArgumentsPassedReturnCorrectSize() {
		assertEquals(ARGUMENTS.length - 3, getCommandContext().size());
	}

	@Before
	public void setUp()
	throws Exception {
		CommandContext commandContext = new NestedCommandContext(ARGUMENTS, mock(CommandSender.class));
		setCommandContext(commandContext);
	}
}
