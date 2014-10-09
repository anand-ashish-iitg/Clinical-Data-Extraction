package resources;

import com.care.framework.IPreProcessor;

import java.util.ArrayList;
import java.util.List;

public class PreProcessor implements IPreProcessor
{
	@Override
	public List<String> PreProcess(List<String> data)
	{
		return null;
	}

	@Override
	public List<String> PreProcess(String data)
	{
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add(data.toUpperCase());
		list.add("c");

		return list;
	}


}
