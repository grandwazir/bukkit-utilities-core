package name.richardson.james.bukkit.utilities.listener;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bukkit.plugin.Plugin;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localised;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;

public class AbstractLocalisedListener extends AbstractListener implements Localised {

	private final ResourceBundle localisation;

	public AbstractLocalisedListener(final Plugin plugin, final ResourceBundles bundleName) {
		super(plugin);
		this.localisation = ResourceBundle.getBundle(bundleName.getBundleName());
	}

	public AbstractLocalisedListener(final String pluginName, final ResourceBundles bundleName) {
		super(pluginName);
		this.localisation = ResourceBundle.getBundle(bundleName.getBundleName());
	}

	public String getMessage(final String key) {
		String message = this.localisation.getString(key);
		message = ColourFormatter.replace(message);
		return message;
	}

	public String getMessage(final String key, final Object... elements) {
		final MessageFormat formatter = new MessageFormat(this.localisation.getString(key));
		formatter.setLocale(Locale.getDefault());
		String message = formatter.format(elements);
		message = ColourFormatter.replace(message);
		return message;
	}

}
