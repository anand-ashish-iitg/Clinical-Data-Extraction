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

	private void DoWork(ComponentType componentType)
	{
		if (componentType == ComponentType.PRE_PROCESSOR)
		{
			if (this.componentInstance instanceof IPreProcessor)
			{
				// TODO call specific method as specified in config
				System.out.println("Successful");
				IPreProcessor objT = (IPreProcessor) this.componentInstance;

				System.out.print(objT.PreProcess());
			}
			else
			{
				// TODO throw exception
				// log errors
			}
		}
	}

	public void StartComponent(Component component)
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
			this.DoWork(component.getType());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
