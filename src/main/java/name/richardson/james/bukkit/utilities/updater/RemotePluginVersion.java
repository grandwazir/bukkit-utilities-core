package name.richardson.james.bukkit.utilities.updater;

public class RemotePluginVersion extends PluginVersion {

	private final String downloadPath;

	public RemotePluginVersion(final String version, String downloadPath) {
		super(version);
		this.downloadPath = downloadPath;
	}

	protected String getDownloadPath() {
		return downloadPath;
	}

}
