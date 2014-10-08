package resources;

import com.care.framework.IPreProcessor;

public class PreProcessor implements IPreProcessor
{

	@Override
	public String PreProcess(String data)
	{
		return data.toUpperCase();
	}

}
