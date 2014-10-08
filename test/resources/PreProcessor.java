package resources;

import com.care.framework.IPreProcessor;

public class PreProcessor implements IPreProcessor
{

	@Override
	public String PreProcess()
	{
		System.out.println("Method Called");

		return "Successful !!";
	}

}
