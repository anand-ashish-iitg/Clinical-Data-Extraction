package com.care.config;

import com.care.datatype.GenerateOutputType;
import com.care.datatype.Output;
import com.care.datatype.OutputType;
import com.care.exception.ConfigException;
import com.google.common.base.Strings;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by AMIT on 7/10/14.
 */
public class OutputParser
{
        private static Output output = new Output();

        public static Output GetOutput(Node node) throws ConfigException
        {
                NodeList children = node.getChildNodes();
                for (int j = 0; j < children.getLength(); j++)
                {
                        Node childOfOutput = children.item(j);
                        if (childOfOutput == null || Strings.isNullOrEmpty(childOfOutput.getNodeName()))
                        {
                                continue;
                        }

                        // parsing type of the output
                        if (childOfOutput.getNodeName().equalsIgnoreCase("type"))
                        {
                                String typeValue = childOfOutput.getTextContent();
                                if (typeValue.equalsIgnoreCase("file"))
                                {
                                        output.setType(OutputType.FILE);
                                }
                                else if (typeValue.equalsIgnoreCase("xml"))
                                {
                                        output.setType(OutputType.XML);
                                }
                                else
                                {
                                        throw new ConfigException(typeValue + " format of files not supported");
                                }
                        }

                        // parsing path of the output
                        if (childOfOutput.getNodeName().equalsIgnoreCase("path"))
                        {
                                String pathValue = childOfOutput.getTextContent();
                                if (!Strings.isNullOrEmpty(pathValue))
                                {
                                        output.setPath(pathValue);
                                }
                                else
                                {
                                        throw new ConfigException("Path cannot be empty");
                                }
                        }

                        // parsing output generate type
                        if (childOfOutput.getNodeName().equalsIgnoreCase("generateType"))
                        {
                                String parseType = childOfOutput.getTextContent();
                                if (parseType.equalsIgnoreCase("string"))
                                {
                                        output.setGenerateType(GenerateOutputType.STRING);
                                }
                                else if (parseType.equalsIgnoreCase("list"))
                                {
                                        output.setGenerateType(GenerateOutputType.LIST);
                                }
                                else
                                {
                                        throw new ConfigException(parseType + " parsing of files not supported");
                                }
                        }
                }

                return output;
        }
}
