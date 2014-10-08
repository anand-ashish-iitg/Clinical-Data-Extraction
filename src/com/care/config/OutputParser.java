package com.care.config;

import com.care.datatype.GenerateOutputType;
import com.care.datatype.Output;
import com.care.datatype.OutputType;
import com.google.common.base.Strings;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by AMIT on 7/10/14.
 */
public class OutputParser
{
	private static Output output = new Output();

	public static Output GetOutput(Node node)
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
					// TODO throw exception
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
					// TODO throw exception
				}
			}
		}

		return output;
	}
}
