/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * MavenManifest.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.updater;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MavenManifest {

	private final List<String> versionList = new LinkedList<String>();

	public MavenManifest(final File file) throws SAXException, IOException, ParserConfigurationException {
		final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		final Document doc = docBuilder.parse(file);
		// normalize text representation
		doc.getDocumentElement().normalize();
		final NodeList root = doc.getChildNodes();
		// navigate down to get the versioning node
		final Node meta = this.getNode("metadata", root);
		final Node versioning = this.getNode("versioning", meta.getChildNodes());
		final Node versions = this.getNode("versions", versioning.getChildNodes());
		// get a list of versions
		final NodeList nodes = versions.getChildNodes();
		this.setVersionList(nodes);
		file.deleteOnExit();
	}

	public String getCurrentVersion() {
		return this.versionList.get(0);
	}

	public List<String> getVersionList() {
		return Collections.unmodifiableList(this.versionList);
	}

	private Node getNode(final String tagName, final NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			final Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				return node;
			}
		}
		return null;
	}

	private void setVersionList(final NodeList nodes) {
		this.versionList.clear();
		int i = 0;
		while (i <= (nodes.getLength() - 1)) {
			final Node node = nodes.item(i);
			if (node.getNodeName().equalsIgnoreCase("version")) {
				this.versionList.add(0, node.getChildNodes().item(0).getNodeValue());
			}
			i++;
		}
	}
}
