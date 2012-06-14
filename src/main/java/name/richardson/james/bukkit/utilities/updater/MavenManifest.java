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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MavenManifest {
  
  private final List<String> versionList = new LinkedList<String>();

  public MavenManifest(File file) throws SAXException, IOException, ParserConfigurationException {
    
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
      Document doc = docBuilder.parse (file);
      // normalize text representation
      doc.getDocumentElement().normalize();
      
      // get a list of all available versions
      NodeList versions = doc.getDocumentElement().getElementsByTagName("version");
      int i = versions.getLength();
      
      while (i > 0) {
        versionList.add(0, versions.item(i).toString());
        i--;
      }
      
      file.deleteOnExit();
      
  }
  
  public List<String> getVersionList() {
    return Collections.unmodifiableList(versionList);
  }
  
  public String getCurrentVersion() {
    return versionList.get(0);
  }
  
  
}
