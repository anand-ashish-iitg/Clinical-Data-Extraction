package com.care.html;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.care.config.ComponentParser;
import com.care.config.InputParser;
import com.care.config.OutputParser;
import com.care.datatype.Component;
import com.care.exception.ConfigException;

public class HTMLGenerator
{
    public static String getHTML(String xmlFilePath) throws SAXException, IOException, ParserConfigurationException
    {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new File(xmlFilePath));
        
        // Iterating through the nodes and extracting the data.
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        StringBuilder htmlContent =new StringBuilder();
        htmlContent.append("<html><link href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css\" rel=\"stylesheet\">");
        htmlContent.append("<body><div class=\"container\">");
        htmlContent.append("<table class=\"table table-hover table-bordered\">");
        htmlContent.append("<thead><tr><th>Word</th><th>Type</th></tr></thead>");
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            if (node.getNodeName().equalsIgnoreCase("block"))
            {
                String tableRow = "<tr class=\"%s\">";
                String className ="";
                NodeList tagList = node.getChildNodes();
                for (int j = 0; j < tagList.getLength(); j++)
                {
                    Node tagNode = tagList.item(j);
                    tableRow +="<td>" + tagNode.getTextContent() +"</td>";
                    if (tagNode.getNodeName().equalsIgnoreCase("name"))
                    {
                        tableRow +="<td>" + "Name" +"</td>";
                        className = "success";
                    }else if (tagNode.getNodeName().equalsIgnoreCase("place"))
                    {
                        tableRow +="<td>" + "Place" +"</td>";
                        className = "info";
                    }
                }
                tableRow += "</tr>";
                htmlContent.append(String.format(tableRow,className));
            }
        }
        htmlContent.append("</table></div></body>");
        htmlContent.append("<script src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js\"></script>");
        htmlContent.append("<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js\"></script>");
        htmlContent.append("</html>");
        return htmlContent.toString();
    }
}
