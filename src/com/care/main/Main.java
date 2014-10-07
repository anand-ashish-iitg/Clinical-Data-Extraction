package com.care.main;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.care.config.ComponentParser;
import com.care.config.InputParser;
import com.care.datatype.Component;
import com.care.datatype.Input;
import com.care.platform.PlatformManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO error checking of all strings required
// TODO add logger library
// First argument should be the path of the config file
public class Main {
	private static Input input;
	private static Component component;

	private static void ParseConfig(String configFilePath) throws Exception{
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
	}

	private static void StartPlatform() throws Exception{
		PlatformManager manager = new PlatformManager();

		manager.StartComponent(component);
	}

	public static void main(String []args){
		if(args.length < 1){
			System.out.println("Min 1 argument reqd");
			// TODO throw exception
			return;
		}

		String configFilePath = args[0];

		try {
			// Parsing the config.xml
			ParseConfig(configFilePath);

			// TODO Get the input from the format

			// Starting the platform
			StartPlatform();

			// TODO Write the output in the required format
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
