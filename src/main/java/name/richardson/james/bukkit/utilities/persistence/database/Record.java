/*
 * Copyright (c) 2013 James Richardson.
 *
 * Record.java is part of BukkitUtilities.
 *
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.persistence.database;

import java.sql.Timestamp;

/**
 * This interface represents an Entity (or record) which is subsquently persisted into a database. It provides an interface of common metadata desirable for
 * most records.
 *
 * @author James Richardson
 * @version 7.0.0
 */
public interface Record {

	/**
	 * Get the time when this record was created.
	 *
	 * @return The timestamp at which the record was created.
	 * @since 7.0.0
	 */
	public Timestamp getCreatedAt();

	/**
	 * Set the time when this record was created.
	 *
	 * @param createdAt
	 * @since 7.0.0
	 */
	public void setCreatedAt(Timestamp createdAt);

	/**
	 * Get the primary key for this record
	 *
	 * @return the primary key
	 * @since 7.0.0
	 */
	public long getId();

	/**
	 * Set the primary key for this record.
	 * <p/>
	 * WARNING: You should not ever need to do this.
	 *
	 * @since 7.0.0
	 */
	public void setId(long id);

	/**
	 * Get the last time this record was modified.
	 *
	 * @return The timestamp at which the record was last modified.
	 * @since 7.0.0
	 */
	public Timestamp getModifiedAt();

	/**
	 * Set the last time this record was modified.
	 *
	 * @since 7.0.0
	 */
	public void setModifiedAt(Timestamp modifiedAt);

	/**
	 * Get the version of this record for OptimisticLocking.
	 *
	 * @returns The version of the record.
	 */
	public int getVersion();

	/**
	 * Set the version for OptimisticLocking
	 *
	 * @since 7.0.0
	 */
	public void setVersion(int version);

}
