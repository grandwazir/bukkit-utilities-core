/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TestBeanParent.java is part of bukkit-utilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.persistence.database;

import javax.persistence.*;

import java.util.List;

import com.avaje.ebean.validation.NotNull;

@Entity
public class TestBeanParent {

	@OneToMany(targetEntity = TestBeanChild.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
	private List<TestBeanChild> children;

	@NotNull
	@Id
	private int id;

	public List<TestBeanChild> getChildren() {
		return children;
	}

	public void setChildren(List<TestBeanChild> children) {
		this.children = children;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
