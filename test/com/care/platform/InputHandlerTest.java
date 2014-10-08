package com.care.platform;

import com.care.datatype.Input;
import com.care.datatype.InputType;
import com.care.datatype.ParseInputType;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Scanner;

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

		// TODO see if better way to do this
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
		input.setPath(".\\test\\resources\\InputFile.txt");
		input.setParseType(ParseInputType.STRING);

		handler = new InputHandler(input);

		// TODO see if better way to do this
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
	public void ConvertValidXmlStringToList() throws Exception
	{
		Input input = new Input();
		input.setType(InputType.XML);
		input.setPath(".\\test\\resources\\ValidXmlFile.xml");
		input.setParseType(ParseInputType.LIST);

		handler = new InputHandler(input);

		// TODO see if better way to do this
		try
		{
			String content = handler.ReadFile();
			List<String> list = handler.ConvertXmlStringToList(content);

			Assert.assertEquals(4, list.size());
			for(String a : list)
			{
				System.out.println(a);
			}
		}
		catch (IOException e)
		{
			Assert.fail(e.toString());
		}
	}

	@Test
	public void ConvertInValidXmlStringToList() throws Exception
	{

	}
}
