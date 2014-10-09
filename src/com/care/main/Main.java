package com.care.main;

import com.care.config.ComponentParser;
import com.care.config.InputParser;
import com.care.config.OutputParser;
import com.care.datatype.Component;
import com.care.datatype.Input;
import com.care.datatype.Output;
import com.care.datatype.ParseInputType;
import com.care.platform.InputHandler;
import com.care.platform.OutputHandler;
import com.care.platform.PlatformManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

// TODO error checking of all strings required
// TODO add logger library
// First argument should be the path of the config file
public class Main
{
	private static Input input;
	private static Component component;
	private static Output output;

	private static void ParseConfig(String configFilePath) throws Exception
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new File(configFilePath));

		//Iterating through the nodes and extracting the data.
		NodeList nodeList = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);

			// parse input
			if (node.getNodeName().equalsIgnoreCase("input"))
			{
				input = InputParser.GetInput(node);
				continue;
			}

			// parse main component
			if (node.getNodeName().equalsIgnoreCase("component"))
			{
				component = ComponentParser.GetComponent(node);
				continue;
			}

			// parse the output
			if (node.getNodeName().equalsIgnoreCase("output"))
			{
				output = OutputParser.GetOutput(node);
				continue;
			}
		}
	}

	private static Object ParseInputFile() throws Exception
	{
		InputHandler inputHandler = new InputHandler(input);

		String inputContent = inputHandler.ReadFile();
		if (input.getParseType() == ParseInputType.LIST)
		{
			return inputHandler.ConvertXmlStringToList(inputContent);
		}
		else
		{
			return inputContent;
		}
	}

	private static Object StartPlatform(Object inputContent) throws Exception
	{
		PlatformManager manager = new PlatformManager();

		return manager.StartComponent(component, inputContent);
		// TODO check either parseInputType or object and accordingly call function
		// of the interface
	}

	private static void GenerateOutputFile(Object outputContent) throws Exception
	{
		OutputHandler outputHandler = new OutputHandler(output);

		// TODO make this more generic
		// outputHandler.WriteStringToFile((String) outputContent);
		outputHandler.WriteListToFile((List<String>) outputContent);
	}

	public static void main(String[] args)
	{
		if (args.length < 1)
		{
			System.out.println("Min 1 argument reqd");
			// TODO throw exception
			return;
		}

		String configFilePath = args[0];

		try
		{
			// Parsing the config.xml
			ParseConfig(configFilePath);

			// Get the input from the format
			Object content = ParseInputFile();

			// Starting the platform
			Object output = StartPlatform(content);

			// Write the output in the required format
			GenerateOutputFile(output);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
