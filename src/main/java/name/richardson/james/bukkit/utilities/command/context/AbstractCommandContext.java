/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractCommandContext.java is part of bukkit-utilities.

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

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.logging.PluginLoggerFactory;

/**
 * An example implementation of {@link CommandContext}. This implementation makes no additional verification checks on requested arguments and may throw
 * IndexOutOfBoundExceptions from the internal backing storage.
 */
public class AbstractCommandContext implements CommandContext {

	private final Matcher argumentMatcher = Pattern.compile("(\\w+)").matcher("");
	private final Matcher flagWithoutArgumentsMatcher = Pattern.compile("-(\\w+)").matcher("");
	private final Matcher flagWithArgumentsMatcher = Pattern.compile("(\\w{1}):(\\w+)").matcher("");

	private final List<String> arguments = new ArrayList<String>();
	private final Map<String, String> flags = new HashMap<String, String>();
	private final Logger logger = PluginLoggerFactory.getLogger(AbstractCommandContext.class);
	private final CommandSender sender;

	/**
	 * Constructs a new AbstractCommandContext with the arguments and CommandSender.
	 *
	 * @param arguments provided arguments
	 * @param sender the CommandSender executing the command
	 */
	public AbstractCommandContext(String[] arguments, CommandSender sender) {
		Validate.notNull(arguments);
		Validate.notNull(sender);
		String argument = StringUtils.join(arguments, " ");
		this.sender = sender;
		setArguments(argument);
		setFlags(argument);
		if (logger.isLoggable(Level.FINEST)) logger.finest("A command context has been created: " + this.toString());
	}

	/**
	 * Get the CommandSender who called this command.
	 *
	 * @return the CommandSender
	 */
	@Override
	public CommandSender getCommandSender() {
		return sender;
	}

	/**
	 * Get the contents of the flag.
	 *
	 * @param label the flag label to look up.
	 * @return the contents of the flag.
	 */
	@Override
	public String getFlag(String label) {
		Validate.notNull(label);
		String flag = null;
		if (hasFlag(label)) {
			flag = flags.get(label);
		}
		return flag;
	}

	/**
	 * Join all the arguments from a specified index onwards into one String.
	 *
	 * @param initialIndex the index to start at at
	 * @return a String containing all the arguments seperated by ' '.
	 */
	@Override
	public String getJoinedArguments(int initialIndex) {
		Validate.notNull(initialIndex);
		Validate.isTrue(initialIndex < size(), "Initial index can not be greater than size!");
		return StringUtils.join(getArguments().subList(initialIndex, size()), " ");
	}

	/**
	 * Get the argument at the specified index.
	 *
	 * @param index the argument number to fetch.
	 * @return the argument specified.
	 */
	@Override
	public String getString(int index) {
		Validate.notNull(index);
		if (has(index)) {
			return arguments.get(index);
		} else {
			return null;
		}
	}

	/**
	 * Check to see if the context contains an argument.
	 *
	 * @param index the argument number to check
	 * @return true if the argument exists, false otherwise.
	 */
	@Override
	public boolean has(int index) {
		Validate.notNull(index);
		return size() > 0 && (index + 1) <= size();
	}

	/**
	 * Check to see if the context contains a flag.
	 *
	 * @param label the prefix of the flag to check
	 * @return true if the flag exists, false otherwise.
	 */
	@Override
	public boolean hasFlag(String label) {
		Validate.notNull(label);
		return flags.containsKey(label);
	}

	/**
	 * Get the total number of arguments contained within this context. The total does not include the CommandSender or any optional flags.
	 *
	 * @return total number of arguments.
	 */
	@Override
	public int size() {
		return arguments.size();
	}

	@Override
	public String toString() {
		return "AbstractCommandContext{" +
		", arguments=" + arguments +
		", flags=" + flags +
		", sender=" + sender +
		'}';
	}

	/**
	 * Get a list containing all arguments.
	 *
	 * @return a immutable list.
	 */
	protected final List<String> getArguments() {
		return Collections.unmodifiableList(arguments);
	}

	private final void setArguments(String arguments) {
		arguments = arguments.replaceAll(flagWithArgumentsMatcher.pattern().toString(), "");
		arguments = arguments.replaceAll(flagWithoutArgumentsMatcher.pattern().toString(), "");
		argumentMatcher.reset(arguments);
		while (argumentMatcher.find()) {
			this.arguments.add(argumentMatcher.group(1));
		}
	}

	private final void setFlags(String arguments) {
		flagWithArgumentsMatcher.reset(arguments);
		while (flagWithArgumentsMatcher.find()) {
			flags.put(flagWithArgumentsMatcher.group(1), flagWithArgumentsMatcher.group(2));
		}
		flagWithoutArgumentsMatcher.reset(arguments);
		while (flagWithoutArgumentsMatcher.find()) {
			flags.put(flagWithoutArgumentsMatcher.group(1), null);
		}
	}

}
