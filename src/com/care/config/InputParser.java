package com.care.config;

import com.google.common.base.Strings;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by AMIT on 7/10/14.
 */
public class InputParser {
	private static Input input = new Input();

	public static Input GetInput(Node node){
		NodeList children = node.getChildNodes();
		for (int j = 0; j < children.getLength(); j++) {
			Node childOfInput = children.item(j);
			if(childOfInput == null || Strings.isNullOrEmpty(childOfInput.getNodeName()))
				continue;

			// parsing type of the input
			if (childOfInput.getNodeName().equalsIgnoreCase("type")) {
				String typeValue = childOfInput.getTextContent();
				if(typeValue.equalsIgnoreCase("file"))
					input.setType(InputType.FILE);
				else if(typeValue.equalsIgnoreCase("xml"))
					input.setType(InputType.XML);
				else{
					// TODO throw exception
				}
			}

			// parsing path of the input
			if (childOfInput.getNodeName().equalsIgnoreCase("path")) {
				String pathValue = childOfInput.getTextContent();
				if(!Strings.isNullOrEmpty(pathValue)){
					input.setPath(pathValue);
				}
			}
		}

		return input;
	}
}
