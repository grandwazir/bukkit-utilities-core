package name.richardson.james.bukkit.utilities.formatters;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.StrictResourceBundleLocalisation;

public abstract class AbstractChoiceFormatter implements ChoiceFormatter {

	private final Localisation localisation = new StrictResourceBundleLocalisation();
	private Object[] arguments;
	private String[] formats;
	private double[] limits;
	private String message = "{0}";

	@Override
	public final String getMessage() {
		final MessageFormat formatter = new MessageFormat(this.message);
		final ChoiceFormat cFormatter = new ChoiceFormat(this.limits, this.formats);
		formatter.setFormatByArgumentIndex(0, cFormatter);
		return formatter.format(this.arguments);
	}

	@Override
	public final void setMessage(final String message) {
		this.message = message;
	}

	@Override
	public final void setArguments(final Object... arguments) {
		this.arguments = arguments;
	}

	@Override
	public final void setFormats(final String... args) {
		List<String> formats = new LinkedList<String>();
		for (String key : args) {
			formats.add(this.localisation.getMessage(key));
		}
		this.formats = formats.toArray(new String[formats.size()]);
	}

	@Override
	public final void setLimits(final double... limits) {
		this.limits = limits;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("AbstractChoiceFormatter{");
		sb.append("arguments=").append(Arrays.toString(arguments));
		sb.append(", formats=").append(Arrays.toString(formats));
		sb.append(", limits=").append(Arrays.toString(limits));
		sb.append(", localisation=").append(localisation);
		sb.append(", message='").append(message).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
