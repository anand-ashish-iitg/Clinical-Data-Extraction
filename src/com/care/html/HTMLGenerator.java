package com.care.html;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HTMLGenerator
{
    /**
     * Converts XML blob to HTML table content
     * Each identified concept is given a different row color as
     * according to the key specified
     * @param xmlFilePath
     * @return
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
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
                // TODO error is in above statement and that is happening 
                // because of percentage mark as then java thinks it as to be
                // a part of string.format just like %s
            }
        }
        htmlContent.append("</table></div></body>");
        htmlContent.append("<script src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js\"></script>");
        htmlContent.append("<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js\"></script>");
        htmlContent.append("</html>");
        return htmlContent.toString();
    }
    
    /**
     * Converts XML blob to HTML content
     * Each identified concept is given a different color as
     * according to the key specified
     * @param xmlFilePath
     * @return
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static String getDiffHTML(String xmlFilePath) throws SAXException, IOException, ParserConfigurationException
    {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new File(xmlFilePath));
        
        // Iterating through the nodes and extracting the data.
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><link href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css\" rel=\"stylesheet\">");
        htmlContent.append("<body><div class=\"container\">");
        
        // COLOR KEY
        htmlContent.append("<p><font color=\"red\">PROBLEM</font></br>");
        htmlContent.append("<font color=\"blue\">TREATMENT</font></br>");
        htmlContent.append("<font color=\"green\">TEST</font></br>");
        htmlContent.append("<font color=\"black\">NONE</font></br></p>");
        
        htmlContent.append("<p><font color=\"brown\">PATIENT</font></br>");
        htmlContent.append("<font color=\"cadetblue\">DOCTOR</font></br>");
        htmlContent.append("<font color=\"chartreuse \">ID</font></br>");
        htmlContent.append("<p><font color=\"darkslateblue\">AGE</font></br>");
        htmlContent.append("<font color=\"darkviolet\">DATE</font></br>");
        htmlContent.append("<font color=\"forestgreen\">LOCATION</font></br>");
        htmlContent.append("<p><font color=\"hotpink\">HOSPITAL</font></br>");
        htmlContent.append("<font color=\"lightblue\">PHONE</font></br>");

        // CSS FORMATTING
        htmlContent.append("<style>problem{color:red} treatment{color:blue} test{color:green}</style>");
        htmlContent.append("<style>patient{color:brown} doctor{color:cadetblue} id{color:chartreuse} age{color:darkslateblue} date{color:darkviolet} location{color:forestgreen} hospital{color:hotpink} phone{color:lightblue}</style>");

        htmlContent.append("<p>");
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            if (node.getNodeName().equalsIgnoreCase("block"))
            {
                String tableRow = "";
                NodeList tagList = node.getChildNodes();
                for (int j = 0; j < tagList.getLength(); j++)
                {
                    // change font tag to span
                    Node tagNode = tagList.item(j);
                    tableRow +=" <span>" + tagNode.getTextContent() + "</span> ";
                }
                htmlContent.append(tableRow);
            }
        }
        htmlContent.append("</p></div></body>");
        
        // JAVASCRIPT
        htmlContent.append("<script src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js\"></script>");
        htmlContent.append("<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js\"></script>");
        
        htmlContent.append("</html>");
        
        return htmlContent.toString();
    }
}
