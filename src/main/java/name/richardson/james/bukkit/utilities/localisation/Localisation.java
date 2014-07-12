package name.richardson.james.bukkit.utilities.localisation;

@SuppressWarnings({"HardcodedFileSeparator", "StaticNonFinalField"})
public class Localisation {

	private static final String PATH = "localisation/bukkit-utilities-core";
	private static LocalisedMessages messages;

	public static LocalisedMessages getMessages() {
		if (messages == null) {

			messages = new SimpleLocalisedMessages(PATH);
		}
		return messages;
	}

}
