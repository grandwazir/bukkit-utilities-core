package name.richardson.james.bukkit.utilities.formatters;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import name.richardson.james.bukkit.utilities.localisation.Localised;

public abstract class AbstractChoiceFormatter implements ChoiceFormatter {

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
	public final void setFormats(final Localised... args) {
		List<String> formats = new LinkedList<String>();
		for (Localised key : args) {
			formats.add(key.asMessage());
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
		sb.append(", message='").append(message).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
