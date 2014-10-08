package com.care.platform;

import com.care.datatype.Input;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMIT on 8/10/14.
 */
public class InputHandler {
	private Input input;

	public InputHandler(Input input){
		this.input = input;
	}

	/**
	 * Reads file into a string
	 * @return
	 * @throws IOException
	 */
	public String ReadFile() throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(input.getPath()));

		return new String(encoded);
	}

	/**
	 * Converts string to List<string>
	 * separated by <block></block>
	 * @param content valid XMLString
	 * @return List<String>
	 */
	public List<String> ConvertXmlStringToList(String content){
		List<String> list = new ArrayList<String>();

		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(content)));

			NodeList nodeList = doc.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Node node = nodeList.item(i);

				if (node.getNodeName().equalsIgnoreCase("block"))
				{
					String blockContent = node.getTextContent();
					list.add(blockContent);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return list;
	}
}
