package name.richardson.james.bukkit.utilities.command;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localised;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;
import name.richardson.james.bukkit.utilities.matchers.Matcher;
import name.richardson.james.bukkit.utilities.permissions.BukkitPermissionManager;

public abstract class AbstractCommand implements Command, Localised {
	
	private final String name;
	private final String description;
	private final String usage;
	private ResourceBundle localisation; 
	private final List<Matcher> matchers = new ArrayList<Matcher>();
	private BukkitPermissionManager permissionManager;
	
	public AbstractCommand(ResourceBundles resourceBundleName) {
		final ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleName.getBundleName());
		final String simpleName = this.getClass().getSimpleName().toLowerCase();
		this.name = bundle.getString(simpleName + ".name");
		this.description = bundle.getString(simpleName + ".description");
		this.usage = bundle.getString(simpleName + ".usage");
		if (this.getClass().isAnnotationPresent(CommandPermissions.class)) this.setPermissions();
		if (this.getClass().isAnnotationPresent(CommandMatchers.class)) this.setMatchers();
	}
	
	protected void setMatchers() {
		final CommandMatchers annotation = this.getClass().getAnnotation(CommandMatchers.class);
		for (Class<Matcher> matcherClass : annotation.matchers()) {
			try {
				matchers.add(matcherClass.getConstructor().newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected List<Matcher> getMatchers() {
		return this.matchers;
	}

	private void setPermissions() {
		final CommandPermissions annotation = this.getClass().getAnnotation(CommandPermissions.class);
		this.permissionManager = new BukkitPermissionManager();
		this.permissionManager.createPermissions(annotation.required());
		this.permissionManager.createPermissions(annotation.optional());
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getUsage() {
		return this.usage;
	}

	public boolean isAuthorized(Permissible permissible) {
		if (this.permissionManager == null) return true;
		for (Permission permission : this.permissionManager.listPermissions()) {
			if (permissible.hasPermission(permission)) return true;
		}
		return false;
	}

	public List<String> onTabComplete(List<String> arguments, CommandSender sender) {
		final List<String> results = new ArrayList<String>();
		final Matcher matcher = matchers.get(arguments.size());
		if (matcher != null) {
			results.addAll(matcher.getMatches(arguments.get(arguments.size())));
		}
		return results;
	}

	public String getMessage(String key) {
    String message = localisation.getString(key);
    message = ColourFormatter.replace(message);
    return message;
	}

	public String getMessage(String key, String... elements) {
    MessageFormat formatter = new MessageFormat(localisation.getString(key));
    formatter.setLocale(Locale.getDefault());
    String message = formatter.format(elements);
    message = ColourFormatter.replace(message);
    return message;
	}

	
}