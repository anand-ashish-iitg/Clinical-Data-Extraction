package com.care.platform;

import com.care.datatype.Component;
import com.care.datatype.ComponentType;
import com.care.framework.IPreProcessor;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class PlatformManager
{

	private Object componentInstance;

	private Object DoWork(ComponentType componentType, Object inputContent)
	{
		if (componentType == ComponentType.PRE_PROCESSOR)
		{
			if (this.componentInstance instanceof IPreProcessor)
			{
				IPreProcessor preProcessor = (IPreProcessor) this.componentInstance;

				// TODO call specific method as specified in config
				return preProcessor.PreProcess((String) inputContent);
			}
			else
			{
				// TODO throw exception
				// TODO log errors
			}
		}

		return null;
	}

	public Object StartComponent(Component component, Object inputContent)
	{
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

			// Doing component type specific work
			return this.DoWork(component.getType(), inputContent);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// TODO throw exception
		}

		return null;
	}
}
