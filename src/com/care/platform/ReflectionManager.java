package com.care.platform;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.care.framework.IPreProcessor;

public class ReflectionManager {

	public static void main(String[] args) {
		// Create a File object on the root of the directory containing the class file
		File file = new File("C:\\Users\\AMIT\\Dropbox\\BTP\\ClinicalDataExtraction\\bin\\com\\care\\platform\\");

		try {
		    // Convert File to a URL
		    URL url = file.toURL();          // file:/c:/myclasses/
		    URL[] urls = new URL[]{url};

		    // Create a new class loader with the directory
		    ClassLoader cl = new URLClassLoader(urls);

		    // Load in the class; MyClass.class should be located in
		    // the directory file:/c:/myclasses/com/mycompany
		    Class cls = cl.loadClass("com.care.platform.PreProcessor");
		    Object obj = cls.newInstance();
		    
		    if(obj instanceof IPreProcessor){
		    	System.out.println("Successful");
		    	IPreProcessor objT = (IPreProcessor)obj;
		    	
		    	System.out.print(objT.PreProcess());
		    }
		    	
		} catch (MalformedURLException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
