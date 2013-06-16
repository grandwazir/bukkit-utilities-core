package name.richardson.james.bukkit.utilities.localisation;

import java.util.ResourceBundle;

import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;

public class LocalisedCommandSender {

	private final CommandSender sender;
	private final ResourceBundle bundle;

	public LocalisedCommandSender(final CommandSender sender) {
		this.bundle = ResourceBundle.getBundle(ResourceBundles.MESSAGES.getBundleName());
		this.sender = sender;
	}

	public LocalisedCommandSender(final CommandSender sender, final ResourceBundles bundleName) {
		this.bundle = ResourceBundle.getBundle(bundleName.getBundleName());
		this.sender = sender;
	}

	public void send(String key, Object... arguments) {
		String message = String.format(bundle.getString(key), arguments);
		message = ColourFormatter.replace(message);
		sender.sendMessage(message);
	}

}
