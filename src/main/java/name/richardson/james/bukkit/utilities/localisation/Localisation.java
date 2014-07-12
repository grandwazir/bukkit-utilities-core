package name.richardson.james.bukkit.utilities.localisation;

import com.vityuk.ginger.Localization;
import com.vityuk.ginger.LocalizationBuilder;

@SuppressWarnings({"HardcodedFileSeparator", "StaticNonFinalField"})
public class Localisation {

	private static final String PATH = "classpath:localisation/bukkit-utilities-core.properties";
	private static LocalisedMessages messages;

	public static LocalisedMessages getMessages() {
		if (messages == null) {
			Localization localisation = getBuilder();
			messages = localisation.getLocalizable(LocalisedMessages.class);
		}
		return messages;
	}

	private static Localization getBuilder() {
		LocalizationBuilder builder = new LocalizationBuilder();
		builder.withResourceLocation(PATH);
		Localization localisation = builder.build();
		return localisation;
	}

}
