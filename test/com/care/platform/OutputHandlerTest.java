package com.care.platform;

import com.care.datatype.GenerateOutputType;
import com.care.datatype.Output;
import com.care.datatype.OutputType;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMIT on 8/10/14.
 */
public class OutputHandlerTest
{
	@Test
	public void WriteStringToNewFile() throws IOException
	{
		String path = "abc.txt";
		String content = "HelloWorld!";

		Output output = new Output();
		output.setType(OutputType.FILE);
		output.setPath(path);
		output.setGenerateType(GenerateOutputType.STRING);

		OutputHandler handler = new OutputHandler(output);
		try
		{
			handler.WriteStringToFile(content);
		}
		catch (IOException e)
		{
			Assert.fail(e.toString());
		}

		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String actualContent = new String(data);
		Assert.assertEquals(content, actualContent);

		new File(path).delete();
	}

	@Test
	public void testWriteListToFile() throws Exception
	{
		String path = "abc.txt";
		List<String> content = new ArrayList<String>();
		content.add("Yes");
		content.add("<tag>hello</tag>world");

		Output output = new Output();
		output.setType(OutputType.FILE);
		output.setPath(path);
		output.setGenerateType(GenerateOutputType.LIST);

		OutputHandler handler = new OutputHandler(output);
		try
		{
			handler.WriteListToFile(content);
		}
		catch (IOException e)
		{
			Assert.fail(e.toString());
		}

		new File(path).delete();
	}
}
