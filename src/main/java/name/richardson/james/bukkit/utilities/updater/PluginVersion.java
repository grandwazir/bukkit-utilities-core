package name.richardson.james.bukkit.utilities.updater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PluginVersion implements Version {

	@SuppressWarnings("HardcodedFileSeparator")
	private static final String REGEX = "(\\d+).(\\d+).(\\d+)";
	private final int majorVersion;
	private final int minorVersion;
	private final int patchVersion;

	public PluginVersion(CharSequence version) {
		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(version);
		matcher.find();
		majorVersion = Integer.parseInt(matcher.group(0));
		minorVersion = Integer.parseInt(matcher.group(1));
		patchVersion = Integer.parseInt(matcher.group(2));
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

}
