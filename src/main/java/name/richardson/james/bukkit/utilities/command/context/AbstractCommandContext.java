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
 * An example implementation of {@link CommandContext}. This implementation makes no additional verification checks on requested argumentsList and may throw
 * IndexOutOfBoundExceptions from the internal backing storage.
 */
public class AbstractCommandContext implements CommandContext {

	public static final Pattern ARGUMENT_PATTERN_WORDS = Pattern.compile("(\\S+)");
	public static final Pattern ARGUMENT_PATTERN_FOR_FLAGS = Pattern.compile("(\\w{1}):(\\w+)");
	public static final Pattern ARGUMENT_PATTERN_FOR_SWITCHES = Pattern.compile("-(\\w+)");
	private static final Logger LOGGER = PluginLoggerFactory.getLogger(CommandContext.class);
	private final String argumentsAsString;
	private final List<String> argumentsList = new ArrayList<String>();
	private final CommandSender commandSender;
	private final Map<String, String> flagsMap = new HashMap<String, String>();
	private final Collection<String> switchesCollection = new HashSet<String>();

	/**
	 * Constructs a new AbstractCommandContext with the argumentsList and CommandSender.
	 *
	 * @param arguments provided argumentsList
	 * @param commandSender the CommandSender executing the command
	 */
	public AbstractCommandContext(String[] arguments, CommandSender commandSender) {
		Validate.notNull(arguments);
		Validate.notNull(commandSender);
		this.commandSender = commandSender;
		argumentsAsString = StringUtils.join(arguments, " ");
		parseArgumentList();
		parseFlags();
		parseSwitches();
	}

	private final void parseArgumentList() {
		String argumentsList = argumentsAsString;
		argumentsList = argumentsList.replaceAll(ARGUMENT_PATTERN_FOR_FLAGS.pattern(), "");
		argumentsList = argumentsList.replaceAll(ARGUMENT_PATTERN_FOR_SWITCHES.pattern(), "");
		Matcher matcher = ARGUMENT_PATTERN_WORDS.matcher(argumentsList);
		while (matcher.find()) {
			this.argumentsList.add(matcher.group(1));
		}
	}

	private final void parseFlags() {
		Matcher matcher = ARGUMENT_PATTERN_FOR_FLAGS.matcher(argumentsAsString);
		while (matcher.find()) {
			flagsMap.put(matcher.group(1), matcher.group(2));
		}
	}

	private final void parseSwitches() {
		Matcher matcher = ARGUMENT_PATTERN_FOR_SWITCHES.matcher(argumentsAsString);
		while (matcher.find()) {
			switchesCollection.add(matcher.group(1));
		}
	}

	/**
	 * Get the CommandSender who called this command.
	 *
	 * @return the CommandSender
	 */
	@Override
	public CommandSender getCommandSender() {
		return commandSender;
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
		return flagsMap.get(label);
	}

	/**
	 * Join all the argumentsList from a specified index onwards into one String.
	 *
	 * @param initialIndex the index to start at at
	 * @return a String containing all the argumentsList separated by ' '.
	 */
	@Override
	public String getJoinedArguments(int initialIndex) {
		Validate.notNull(initialIndex);
		Validate.isTrue(initialIndex < size(), "Initial index can not be greater than size!");
		return StringUtils.join(getArgumentsList().subList(initialIndex, size()), " ");
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
		String s = null;
		if (hasArgument(index)) {
			s = argumentsList.get(index);
		}
		return s;
	}

	/**
	 * Check to see if the context contains an argument.
	 *
	 * @param index the argument number to check
	 * @return true if the argument exists, false otherwise.
	 */
	@Override
	public boolean hasArgument(int index) {
		Validate.notNull(index);
		return size() > 0 && (index + 1) <= size();
	}

	/**
	 * Check to see if the context contains a flag.
	 *
	 * @param switchName the prefix of the flag to check
	 * @return true if the flag exists, false otherwise.
	 */
	@Override
	public boolean hasSwitch(String switchName) {
		Validate.notNull(switchName);
		return switchesCollection.contains(switchName);
	}

	/**
	 * Get the total number of argumentsList contained within this context. The total does not include the CommandSender or any optional flagsMap.
	 *
	 * @return total number of argumentsList.
	 */
	@Override
	public int size() {
		return argumentsList.size();
	}

	/**
	 * Get a list containing all argumentsList.
	 *
	 * @return a immutable list.
	 */
	protected final List<String> getArgumentsList() {
		return Collections.unmodifiableList(argumentsList);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("AbstractCommandContext{");
		sb.append("argumentsAsString='").append(argumentsAsString).append('\'');
		sb.append(", argumentsList=").append(argumentsList);
		sb.append(", commandSender=").append(commandSender);
		sb.append(", flagsMap=").append(flagsMap);
		sb.append(", switchesCollection=").append(switchesCollection);
		sb.append('}');
		return sb.toString();
	}

}
