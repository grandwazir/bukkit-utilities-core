package name.richardson.james.bukkit.utilities.updater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.EqualsBuilder;

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
		majorVersion = Integer.parseInt(matcher.group(1));
		minorVersion = Integer.parseInt(matcher.group(2));
		patchVersion = Integer.parseInt(matcher.group(3));
		snapshot = version.contains("SNAPSHOT");
	}

	@Override public int compareTo(final Version o) {
		int result = 0;
		if (majorVersion > o.getMajorVersion()) {
			result = 1;
		} else if (majorVersion < o.getMajorVersion()) {
			result = -1;
		}
		if (result == 0) {
			if (minorVersion > o.getMinorVersion()) {
				result = 1;
			} else if (minorVersion < o.getMinorVersion()) {
				result = -1;
			}
		}
		if (result == 0) {
			if (patchVersion > o.getPatchVersion()) {
				result = 1;
			} else if (patchVersion < o.getPatchVersion()) {
				result = -1;
			}
		}
		if (result == 0) {
			if (snapshot && !o.isSnapshot()) {
				result = 1;
			} else if (!snapshot && o.isSnapshot()) {
				result = -1;
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

	@SuppressWarnings("InstanceofInterfaces")
	@Override public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!(obj instanceof PluginVersion)) return false;
		Version version = (Version) obj;
		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(majorVersion, version.getMajorVersion());
		equalsBuilder.append(minorVersion, version.getMinorVersion());
		equalsBuilder.append(patchVersion, version.getPatchVersion());
		return equalsBuilder.isEquals();
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
