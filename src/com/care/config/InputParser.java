package com.care.config;

import com.care.datatype.Input;
import com.care.datatype.InputType;
import com.care.datatype.ParseInputType;
import com.care.exception.ConfigException;
import com.google.common.base.Strings;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by AMIT on 7/10/14.
 */
public class InputParser
{
    private static Input input = new Input();

    /**
     * Parses config file and extracts
     * input object out of it
     *
     * @param node
     * @return
     * @throws ConfigException
     */
    public static Input GetInput(Node node) throws ConfigException
    {
        NodeList children = node.getChildNodes();
        for (int j = 0; j < children.getLength(); j++)
        {
            Node childOfInput = children.item(j);
            if (childOfInput == null || Strings.isNullOrEmpty(childOfInput.getNodeName()))
            {
                continue;
            }

            // parsing type of the input
            if (childOfInput.getNodeName().equalsIgnoreCase("type"))
            {
                String typeValue = childOfInput.getTextContent();
                if (typeValue.equalsIgnoreCase("folder"))
                {
                    input.setType(InputType.FOLDER);
                }
                else if (typeValue.equalsIgnoreCase("file"))
                {
                    input.setType(InputType.FILE);
                }
                else
                {
                    throw new ConfigException(typeValue + " format of files not supported");
                }
            }

            // parsing path of the input
            if (childOfInput.getNodeName().equalsIgnoreCase("path"))
            {
                String pathValue = childOfInput.getTextContent();
                if (!Strings.isNullOrEmpty(pathValue))
                {
                    input.setPath(pathValue);
                }
                else
                {
                    throw new ConfigException("Path cannot be empty");
                }
            }

            // parsing input parse type
            if (childOfInput.getNodeName().equalsIgnoreCase("parseType"))
            {
                String parseType = childOfInput.getTextContent();
                if (parseType.equalsIgnoreCase("string"))
                {
                    input.setParseType(ParseInputType.STRING);
                }
                else if (parseType.equalsIgnoreCase("list"))
                {
                    input.setParseType(ParseInputType.LIST);
                }
                else
                {
                    throw new ConfigException(parseType + " parsing of files not supported");
                }
            }
        }

        return input;
    }
}
