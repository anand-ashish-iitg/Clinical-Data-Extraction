package com.care.platform;

import com.care.datatype.Component;
import com.care.datatype.ComponentType;
import com.care.exception.ComponentException;
import com.care.framework.IPreProcessor;
import com.google.common.base.Strings;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PlatformManager
{
    private Object componentInstance;
    private Component component;

    public List<String> DoWork(String inputContent) throws ComponentException
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
                throw new ComponentException("IPreProcessor interface not implemented");
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

    public void InitializeClassComponent(Component component)
    {
        System.out.println("h");
        this.component = component;

        // Create a File object on the root of the directory containing
        // the class file
        File file = new File(component.getPath());

        try
        {
            // Convert File to a URL
            URL url = file.toURL();
            URL[] urls;
            if(Strings.isNullOrEmpty(component.getDependencyPath()))
            {
                urls = new URL[] { url };
            }
            else
            {
                urls = new URL[] { new URL("jar:file:" + component.getDependencyPath() + "!/"), url };
            }

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

    public void InitializeJarComponent(Component component)
    {
        this.component = component;

        try
        {
            JarFile jarFile = new JarFile(component.getPath());
            Enumeration e = jarFile.entries();

            URL[] urls = new URL[] { new URL("jar:file:" + component.getDependencyPath() + "!/")};

            // Create a new class loader with the directory
            ClassLoader loader = new URLClassLoader(urls);

            // Loading the class
            while (e.hasMoreElements())
            {
                JarEntry je = (JarEntry) e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class"))
                {
                    continue;
                }
                // -6 because of .class
                String className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');

                if (className.equalsIgnoreCase(component.getClassName()))
                {
                    Class componentClass = loader.loadClass(component.getClassName());
                    this.componentInstance = componentClass.newInstance();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // TODO throw exception
        }
    }
}
