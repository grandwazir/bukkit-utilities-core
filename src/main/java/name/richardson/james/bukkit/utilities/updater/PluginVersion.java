package name.richardson.james.bukkit.utilities.updater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PluginVersion implements Version {

	@SuppressWarnings("HardcodedFileSeparator")
	private static final String REGEX = "(\\d+).(\\d+).(\\d+)";
	private final int majorVersion;
	private final int minorVersion;
	private final int patchVersion;
	private final boolean snapshot;

	public PluginVersion(String version) {
		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(version);
		matcher.find();
		majorVersion = Integer.parseInt(matcher.group(0));
		minorVersion = Integer.parseInt(matcher.group(1));
		patchVersion = Integer.parseInt(matcher.group(2));
		snapshot = version.contains("SNAPSHOT");
	}

	@Override public int compareTo(final Version version) {
		int result = 0;
		if (majorVersion > version.getMajorVersion()) {
			result = 1;
		} else if (majorVersion < version.getMajorVersion()) {
			result = -1;
		} else {
			if (minorVersion > version.getMinorVersion()) {
				result = 1;
			} else if (minorVersion < version.getMinorVersion()) {
				result = -1;
			}
			if (patchVersion > version.getPatchVersion()) {
				result = 1;
			} else if (patchVersion < version.getPatchVersion()) {
				result = -1;
			} else {
				if (snapshot && !version.isSnapshot()) {
					result = 1;
				} else {
					result = -1;
				}
			}
		}
		return result;
	}

	@Override public int getMajorVersion() {
		return majorVersion;
	}

	@Override public int getMinorVersion() {
		return minorVersion;
	}

	@Override public int getPatchVersion() {
		return patchVersion;
	}

	@Override public boolean isSnapshot() {
		return snapshot;
	}

	@Override public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(majorVersion);
		builder.append(".");
		builder.append(minorVersion);
		builder.append(".");
		builder.append(patchVersion);
		if (snapshot) {
			builder.append("-SNAPSHOT");
		}
		return builder.toString();
	}

}
