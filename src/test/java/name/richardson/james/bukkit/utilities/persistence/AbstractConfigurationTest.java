/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 YAMLStorageTest.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.persistence.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;
import org.codehaus.plexus.util.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import name.richardson.james.bukkit.utilities.persistence.configuration.AbstractConfiguration;

public class AbstractConfigurationTest extends TestCase {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	private AbstractConfiguration configuration;

	@Test
	public void testSave()
	throws Exception {
		configuration.getConfiguration().set("test", "test");
		configuration.save();
		File file = FileUtils.resolveFile(folder.getRoot(), "test.yml");
		String content = FileUtils.fileRead(file);
		assertTrue("Configuration has not been saved correctly!", content.contains("test: test"));
	}

	@Test
	public void testCheckHeaderSaved()
	throws Exception {
		File file = FileUtils.resolveFile(folder.getRoot(), "test.yml");
		String content = FileUtils.fileRead(file);
		assertTrue("Comment header from configuration file has not been copied!", content.startsWith("#"));
	}

	@Test
	public void testGetConfiguration()
	throws Exception {
		assertNotNull("Base configuration should never be null!", configuration.getConfiguration());
	}

	@Before
	public void setUp()
	throws Exception {
		folder.create();
		configuration = new AbstractConfigurationTestClass(folder.newFile("test.yml"), getClass().getClassLoader().getResourceAsStream("test.yml"));
	}

}
