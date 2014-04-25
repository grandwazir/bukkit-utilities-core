package name.richardson.james.bukkit.utilities.updater;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import org.bukkit.plugin.PluginDescriptionFile;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.codehaus.plexus.util.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import static name.richardson.james.bukkit.utilities.localisation.BukkitUtilities.*;

public class BukkitDevPluginUpdater extends AbstractPluginUpdater {

	private static final String API_NAME_VALUE = "name";
	private static final String API_LINK_VALUE = "downloadUrl";
	private static final String API_RELEASE_TYPE_VALUE = "releaseType";
	private static final String API_FILE_NAME_VALUE = "fileName";
	private static final String API_GAME_VERSION_VALUE = "gameVersion";

	private static final String API_QUERY = "/servermods/files?projectIds=";
	private static final String API_HOST = "https://api.curseforge.com";
	private static final String API_USER_AGENT = "BukkitUtilities Updater";

	private static final String DL_HOST = "cursecdn.com";

	private final int projectId;
	private final File updateFolder;
	private final String gameVersion;

	private String versionFileName;
	private String versionGameVersion;
	private String versionLink;
	private String versionName;
	private String versionType;
	private DefaultArtifactVersion remoteVersion;

	public BukkitDevPluginUpdater(PluginDescriptionFile descriptionFile, Branch branch, State state, int projectId, File updateFolder, String gameVersion) {
		super(descriptionFile, branch, state);
		this.projectId = projectId;
		this.updateFolder = updateFolder;
		this.gameVersion = gameVersion;
	}

	/**
	 * Get the current remote version of the plugin.
	 * <p/>
	 * This should be the latest released and available version matching the branch requested.
	 *
	 * @return The current remote version of the plugin.
	 */
	@Override
	public DefaultArtifactVersion getLatestRemoteVersion() {
		return remoteVersion;
	}

	/**
	 * Check to see if a new version of the plugin is available.
	 *
	 * @return return true if there is a version available, false otherwise.
	 */
	public boolean isNewVersionAvailable() {
		if (getLocalVersion().compareTo(getLatestRemoteVersion()) == -1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void update() {
		if (isNewVersionAvailable() && getState() == State.UPDATE) {
			final ArtifactVersion current = new DefaultArtifactVersion(this.gameVersion);
			final ArtifactVersion target = new DefaultArtifactVersion(versionGameVersion);
			if (current.getMajorVersion() != target.getMajorVersion()) {
				getLogger().log(Level.WARNING, UPDATER_MANUAL_UPDATE_REQUIRED.asMessage(versionLink));
				return;
			} else {
				try {
					getLogger().log(Level.INFO, UPDATER_DOWNLOADING.asMessage(versionLink));
					File destination = new File(updateFolder, getName() + ".jar");
					URLConnection urlConnection = getConnection(versionLink);
					FileUtils.copyURLToFile(urlConnection.getURL(), destination);
				} catch (Exception e) {
					getLogger().log(Level.WARNING, UPDATER_ENCOUNTERED_EXCEPTION.asMessage(e.getMessage()));
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BukkitDevPluginUpdater{");
		sb.append("gameVersion='").append(gameVersion).append('\'');
		sb.append(", projectId=").append(projectId);
		sb.append(", remoteVersion=").append(remoteVersion);
		sb.append(", updateFolder=").append(updateFolder);
		sb.append(", versionFileName='").append(versionFileName).append('\'');
		sb.append(", versionGameVersion='").append(versionGameVersion).append('\'');
		sb.append(", versionLink='").append(versionLink).append('\'');
		sb.append(", versionName='").append(versionName).append('\'');
		sb.append(", versionType='").append(versionType).append('\'');
		sb.append(", ").append(super.toString());
		sb.append('}');
		return sb.toString();
	}

	@Override
	public void run() {
		try {
			URLConnection urlConnection = getConnection(API_HOST + API_QUERY + projectId);
			final BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String response = reader.readLine();
			JSONArray array = (JSONArray) JSONValue.parse(response);
			java.util.ListIterator versions = array.listIterator(array.size());
			while(versions.hasPrevious()) {
				JSONObject latest = (JSONObject) versions.previous();
				versionType = (String) latest.get(API_RELEASE_TYPE_VALUE);
				versionGameVersion = (String) latest.get(API_GAME_VERSION_VALUE);
				if ((versionType.equals("beta") || versionType.equals("alpha")) && getBranch().equals(Branch.STABLE)) continue;
				if (!isCompatibleWithGameVersion()) continue;
				versionName = (String) latest.get(API_NAME_VALUE);
				remoteVersion = new DefaultArtifactVersion(versionName);
				versionLink = (String) latest.get(API_LINK_VALUE);
				versionFileName = (String) latest.get(API_FILE_NAME_VALUE);
				if (isNewVersionAvailable()) {
					getLogger().log(Level.INFO, UPDATER_NEW_VERSION_AVAILABLE.asMessage(getPluginName(), parseArtifactVersionToString(getLatestRemoteVersion())));
					break;
				}
			}
		} catch (Exception e) {
			getLogger().log(Level.WARNING, UPDATER_ENCOUNTERED_EXCEPTION.asMessage(e.getMessage()));
		}
	}

	private boolean isCompatibleWithGameVersion() {
		final Comparable current = new DefaultArtifactVersion(this.gameVersion);
		final DefaultArtifactVersion target = new DefaultArtifactVersion(versionGameVersion);
		final Object params[] = {target.toString(), current.toString()};
		return current.compareTo(target) != -1;
	}


}
