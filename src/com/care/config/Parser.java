package com.care.config;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.care.datatype.Component;
import com.care.datatype.Input;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO error checking of all strings required
// First argument should be the path of the config file
public class Parser {
	private static Input input;
	private static Component component;

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
					input = InputParser.GetInput(node);
				}
			
				// parse main component
				if (node.getNodeName().equalsIgnoreCase("component")) {
					component = ComponentParser.GetComponent(node);
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
