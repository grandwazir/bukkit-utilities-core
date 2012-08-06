/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * AbstractLocalisation.java is part of BukkitUtilities.
 * 
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.localisation;

import java.util.Locale;

import name.richardson.james.bukkit.utilities.formatters.ChoiceFormatter;

public abstract class AbstractLocalisation implements Localisation {

  private final Locale locale;

  public AbstractLocalisation() {
    this.locale = Locale.getDefault();
  }

  public AbstractLocalisation(final Locale locale) {
    this.locale = locale;
  }

  public ChoiceFormatter getChoiceFormatter() {
    return new ChoiceFormatter(this);
  }

  public Locale getLocale() {
    return this.locale;
  }

}
