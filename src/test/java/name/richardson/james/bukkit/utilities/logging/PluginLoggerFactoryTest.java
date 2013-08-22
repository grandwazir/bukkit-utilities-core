/*
 * Copyright (c) 2013 James Richardson.
 *
 * PluginLoggerFactoryTest.java is part of BukkitUtilities.
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

package name.richardson.james.bukkit.utilities.logging;


import java.util.logging.LogManager;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.command.HelpCommand;

@RunWith(JUnit4.class)
public class PluginLoggerFactoryTest extends TestCase {

	@Test
	public void getLogger_WhenResourceBundleExists_ReturnLogger()
	throws Exception {
		assertNotNull(PluginLoggerFactory.getLogger(HelpCommand.class));
	}

	@Test
	public void getLogger_WhenResourceDoesNotExists_ReturnLogger()
	throws Exception {
		assertNotNull(PluginLoggerFactory.getLogger(JUnit4.class));
	}

	@Test
	public void getLogger_WhenCalledTwice_ReturnSameLogger()
	throws Exception {
		assertSame(PluginLoggerFactory.getLogger(HelpCommand.class), PluginLoggerFactory.getLogger(HelpCommand.class));
	}

	@After
	public void tearDown() {
		LogManager.getLogManager().reset();
	}

}
