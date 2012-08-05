package name.richardson.james.bukkit.utilities.formatters;

import java.text.ChoiceFormat;
import java.text.MessageFormat;

import name.richardson.james.bukkit.utilities.localisation.Localisation;

public final class ChoiceFormatter {

  private Localisation localisation;
  
  private String message = "{0}";

  private Object[] arguments;

  private String[] formats;

  private double[] limits;

  public ChoiceFormatter(Localisation localisation) {
    this.localisation = localisation;
  }
  
  public void setFormats(String...formats) {
    this.formats = formats;
  }
  
  public void setLimits(double...limits) {
    this.limits = limits;
  }
  
  public void setMessage(Object object, String key) {
    this.message = this.localisation.getMessage(object, key);
  }
  
  public void setArguments(Object...arguments) {
    this.arguments = arguments;
  }
  
  public String getMessage() {
    final MessageFormat formatter = new MessageFormat(message);
    final ChoiceFormat cFormatter = new ChoiceFormat(limits, formats);
    formatter.setFormatByArgumentIndex(0, cFormatter);
    return formatter.format(arguments);
  }
  
}
