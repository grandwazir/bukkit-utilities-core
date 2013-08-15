/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 BukkitPermissionManagerTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.permissions;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class BukkitPermissionManagerTest extends TestCase {

	private PluginManager pluginManager;
	private Permission permission;
	private PermissionManager permissionManager;
	private Server server;

	@Test
	public void testListPermissions()
	throws Exception {
		permissionManager.addPermission(permission);
		Assert.assertTrue(permissionManager.listPermissions().contains(permission));
	}

	@Test
	public void testCreatePermissions()
	throws Exception {
		String[] permissions = {"test", "test.node"};
		permissionManager.createPermissions(permissions);
		Assert.assertTrue(permissionManager.listPermissions().size() == 2);
	}

	@Test
	public void testCreatedPermissionDescription() {
		Permission permission = permissionManager.createPermission("test", "description");
		Assert.assertTrue(permission.getDescription(), permission.getDescription().contentEquals("description"));
	}

	@Test
	public void testCreatedPermissionName() {
		Permission permission = permissionManager.createPermission("test", "description");
		Assert.assertTrue(permission.getName(), permission.getName().contentEquals("test"));
	}

	@Test
	public void testCreatedPermissionDefault() {
		Permission permission = permissionManager.createPermission("test", "description");
		Assert.assertTrue(permission.getDefault().toString(), permission.getDefault() == PermissionDefault.OP);
	}

	@Test
	public void testCreatedPermissionParent() {
		when(permission.getName()).thenReturn("test");
		Permission permission = permissionManager.createPermission("test.node", "description");
		verify(pluginManager).getPermission("test");
	}

	@Test
	public void testCreatePermissionWithParentSupplied() {
		Permission permission = permissionManager.createPermission("test.node", "description", PermissionDefault.NOT_OP, this.permission, false);
		verify(pluginManager, never()).getPermission("test");
	}


	@Test
	public void testAddPermission()
	throws Exception {
		permissionManager.addPermission(permission);
		verify(pluginManager).addPermission(permission);
	}

	@Test
	public void testDefaultConstructor() {
		PermissionManager manager = new BukkitPermissionManager();
		verify(server).getPluginManager();
	}


	@Before
	public void setUp()
	throws Exception {
		pluginManager = mock(PluginManager.class);
		permission = mock(Permission.class);
		when(pluginManager.getPermission(Matchers.<String>anyObject())).thenReturn(permission);
		permissionManager =  new BukkitPermissionManager(pluginManager);
		server = mock(Server.class);
		when(server.getPluginManager()).thenReturn(pluginManager);
		Field field = Bukkit.class.getDeclaredField("server");
		field.setAccessible(true);
		field.set(null, server);
	}

	@After
	public void tearDown() throws Exception {
		Field field = Bukkit.class.getDeclaredField("server");
		field.setAccessible(true);
		field.set(null, null);
	}

}
