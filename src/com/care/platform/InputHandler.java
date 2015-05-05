package com.care.platform;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

import com.care.datatype.Input;

/**
 * Created by AMIT on 8/10/14.
 */
public class InputHandler
{
    private Input input;

    public InputHandler(Input input)
    {
        this.input = input;
    }

    /**
     * Reads file into a string
     *
     * @return
     * @throws IOException
     */
    public String ReadFile() throws IOException
    {
        byte[] content = Files.readAllBytes(Paths.get(input.getPath()));

        return new String(content);
    }

    /**
     * Converts string to List<string> separated by <block></block>
     *
     * @param content valid XMLString
     * @return List<String>
     */
    public List<String> ConvertXmlStringToList(String content) throws JDOMException, IOException
    {
        List<String> list = new ArrayList<String>();

        Document document = new SAXBuilder().build(new InputSource(new StringReader(content)));

        List<Element> rootElements = document.getRootElement().getChildren();
        for (Element node : rootElements)
        {
            if (node.getName().equalsIgnoreCase("block"))
            {
                List<Content> blockContent = node.getContent();
                list.add(new XMLOutputter().outputString(blockContent));
            }
        }

        return list;
    }
}
