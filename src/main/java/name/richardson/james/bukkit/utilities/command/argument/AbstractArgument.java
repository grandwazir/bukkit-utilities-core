/*
 * Copyright (c) 2014 James Richardson.
 *
 * AbstractArgument.java is part of BukkitUtilities.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;

public abstract class AbstractArgument implements Argument, ArgumentMetadata {

	private final ArgumentMetadata metadata;
	private Suggester suggester;
	private String value;
	private Collection<String> values = new ArrayList<String>();

	public AbstractArgument(final ArgumentMetadata metadata, Suggester suggester) {
		this.metadata = metadata;
		this.suggester = suggester;
		if (suggester != null) System.out.print(suggester.toString());
	}

	protected final String[] getSeparatedValues(final String argument) {
		return StringUtils.split(argument, ",");
	}

	public final String getId() {
		return metadata.getId();
	}

	@Override
	public final String getName() {
		return metadata.getName();
	}

	public void parseValue(final String argument) {
		setValue(null);
		String[] match = getMatch(argument);
		if (match != null) setValues(match);
	}

	public final String getError() {
		return metadata.getError();
	}

	@Override
	public final String getDescription() {
		return metadata.getDescription();
	}

	@Override
	public String getString() {
		String value = null;
		Iterator<String> iterator = values.iterator();
		if (iterator.hasNext()) value = iterator.next();
		return value;
	}

	public Collection<String> getStrings() {
		return Collections.unmodifiableCollection(values);
	}

	protected abstract String[] getMatch(String argument);

	protected final Suggester getSuggester() {
		return suggester;
	}

	protected final void setValue(final String value) {
		this.values.clear();
		this.values.add(value);
	}

	protected final void setValues(final String[] values) {
		this.values.clear();
		for (String value : values) {
			this.values.add(value);
		}
	}


}
