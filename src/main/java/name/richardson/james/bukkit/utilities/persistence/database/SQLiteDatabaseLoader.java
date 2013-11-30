/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 SQLiteDatabaseLoader.java is part of bukkit-utilities.

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

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;

/**
 * SQLite storage handles loading SQLite databases. Due to bug in the persistence library that Bukkit uses attempting to create a database with key constraists
 * fails. This class handles this by making the necessary modifications to the DDL script for it to be valid when using SQLite.
 */
public final class SQLiteDatabaseLoader extends AbstractDatabaseLoader {

	public SQLiteDatabaseLoader(DatabaseConfiguration configuration) {
		super(configuration);
		configuration.getServerConfig().setDatabasePlatform(new SQLitePlatform());
		configuration.getServerConfig().getDatabasePlatform().getDbDdlSyntax().setIdentity("");
	}

	@Override
	protected void afterDatabaseCreate() {
		return;
	}

	@Override
	protected void beforeDatabaseCreate() {
		return;
	}

	@Override
	protected void beforeDatabaseDrop() {
		return;
	}

	@Override
	public final String getGenerateDDLScript() {
		final SpiEbeanServer server = (SpiEbeanServer) getEbeanServer();
		final DdlGenerator generator = server.getDdlGenerator();
		// Create a BufferedReader out of the potentially invalid script
		final BufferedReader scriptReader = new BufferedReader(new StringReader(generator.generateCreateDdl()));
		// Create an array to store all the lines
		final List<String> scriptLines = new ArrayList<String>();
		// Create some additional variables for keeping track of tables
		final HashMap<String, Integer> foundTables = new HashMap<String, Integer>();
		// The name of the table we are currently reviewing
		String currentTable = null;
		int tableOffset = 0;
		try {
			// Loop through all lines
			String currentLine;
			while ((currentLine = scriptReader.readLine()) != null) {
				// Trim the current line to remove trailing spaces
				currentLine = currentLine.trim();
				// Add the current line to the rest of the lines
				scriptLines.add(currentLine.trim());
				// Check if the current line is of any use
				if (currentLine.startsWith("create table")) {
					// Found a table, so get its name and remember the line it has been
					// encountered on
					currentTable = currentLine.split(" ", 4)[2];
					foundTables.put(currentLine.split(" ", 3)[2], scriptLines.size() - 1);
				} else if (currentLine.startsWith(";") && (currentTable != null) && !currentTable.equals("")) {
					// Found the end of a table definition, so update the entry
					final int index = scriptLines.size() - 1;
					foundTables.put(currentTable, index);
					// Remove the last ")" from the previous line
					String previousLine = scriptLines.get(index - 1);
					previousLine = previousLine.substring(0, previousLine.length() - 1);
					scriptLines.set(index - 1, previousLine);
					// Change ";" to ");" on the current line
					scriptLines.set(index, ");");
					// Reset the table-tracker
					currentTable = null;
					// Found a potentially unsupported action
				} else if (currentLine.startsWith("alter table")) {
					final String[] alterTableLine = currentLine.split(" ", 4);
					if (alterTableLine[3].startsWith("add constraint")) {
						// Found an unsupported action: ALTER TABLE using ADD CONSTRAINT
						final String[] addConstraintLine = alterTableLine[3].split(" ", 4);
						// Check if this line can be fixed somehow
						if (addConstraintLine[3].startsWith("foreign key")) {
							// Calculate the index of last line of the current table
							final int tableLastLine = foundTables.get(alterTableLine[2]) + tableOffset;
							// Add a "," to the previous line
							scriptLines.set(tableLastLine - 1, scriptLines.get(tableLastLine - 1) + ",");
							// Add the constraint as a new line - Remove the ";" on the
							// end
							final String constraintLine = String.format("%s %s %s", addConstraintLine[1], addConstraintLine[2], addConstraintLine[3]);
							scriptLines.add(tableLastLine, constraintLine.substring(0, constraintLine.length() - 1));
							// Remove this line and raise the table offset because a line
							// has
							// been inserted.
							scriptLines.remove(scriptLines.size() - 1);
							tableOffset++;
						} else {
							// Exception: This line cannot be fixed but is known the be
							// unsupported by SQLite.
							throw new RuntimeException("Unsupported action encountered: ALTER TABLE using ADD CONSTRAINT with " + addConstraintLine[3]);
						}
					}
				}
			}
		} catch (final Exception exception) {
			throw new RuntimeException("Failed to valid the CreateDDL script!");
		}
		// Turn all the lines back into a single string
		String newScript = "";
		for (final String newLine : scriptLines) {
			newScript += newLine + "\n";
		}
		// Print the new script
		// System.out.println(newScript);
		// Return the fixed script
		return newScript;
	}

}
