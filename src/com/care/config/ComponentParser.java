package com.care.config;

import com.care.datatype.Component;
import com.care.datatype.ComponentLoadType;
import com.care.datatype.ComponentType;
import com.care.exception.ConfigException;
import com.google.common.base.Strings;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by AMIT on 7/10/14.
 */
public class ComponentParser
{

    /**
     * Parses config file and extracts
     * list of components object out of it
     *
     * @param node
     * @return
     * @throws ConfigException
     */
    public static Component GetComponent(Node node) throws ConfigException
    {
        Component component = new Component();
        NodeList children = node.getChildNodes();
        for (int j = 0; j < children.getLength(); j++)
        {
            Node childOfInput = children.item(j);
            if (childOfInput == null || Strings.isNullOrEmpty(childOfInput.getNodeName()))
            {
                continue;
            }

            // parsing load type of the component
            if (childOfInput.getNodeName().equalsIgnoreCase("loadType"))
            {
                String loadType = childOfInput.getTextContent();
                if (loadType.equalsIgnoreCase("class"))
                {
                    component.setLoadType(ComponentLoadType.CLASS);
                }
                else if (loadType.equalsIgnoreCase("jar"))
                {
                    component.setLoadType(ComponentLoadType.JAR);
                }
                else
                {
                    throw new ConfigException(loadType + " : is not supported");
                }
            }

            // parsing type of the component
            if (childOfInput.getNodeName().equalsIgnoreCase("type"))
            {
                String type = childOfInput.getTextContent();
                if (type.equalsIgnoreCase("pre_processor"))
                {
                    component.setType(ComponentType.PRE_PROCESSOR);
                }
                else if (type.equalsIgnoreCase("de_identifier"))
                {
                    component.setType(ComponentType.DE_IDENTIFIER);
                }
                else
                {
                    throw new ConfigException(type + " : is not supported");
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
                    throw new ConfigException("Path cannot be empty");
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
                    throw new ConfigException(className + " is not present.");
                }
            }

            // parsing dependency path of the component
            if (childOfInput.getNodeName().equalsIgnoreCase("dependencyPath"))
            {
                String dependencyPath = childOfInput.getTextContent();
                if (!Strings.isNullOrEmpty(dependencyPath))
                {
                    component.setDependencyPath(dependencyPath);
                }
                else
                {
                    throw new ConfigException(dependencyPath + " is not present.");
                }
            }
        }

        return component;
    }
}
