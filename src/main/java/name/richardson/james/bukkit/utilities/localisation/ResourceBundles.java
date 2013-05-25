package name.richardson.james.bukkit.utilities.localisation;

public enum ResourceBundles {

	MESSAGES("messages"), PERMISSIONS("permissions"), UTILITIES("utilities");

	private String bundleName;

	ResourceBundles(String bundleName) {
		this.bundleName = bundleName;
	}

	public String getBundleName() {
		return bundleName;
	}

	@Override
	public String toString() {
		return bundleName;
	}

}
