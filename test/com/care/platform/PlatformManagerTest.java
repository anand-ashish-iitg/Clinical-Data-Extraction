package com.care.platform;

import com.care.datatype.Component;
import com.care.datatype.ComponentType;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by AMIT on 8/10/14.
 */
public class PlatformManagerTest
{
	@Test
	public void testStartComponent()
	{
		Component component = new Component();
		component.setType(ComponentType.PRE_PROCESSOR);
		component.setPath(".\\bin\\resources");
		component.setClassName("resources.PreProcessor");

		String inputContent = "Hello";

		PlatformManager manager = new PlatformManager();
		String output = (String) manager.StartComponent(component, inputContent);

		Assert.assertEquals("HELLO", output);
	}
}
