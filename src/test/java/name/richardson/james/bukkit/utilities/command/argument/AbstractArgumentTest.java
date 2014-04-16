/*
 * Copyright (c) 2014 James Richardson.
 *
 * AbstractOptionArgumentTest.java is part of BukkitUtilities.
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

package name.richardson.james.bukkit.utilities.command.argument;

import org.junit.Assert;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;

import static org.junit.Assert.assertEquals;

public abstract class AbstractArgumentTest {

	private Argument argument;

	@Test
	public void descriptionShouldMatchConstructorValue() {
		Assert.assertEquals("Description does not match value given in constructor!", getArgument().getDescription(), getCommandMetadata().getDescription());
	}

	protected ArgumentMetadata getCommandMetadata() {
		return new SimpleArgumentMetadata("p", "player", "the name of the player", "you must specify the name of the player!");
	}

	protected Argument getArgument() {
		return argument;
	}

	protected void setArgument(final Argument argument) {
		this.argument = argument;
	}

	@Test
	public void errorShouldMatchConstructorValue() {
		Assert.assertEquals("Id does not match value given in constructor!", getArgument().getError(), getCommandMetadata().getError());
	}

	@Test
	public void idShouldMatchConstructorValue() {
		Assert.assertEquals("Id does not match value given in constructor!", getArgument().getId(), getCommandMetadata().getId());
	}

	@Test
	public void nameShouldMatchConstructorValue() {
		Assert.assertEquals("Name does not match value given in constructor!", getArgument().getName(), getCommandMetadata().getName());
	}

	@Test
	public abstract void shouldParseArgumentWhenNonExistantCorrectly();

	@Test
	public abstract void shouldParseArgumentWithMultipleParametersCorrectly();

	@Test
	public abstract void shouldParseArgumentWithNoParametersCorrectly();

	@Test
	public abstract void shouldParseArgumentWithSingleParameterCorrectly();

	protected Suggester getSuggester() {
		return null;
	}

}
