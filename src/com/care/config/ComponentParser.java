package com.care.config;

import com.care.datatype.Component;
import com.care.datatype.ComponentType;
import com.care.exception.ComponentException;
import com.google.common.base.Strings;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by AMIT on 7/10/14.
 */
public class ComponentParser
{
    private static Component component = new Component();

    public static Component GetComponent(Node node) throws ComponentException
    {
        NodeList children = node.getChildNodes();
        for (int j = 0; j < children.getLength(); j++)
        {
            Node childOfInput = children.item(j);
            if (childOfInput == null || Strings.isNullOrEmpty(childOfInput.getNodeName()))
            {
                continue;
            }

            // parsing type of the component
            if (childOfInput.getNodeName().equalsIgnoreCase("type"))
            {
                String type = childOfInput.getTextContent();
                if (type.equalsIgnoreCase("preprocessor"))
                {
                    component.setType(ComponentType.PRE_PROCESSOR);
                }
                else if (type.equalsIgnoreCase("deidentifier"))
                {
                    component.setType(ComponentType.DE_IDENTIFIER);
                }
                else
                {
                    throw new ComponentException(type + " : is not supported");
                }
            }

            // parsing path of the component
            if (childOfInput.getNodeName().equalsIgnoreCase("path"))
            {
                String path = childOfInput.getTextContent();
                if (!Strings.isNullOrEmpty(path))
                {
                    component.setPath(path);
                }
                else
                {
                    throw new ComponentException("Path cannot be empty");
                }
            }

            // parsing class-name of the component
            if (childOfInput.getNodeName().equalsIgnoreCase("className"))
            {
                String className = childOfInput.getTextContent();
                if (!Strings.isNullOrEmpty(className))
                {
                    component.setClassName(className);
                }
                else
                {
                    throw new ComponentException(className + " is not present.");
                }
            }

            // TODO do for rest of the parameters
        }

        return component;
    }
}
