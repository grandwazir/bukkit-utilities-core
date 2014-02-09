/*
 * Copyright (c) 2014 James Richardson.
 *
 * AbstractConfigurationTest.java is part of BukkitUtilities.
 *
 * bukkit-utilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * bukkit-utilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * bukkit-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.persistence.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.util.FileUtil;

import junit.framework.TestCase;
import org.codehaus.plexus.util.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public abstract class AbstractConfigurationTest extends TestCase {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	private AbstractConfiguration configuration;

	@Test
	public void getConfigurationShouldNeverReturnNull() {
		assertNotNull(configuration.getConfiguration());
	}

	@Test
	public void isConfigurationPersisted()
	throws Exception {
		getConfiguration().getConfiguration().set("test", "test");
		getConfiguration().save();
		File file = FileUtils.resolveFile(folder.getRoot(), "test.yml");
		String content = FileUtils.fileRead(file);
		assertTrue("Saved configuration has does not contain an element which should have been persisted!", content.contains("test: test"));
	}

	@Test
	public void isHeaderCommentPersisted()
	throws Exception {
		File file = FileUtils.resolveFile(folder.getRoot(), "test.yml");
		String content = FileUtils.fileRead(file);
		assertTrue("Comment header from configuration file has not been copied!", content.startsWith("#"));
	}

	protected abstract AbstractConfiguration getConfiguration();

}
