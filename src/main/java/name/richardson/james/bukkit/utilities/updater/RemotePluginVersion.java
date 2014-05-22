package name.richardson.james.bukkit.utilities.updater;

public class RemotePluginVersion extends PluginVersion {

	private final String downloadPath;
	private final Version gameVersion;

	public RemotePluginVersion(final String version, Version gameVersion, String downloadPath) {
		super(version);
		this.gameVersion = gameVersion;
		this.downloadPath = downloadPath;
	}

	protected String getDownloadPath() {
		return downloadPath;
	}

	protected Version getGameVersion() {
		return gameVersion;
	}

}
