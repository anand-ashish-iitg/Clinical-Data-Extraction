package com.care.platform;

import com.care.datatype.Component;
import com.care.datatype.ComponentType;
import com.care.framework.IPreProcessor;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class PlatformManager
{
	private Object componentInstance;
	private Component component;

	public List<String> DoWork(String inputContent)
	{
		ComponentType componentType = component.getType();

		if (componentType == ComponentType.PRE_PROCESSOR)
		{
			if (this.componentInstance instanceof IPreProcessor)
			{
				IPreProcessor preProcessor = (IPreProcessor) this.componentInstance;

				return preProcessor.PreProcess(inputContent);
			}
			else
			{
				// TODO throw exception
				// TODO log errors
			}
		}

		return null;
	}

	public List<String> DoWork(List<String> inputContent)
	{
		ComponentType componentType = component.getType();

		if (componentType == ComponentType.PRE_PROCESSOR)
		{
			if (this.componentInstance instanceof IPreProcessor)
			{
				IPreProcessor preProcessor = (IPreProcessor) this.componentInstance;

				return preProcessor.PreProcess(inputContent);
			}
			else
			{
				// TODO throw exception
				// TODO log errors
			}
		}

		return null;
	}

	public void InitializeComponent(Component component)
	{
		this.component = component;

		// Create a File object on the root of the directory containing the class file
		File file = new File(component.getPath());

		try
		{
			// Convert File to a URL
			URL url = file.toURL();
			URL[] urls = new URL[]{url};

			// Create a new class loader with the directory
			ClassLoader loader = new URLClassLoader(urls);

			// Loading the class
			Class componentClass = loader.loadClass(component.getClassName());
			this.componentInstance = componentClass.newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// TODO throw exception
		}
	}
}
