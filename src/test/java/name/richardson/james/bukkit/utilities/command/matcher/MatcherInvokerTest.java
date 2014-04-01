/*
 * Copyright (c) 2013 James Richardson.
 *
 * SimpleMatcherInvokerTest.java is part of BukkitUtilities.
 *
 * bukkit-utilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * bukkit-utilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * bukkit-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.command.matcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public abstract class MatcherInvokerTest extends TestCase {

	private static final Set<String> RETURNED_ARGUMENTS = new HashSet<String>(Arrays.asList("a", "b"));
	private static final String PROVIDED_ARGUMENTS = "test";
	private MatcherInvoker invoker;

	public static CommandContext getMockCommandContext() {
		CommandContext commandContext = mock(CommandContext.class);
		when(commandContext.hasArgument(0)).thenReturn(true);
		when(commandContext.getArgument(anyInt())).thenReturn(PROVIDED_ARGUMENTS);
		when(commandContext.size()).thenReturn(1);
		return commandContext;
	}

	public static Matcher getMockMatcher() {
		Matcher matcher = mock(Matcher.class);
		when(matcher.matches(anyString())).thenReturn(RETURNED_ARGUMENTS);
		return matcher;
	}

	@Test
	public void addMatcherAndVerify() {
		Matcher matcher = getMockMatcher();
		getInvoker().addMatcher(matcher);
	}

	@Test
	public void getArgumentMatchesWhenMatcherPresent() {
		invoker.addMatcher(getMockMatcher());
		Set<String> matches = invoker.getArgumentMatches(getMockCommandContext());
		assertEquals(RETURNED_ARGUMENTS, matches);
	}

	@Test
	public void getEmptyCollectionWhenNoMatcherPresentForArgumentIndex() {
		invoker.addMatcher(getMockMatcher());
		CommandContext commandContext = getMockCommandContext();
		when(commandContext.hasArgument(1)).thenReturn(true);
		when(commandContext.size()).thenReturn(2);
		Set<String> matches = invoker.getArgumentMatches(commandContext);
		assertEquals(Collections.<String>emptySet(), matches);
	}

	@Test
	public void getEmptyCollectionWhenNoMatchersPresent() {
		Set<String> matches = invoker.getArgumentMatches(getMockCommandContext());
		assertEquals(Collections.<String>emptySet(), matches);
	}

	public MatcherInvoker getInvoker() {
		return invoker;
	}

	public void setInvoker(MatcherInvoker invoker) {
		this.invoker = invoker;
	}

	@Test
	public void checkToStringOverriden() {
		assertTrue("toString has not been overridden", invoker.toString().contains("MatcherInvoker"));
	}

}
