package name.richardson.james.bukkit.utilities.updater;

public interface Version extends Comparable<Version> {

	int getMajorVersion();

	int getMinorVersion();

	int getPatchVersion();

	boolean isSnapshot();

}
