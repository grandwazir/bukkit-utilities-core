package name.richardson.james.bukkit.utilities.updater;

public class RemotePluginVersion extends PluginVersion implements RemoteVersion {

	private final String downloadPath;

	public RemotePluginVersion(final String version, String downloadPath) {
		super(version);
		this.downloadPath = downloadPath;
	}

	@Override public String getDownloadPath() {
		return downloadPath;
	}

}
