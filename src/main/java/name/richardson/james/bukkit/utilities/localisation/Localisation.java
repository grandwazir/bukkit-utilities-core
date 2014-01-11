package name.richardson.james.bukkit.utilities.localisation;

/**
 * Retrieves localised messages using keys from a backing store.
 */
public interface Localisation {

	public static String RESOURCE_BUNDLE_NAME = "localisation/Messages";

	/**
	 * Return the localised message matching the key.
	 *
	 * @param key the key to use when looking up the message
	 * @return the localised message
	 */
	public String getMessage(String key);

	/**
	 * Return the localised message with the arguments provided.
	 *
	 * @param key the key to use when looking up the message
	 * @param arguments the arguments to insert into the string
	 * @return the localised message
	 */
	public String getMessage(String key, Object... arguments);

}
