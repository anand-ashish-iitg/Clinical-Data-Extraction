package com.care.main;

import com.care.config.ComponentParser;
import com.care.config.InputParser;
import com.care.config.OutputParser;
import com.care.datatype.Component;
import com.care.datatype.Input;
import com.care.datatype.Output;
import com.care.datatype.ParseInputType;
import com.care.exception.ComponentException;
import com.care.exception.ConfigException;
import com.care.platform.InputHandler;
import com.care.platform.OutputHandler;
import com.care.platform.PlatformManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO error checking of all strings required
// TODO add logger library
// TODO handle constant string properly in some library
public class Main
{
        private static Input input;
        private static List<Component> components;
        private static Output output;

        private static void ParseConfig(String configFilePath) throws ParserConfigurationException, IOException, SAXException, ConfigException
        {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(new File(configFilePath));

                components = new ArrayList<Component>();

                // Iterating through the nodes and extracting the data.
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
                        if (node.getNodeName().equalsIgnoreCase("components"))
                        {
                                NodeList componentList = node.getChildNodes();
                                for (int j = 0; j < componentList.getLength(); j++)
                                {
                                        try
                                        {
                                                components.add(ComponentParser.GetComponent(componentList.item(j)));
                                        }
                                        catch (ComponentException e)
                                        {
                                                e.printStackTrace();
                                        }
                                }
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

        private static List<String> StartPlatform(Object inputContent) throws Exception
        {
                PlatformManager manager = new PlatformManager();
                List<String> outputContent = null;

                manager.InitializeComponent(components.get(0));

                // Special handling for 1st component.
                if (input.getParseType() == ParseInputType.STRING)
                {
                        outputContent = manager.DoWork((String) inputContent);
                }
                else
                {
                        outputContent = manager.DoWork((List<String>) inputContent);
                }

                for (int i = 1; i < components.size(); i++)
                {
                        outputContent = manager.DoWork((List<String>) outputContent);
                }

                return outputContent;
        }

        private static void GenerateOutputFile(List<String> outputContent) throws Exception
        {
                OutputHandler outputHandler = new OutputHandler(output);

                outputHandler.WriteListToFile(outputContent);
        }

        public static void main(String[] args)
        {
                try
                {
                        if (args.length < 1)
                        {
                                throw new IllegalArgumentException("Config file path required as system argument");
                        }

                        String configFilePath = args[0];

                        // Parsing the config.xml
                        ParseConfig(configFilePath);

                        // Get the input from the format
                        Object content = ParseInputFile();

                        // Starting the platform
                        List<String> output = StartPlatform(content);

                        // Write the output in the required format
                        GenerateOutputFile(output);

                }
                catch (Exception e)
                {
                        // TODO properly display exception message and stack
                        // trace somewhere
                }
        }
}
