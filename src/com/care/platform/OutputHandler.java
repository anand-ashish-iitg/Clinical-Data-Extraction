package com.care.platform;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.freedesktop.bindings.Environment;

import com.care.datatype.Output;
import com.google.common.html.HtmlEscapers;
import com.google.common.xml.XmlEscapers;

/**
 * Created by AMIT on 8/10/14.
 */
public class OutputHandler
{
    private Output output;

    public OutputHandler(Output output)
    {
        this.output = output;
    }

    /**
     * Writes string to a file
     *
     * @param content String
     */
    public void WriteStringToFile(String content) throws IOException
    {
        File file = new File(output.getPath());
        if (!file.exists())
        {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }

    /**
     * Writes List<string> to a file separated by <block></block>
     *
     * @param content list of XMLStrings
     */
    public void WriteListToFileAsXml(List<String> content) throws IOException
    {
        File file = new File(output.getPath());
        if (!file.exists())
        {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        bw.append("<output>");
        for (String line : content)
        {
            bw.append("<block>" + XmlEscapers.xmlContentEscaper().escape(line) + "</block>\n");
        }
        bw.append("</output>");

        bw.close();
    }

    /**
     * Writes List<string> to a file
     *
     * @param content list of XMLStrings
     */
    public void WriteListToFileAsString(List<String> content) throws IOException
    {
        File file = new File(output.getPath());
        if (!file.exists())
        {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        for (String line : content)
        {
            bw.append(line + " ");
        }

        bw.close();
    }
}
