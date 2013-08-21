package name.richardson.james.bukkit.utilities.command;

/**
 * Stores and makes accessible useful metadata about a {@link Command}.
 */
public interface CommandMetadata {

	/**
	 * Return the short name of this command.
	 *
	 * @return the localised name of the command
	 */
	public String getName();

	/**
	 * Returns a brief description of what this command does.
	 *
	 * @return the localised description of the command
	 */
	public String getDescription();

	/**
	 * Returns the arguments that can be passed to this command.
	 *
	 * @return the localised usage message
	 */
	public String getUsage();

}
