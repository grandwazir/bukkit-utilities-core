package name.richardson.james.bukkit.utilities.localisation;


public interface Localisation {

	public String getMessage(String key);

	public String getMessage(String key, Object... arguments);

}
