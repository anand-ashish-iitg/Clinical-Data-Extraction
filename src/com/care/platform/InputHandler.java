package com.care.platform;

import com.care.datatype.Input;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

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
		byte[] content = Files.readAllBytes(Paths.get(input.getPath()));

		return new String(content);
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
			Document document = new SAXBuilder().build(new InputSource(new StringReader(content)));

			List<Element> rootElements = document.getRootElement().getChildren();
			for (Element node : rootElements)
			{
				if (node.getName().equalsIgnoreCase("block"))
				{
					List<Content> blockContent = node.getContent();
					// TODO fix this bug, this doesn't fetch if it further contains xml
					list.add(new XMLOutputter().outputString(blockContent));
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
