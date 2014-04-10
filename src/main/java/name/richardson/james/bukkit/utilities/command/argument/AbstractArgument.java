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

import java.util.Set;

import name.richardson.james.bukkit.utilities.command.matcher.Matcher;

public abstract class AbstractArgument implements Argument {

	private final String desc;
	private final String name;
	private final Class<?> type;
	private Matcher matcher;
	private String value;

	public AbstractArgument(String name, String desc, Class<?> type) {
		this.name = name;
		this.desc = desc;
		this.type = type;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("AbstractArgument{");
		sb.append("desc='").append(desc).append('\'');
		sb.append(", matcher=").append(matcher);
		sb.append(", name='").append(name).append('\'');
		sb.append(", type=").append(type);
		sb.append(", value='").append(value).append('\'');
		sb.append('}');
		return sb.toString();
	}

	@Override
	public String getValue() {
		return this.value;
	}

	protected void setValue(final String value) {
		this.value = value;
	}

	@Override
	public Object getType() {
		return type;
	}

	@Override
	public Set<String> getMatches(String argument) {
		return this.matcher.matches(argument);
	}

	@Override
	public void setMatcher(final Matcher matcher) {
		this.matcher = matcher;
	}

}
