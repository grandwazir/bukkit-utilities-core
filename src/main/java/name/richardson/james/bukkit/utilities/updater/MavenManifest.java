package name.richardson.james.bukkit.utilities.updater;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
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

    NodeList root = doc.getChildNodes();
    
    // navigate down to get the versioning node
    Node meta = getNode("metadata", root);
    Node versioning = getNode("versioning", meta.getChildNodes());
    Node versions = getNode("versions", versioning.getChildNodes());
    
    // get a list of versions
    NodeList nodes = versions.getChildNodes();
    this.setVersionList(nodes);
    
    file.deleteOnExit();

  }

  public String getCurrentVersion() {
    return this.versionList.get(0);
  }

  public List<String> getVersionList() {
    return Collections.unmodifiableList(this.versionList);
  }

  private void setVersionList(NodeList nodes) {
    versionList.clear();
    int i = 0;
    while (i <= nodes.getLength() - 1) {
      Node node = nodes.item(i);
      if (node.getNodeName().equalsIgnoreCase("version")) {
        versionList.add(0, node.getChildNodes().item(0).getNodeValue());
      } 
      i++;
    }
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

}
