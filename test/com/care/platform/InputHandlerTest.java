package com.care.platform;

import com.care.datatype.Input;
import com.care.datatype.InputType;
import com.care.datatype.ParseInputType;
import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

/**
 * Created by AMIT on 8/10/14.
 */
public class InputHandlerTest
{
    private static InputHandler handler;

    /**
     * when file does not exist
     */
    @Test
    public void ReadFileNotExisting()
    {
        Input input = new Input();
        input.setType(InputType.FILE);
        input.setPath(".\\test\\resources\\Input.txt");
        input.setParseType(ParseInputType.STRING);

        handler = new InputHandler(input);

        try
        {
            handler.ReadFile();

            Assert.fail("IOException expected");
        }
        catch (IOException e)
        {
            Assert.assertEquals(NoSuchFileException.class, e.getClass());
        }
    }

    /**
     * where file exists
     */
    @Test
    public void ReadExistingFile()
    {
        Input input = new Input();
        input.setType(InputType.FILE);
        input.setPath("./test/resources/InputFile.txt");
        input.setParseType(ParseInputType.STRING);

        handler = new InputHandler(input);

        try
        {
            String content = handler.ReadFile();

            Assert.assertEquals("Hello World", content);
        }
        catch (IOException e)
        {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void ConvertValidXmlStringToList()
    {
        Input input = new Input();
        input.setType(InputType.XML);
        input.setPath("./test/resources/ValidXmlFile.xml");
        input.setParseType(ParseInputType.LIST);

        handler = new InputHandler(input);
        try
        {
            String content = handler.ReadFile();
            List<String> list = handler.ConvertXmlStringToList(content);

            Assert.assertEquals(4, list.size());
        }
        catch (IOException e)
        {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void ConvertInValidXmlStringToList() throws Exception
    {
        // TODO implement after standardizing the exceptions
    }
}
