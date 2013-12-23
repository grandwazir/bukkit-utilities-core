/*
 * Copyright (c) 2013 James Richardson.
 *
 * AbstractRecordTest.java is part of BukkitUtilities.
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

import javax.persistence.Entity;
import java.sql.Timestamp;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public abstract class AbstractRecordTest extends TestCase {

	private final Timestamp now = new Timestamp(System.currentTimeMillis());
	private Record record;

	public Timestamp getNow() {
		return now;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	@Test
	public void setCreatedAtCorrectly() {
		getRecord().setCreatedAt(getNow());
		assertSame(getRecord().getCreatedAt(), getNow());
	}

	@Test
	public void setIdCorrectly() {
		long id = 1;
		getRecord().setId(id);
		assertSame(getRecord().getId(), id);
	}

	@Test
	public void setModifiedAtCorrectly() {
		getRecord().setModifiedAt(getNow());
		assertSame(getRecord().getModifiedAt(), getNow());
	}

	@Test
	public void setVersionCorrectly() {
		int version = 1;
		getRecord().setVersion(1);
		assertSame(getRecord().getVersion(), version);
	}

}
