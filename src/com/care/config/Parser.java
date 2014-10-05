package com.care.config;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Strings;

// TODO error checking of all strings required
// First argument should be the path of the config file
public class Parser {
	private static Input input = new Input();

	public static void main(String []args){
		String configFilePath = args[0];

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(configFilePath));

			//Iterating through the nodes and extracting the data.
			NodeList nodeList = doc.getDocumentElement().getChildNodes();			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				
				// parse input
				if (node.getNodeName().equalsIgnoreCase("input")) {
					NodeList children = node.getChildNodes();
					for (int j = 0; j < nodeList.getLength(); j++) {
						Node childOfInput = children.item(j);
						if(childOfInput == null || Strings.isNullOrEmpty(childOfInput.getNodeName()))
							continue;
						
						// parsing type of the input
						if (childOfInput.getNodeName().equalsIgnoreCase("type")) {
							String typeValue = childOfInput.getTextContent();
							if(typeValue.equalsIgnoreCase("file"))
								input.setType(InputType.FILE);
							else if(typeValue.equalsIgnoreCase("xml"))
								input.setType(InputType.XML);
							else{
								// TODO throw exception
							}
						}
						
						// parsing path of the input
						if (childOfInput.getNodeName().equalsIgnoreCase("path")) {
							String pathValue = childOfInput.getTextContent();
							if(!Strings.isNullOrEmpty(pathValue)){
								input.setPath(pathValue);
							}
						}
					}
				}
			
				// parse main component
				if (node.getNodeName().equalsIgnoreCase("component")) {
					// TODO implementation left
				}
				
				// parse the output
				if (node.getNodeName().equalsIgnoreCase("output")) {
					// TODO implementation left
				}
			}
		} catch (Exception e) {
			// TODO add logger library
			e.printStackTrace();
		} 
	}
}
