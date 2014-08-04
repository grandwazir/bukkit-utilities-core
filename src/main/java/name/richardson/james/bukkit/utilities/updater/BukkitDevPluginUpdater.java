package name.richardson.james.bukkit.utilities.updater;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.bukkit.plugin.PluginDescriptionFile;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import name.richardson.james.bukkit.utilities.localisation.Messages;
import name.richardson.james.bukkit.utilities.localisation.MessagesFactory;

public final class BukkitDevPluginUpdater extends AbstractPluginUpdater {

	private static final Messages MESSAGES = MessagesFactory.getMessages();
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String API_NAME_VALUE = "name";
	private static final String API_LINK_VALUE = "downloadUrl";
	private static final String API_RELEASE_TYPE_VALUE = "releaseType";
	private static final String API_FILE_NAME_VALUE = "fileName";
	private static final String API_GAME_VERSION_VALUE = "gameVersion";
	private static final String API_QUERY = "/servermods/files?projectIds=";
	private static final String API_HOST = "https://api.curseforge.com";
	private static final String API_USER_AGENT = "BukkitUtilities Updater";
	private static final String DL_HOST = "cursecdn.com";
	private final Version gameVersion;
	private final int projectId;
	private final String updateFolder;
	private RemoteVersion remoteVersion;
	private String versionGameVersion;

	public BukkitDevPluginUpdater(PluginDescriptionFile descriptionFile, Branch branch, State state, int projectId, File updateFolder, String gameVersion) {
		super(descriptionFile, branch, state);
		this.projectId = projectId;
		this.updateFolder = updateFolder.getPath();
		this.gameVersion = new PluginVersion(gameVersion);
	}

	/**
	 * Get the current remote version of the plugin. <p/> This should be the latest released and available version matching the branch requested.
	 *
	 * @return The current remote version of the plugin.
	 */
	@Override
	public RemoteVersion getLatestRemoteVersion() {
		return remoteVersion;
	}

	/**
	 * Check to see if a new version of the plugin is available.
	 *
	 * @return return true if there is a version available, false otherwise.
	 */
	@Override public boolean isNewVersionAvailable() {
		Version localVersion = getLocalVersion();
		return localVersion.compareTo(getLatestRemoteVersion()) == -1;
	}

	@Override
	public void run() {
		try {
			URLConnection urlConnection = getConnection(API_HOST + API_QUERY + projectId);
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String response = reader.readLine();
			JSONArray array = (JSONArray) JSONValue.parse(response);
			java.util.ListIterator versions = array.listIterator(array.size());
			Branch branch = getBranch();
			while (versions.hasPrevious()) {
				JSONObject latest = (JSONObject) versions.previous();
				String versionType = (String) latest.get(API_RELEASE_TYPE_VALUE);
				if ((versionType.equals("beta") || versionType.equals("alpha")) && branch.equals(Branch.STABLE)) continue;
				Version requiredGameVersion = new PluginVersion((String) latest.get(API_GAME_VERSION_VALUE));
				if (!isCompatibleWithGameVersion(requiredGameVersion)) continue;
				String versionName = (String) latest.get(API_NAME_VALUE);
				remoteVersion = new RemotePluginVersion(versionName, (String) latest.get(API_LINK_VALUE));
				String versionFileName = (String) latest.get(API_FILE_NAME_VALUE);
				if (isNewVersionAvailable()) {
					String message = MESSAGES.updateAvailable(getName(), remoteVersion.toString());
					LOGGER.log(Level.INFO, message);
					break;
				}
			}
		} catch (Exception e) {
			String message = MESSAGES.updateException(e.getClass().getSimpleName(), e.getMessage());
			LOGGER.log(Level.WARN, message);
		}
	}

	@Override
	public void update() {
		if (isNewVersionAvailable() && getState() == State.UPDATE) {
			Version localVersion = getLocalVersion();
			RemoteVersion remoteVersion = getLatestRemoteVersion();
			if (localVersion.getMajorVersion() < remoteVersion.getMajorVersion()) {
				String message = MESSAGES.updateRequired(getName(), remoteVersion.toString());
				LOGGER.log(Level.INFO, message);
			} else {
				try {
					String message = MESSAGES.updateDownloading(getName(), remoteVersion.getDownloadPath());
					LOGGER.log(Level.INFO, message);
					URL target = new URL(remoteVersion.getDownloadPath());
					FileSystem system = FileSystems.getDefault();
					Path destination = system.getPath(updateFolder, getName() + ".jar");
					java.nio.file.Files.copy(target.openStream(), destination, StandardCopyOption.REPLACE_EXISTING);
				} catch (Exception e) {
					String message = MESSAGES.updateException(e.getClass().getSimpleName(), e.getMessage());
					LOGGER.log(Level.WARN, message);
				}
			}
		}
	}

	private URLConnection getConnection(String urlString)
	throws IOException {
		if (!urlString.contains(DL_HOST) && !urlString.contains(API_HOST)) throw new IllegalArgumentException();
		URL url = new URL(urlString);
		URLConnection urlConnection = url.openConnection();
		urlConnection.addRequestProperty("User-Agent", API_USER_AGENT);
		return urlConnection;
	}

	private boolean isCompatibleWithGameVersion(Version target) {
		return gameVersion.compareTo(target) >= 0;
	}

}
