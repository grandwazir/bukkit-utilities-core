package name.richardson.james.bukkit.utilities.localisation;

import java.util.ResourceBundle;

import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;

public class LocalisedCommandSender {

	private final CommandSender sender;
	private final ResourceBundle bundle;

	public LocalisedCommandSender(final CommandSender sender, final ResourceBundle bundle) {
		this.bundle = bundle;
		this.sender = sender;
	}

	public void send(String key, Object... arguments) {
		String message = String.format(bundle.getString(key), arguments);
		message = ColourFormatter.replace(message);
		sender.sendMessage(message);
	}

}
