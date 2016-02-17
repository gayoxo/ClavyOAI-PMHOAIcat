/**
 * 
 */
package fdi.ucm.server.exportparser.common;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fdi.ucm.server.modelComplete.collection.CompleteLogAndUpdates;

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public class OAIPMHCatXMLSynonim {
	
	private HashMap<String, String> Language;
	private HashMap<String, String> Publication;
	private HashMap<String, String> Procedural; 
	private HashMap<String, String> Mechanism;
	private HashMap<String, String> Formatos;

	
	public OAIPMHCatXMLSynonim()
	{
		Language=new HashMap<String, String>();
		Publication=new HashMap<String, String>();
		Procedural=new HashMap<String, String>();
		Mechanism=new HashMap<String, String>();
		Formatos=new HashMap<String, String>();
	}
	
	public void processXML(String pathFile,CompleteLogAndUpdates cL) {



		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		   	  DocumentBuilder db = dbf.newDocumentBuilder();
		   	  Document doc = db.parse(pathFile);
		   	  doc.getDocumentElement().normalize();
//		   	  System.out.println("Clavy? " + doc.getDocumentElement().getNodeName());
		   	  
		   	  NodeList nodeLst = doc.getElementsByTagName("language");
//		   	  System.out.println("Information of all languages");
		   	  
		   	for (int s = 0; s < nodeLst.getLength(); s++) {

        	    Node fstNode = nodeLst.item(s);
        	    
        	    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
        	  
        	    	
        	           Element fstElmnt = (Element) fstNode;
        	      NodeList originalNmElmntLst = fstElmnt.getElementsByTagName("original");
        	      Element originalnameNmElmnt = (Element) originalNmElmntLst.item(0);
        	      NodeList originalnameNmElement = originalnameNmElmnt.getChildNodes();
//        	      System.out.println("Jarname : "  + ((Node) originalnameNmElement.item(0)).getNodeValue());
        	      
        	      NodeList synonimNmElmntLst = fstElmnt.getElementsByTagName("synonim");
        	      Element synonimNmElmnt = (Element) synonimNmElmntLst.item(0);
        	      NodeList synonimNmElement = synonimNmElmnt.getChildNodes();
//        	      System.out.println("jarpath  : " + ((Node) synonimNmElement.item(0)).getNodeValue());
        	      
        	      String Origen=((Node) originalnameNmElement.item(0)).getNodeValue();
        	      String Synonimo=((Node) synonimNmElement.item(0)).getNodeValue();
        	      
        	      Language.put(Origen.trim(),Synonimo.trim() );
//        	      SavePair nuevo=new SavePair(((Node) jarnameNmElement.item(0)).getNodeValue(), ((Node) jarpathNmElement.item(0)).getNodeValue());
//        	      
//        	      ListaSer.add(nuevo);
        	    }

        	  }
		   	
		   	NodeList nodeLstPublic = doc.getElementsByTagName("publication");
//		   	  System.out.println("Information of all languages");
		   	  
		   	for (int s = 0; s < nodeLstPublic.getLength(); s++) {

      	    Node fstNode = nodeLstPublic.item(s);
      	    
      	    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
      	  
      	    	
      	           Element fstElmnt = (Element) fstNode;
      	      NodeList originalNmElmntLst = fstElmnt.getElementsByTagName("original");
      	      Element originalnameNmElmnt = (Element) originalNmElmntLst.item(0);
      	      NodeList originalnameNmElement = originalnameNmElmnt.getChildNodes();
//      	      System.out.println("Jarname : "  + ((Node) originalnameNmElement.item(0)).getNodeValue());
      	      
      	      NodeList synonimNmElmntLst = fstElmnt.getElementsByTagName("synonim");
      	      Element synonimNmElmnt = (Element) synonimNmElmntLst.item(0);
      	      NodeList synonimNmElement = synonimNmElmnt.getChildNodes();
//      	      System.out.println("jarpath  : " + ((Node) synonimNmElement.item(0)).getNodeValue());
      	      
      	      String Origen=((Node) originalnameNmElement.item(0)).getNodeValue();
      	      String Synonimo=((Node) synonimNmElement.item(0)).getNodeValue();
      	      
      	      Publication.put(Origen.trim(),Synonimo.trim() );
//      	      SavePair nuevo=new SavePair(((Node) jarnameNmElement.item(0)).getNodeValue(), ((Node) jarpathNmElement.item(0)).getNodeValue());
//      	      
//      	      ListaSer.add(nuevo);
      	    }

      	  }
		   	
		   	NodeList nodeLstProcedural = doc.getElementsByTagName("procedural");
//		   	  System.out.println("Information of all languages");
		   	  
		   	for (int s = 0; s < nodeLstProcedural.getLength(); s++) {

    	    Node fstNode = nodeLstProcedural.item(s);
    	    
    	    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
    	  
    	    	
    	           Element fstElmnt = (Element) fstNode;
    	      NodeList originalNmElmntLst = fstElmnt.getElementsByTagName("original");
    	      Element originalnameNmElmnt = (Element) originalNmElmntLst.item(0);
    	      NodeList originalnameNmElement = originalnameNmElmnt.getChildNodes();
//    	      System.out.println("Jarname : "  + ((Node) originalnameNmElement.item(0)).getNodeValue());
    	      
    	      NodeList synonimNmElmntLst = fstElmnt.getElementsByTagName("synonim");
    	      Element synonimNmElmnt = (Element) synonimNmElmntLst.item(0);
    	      NodeList synonimNmElement = synonimNmElmnt.getChildNodes();
//    	      System.out.println("jarpath  : " + ((Node) synonimNmElement.item(0)).getNodeValue());
    	      
    	      String Origen=((Node) originalnameNmElement.item(0)).getNodeValue();
    	      String Synonimo=((Node) synonimNmElement.item(0)).getNodeValue();
    	      
    	      Procedural.put(Origen.trim(),Synonimo.trim() );
//    	      SavePair nuevo=new SavePair(((Node) jarnameNmElement.item(0)).getNodeValue(), ((Node) jarpathNmElement.item(0)).getNodeValue());
//    	      
//    	      ListaSer.add(nuevo);
    	    }

    	  }
		   	
		   	NodeList nodeLstMechanism = doc.getElementsByTagName("mechanism");
//		   	  System.out.println("Information of all languages");
		   	  
		   	for (int s = 0; s < nodeLstMechanism.getLength(); s++) {

  	    Node fstNode = nodeLstMechanism.item(s);
  	    
  	    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
  	  
  	    	
  	           Element fstElmnt = (Element) fstNode;
  	      NodeList originalNmElmntLst = fstElmnt.getElementsByTagName("original");
  	      Element originalnameNmElmnt = (Element) originalNmElmntLst.item(0);
  	      NodeList originalnameNmElement = originalnameNmElmnt.getChildNodes();
//  	      System.out.println("Jarname : "  + ((Node) originalnameNmElement.item(0)).getNodeValue());
  	      
  	      NodeList synonimNmElmntLst = fstElmnt.getElementsByTagName("synonim");
  	      Element synonimNmElmnt = (Element) synonimNmElmntLst.item(0);
  	      NodeList synonimNmElement = synonimNmElmnt.getChildNodes();
//  	      System.out.println("jarpath  : " + ((Node) synonimNmElement.item(0)).getNodeValue());
  	      
  	      String Origen=((Node) originalnameNmElement.item(0)).getNodeValue();
  	      String Synonimo=((Node) synonimNmElement.item(0)).getNodeValue();
  	      
  	    Mechanism.put(Origen.trim(),Synonimo.trim() );
//  	      SavePair nuevo=new SavePair(((Node) jarnameNmElement.item(0)).getNodeValue(), ((Node) jarpathNmElement.item(0)).getNodeValue());
//  	      
//  	      ListaSer.add(nuevo);
  	    }

  	  }
		   	
		   	NodeList nodeLstFormatos = doc.getElementsByTagName("formatos");
//		   	  System.out.println("Information of all languages");
		   	  
		   	for (int s = 0; s < nodeLstFormatos.getLength(); s++) {

	    Node fstNode = nodeLstFormatos.item(s);
	    
	    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
	  
	    	
	           Element fstElmnt = (Element) fstNode;
	      NodeList originalNmElmntLst = fstElmnt.getElementsByTagName("original");
	      Element originalnameNmElmnt = (Element) originalNmElmntLst.item(0);
	      NodeList originalnameNmElement = originalnameNmElmnt.getChildNodes();
//	      System.out.println("Jarname : "  + ((Node) originalnameNmElement.item(0)).getNodeValue());
	      
	      NodeList synonimNmElmntLst = fstElmnt.getElementsByTagName("synonim");
	      Element synonimNmElmnt = (Element) synonimNmElmntLst.item(0);
	      NodeList synonimNmElement = synonimNmElmnt.getChildNodes();
//	      System.out.println("jarpath  : " + ((Node) synonimNmElement.item(0)).getNodeValue());
	      
	      String Origen=((Node) originalnameNmElement.item(0)).getNodeValue();
	      String Synonimo=((Node) synonimNmElement.item(0)).getNodeValue();
	      
	    Formatos.put(Origen.trim(),Synonimo.trim() );
//	      SavePair nuevo=new SavePair(((Node) jarnameNmElement.item(0)).getNodeValue(), ((Node) jarpathNmElement.item(0)).getNodeValue());
//	      
//	      ListaSer.add(nuevo);
	    }

	  }
		   	
		   	  
		} catch (Exception e) {
			cL.getLogLines().add("Error en carga de XML");
		}
		
	}
	
	public HashMap<String, String> getLanguage() {
		return Language;
	}
	
	public HashMap<String, String> getPublication() {
		return Publication;
	}
	
	public HashMap<String, String> getProcedural() {
		return Procedural;
	}
	
	public HashMap<String, String> getMechanism() {
		return Mechanism;
	}
	
	public HashMap<String, String> getFormatos() {
		return Formatos;
	}

}
