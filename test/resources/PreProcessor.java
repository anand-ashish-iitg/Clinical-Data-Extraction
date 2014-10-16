package resources;

import com.care.framework.IPreProcessor;

import java.util.Arrays;
import java.util.List;

public class PreProcessor implements IPreProcessor
{
    @Override
    public List<String> PreProcess(List<String> data)
    {
        return data;
    }

    @Override
    public List<String> PreProcess(String data)
    {
        String[] tokens = data.split(" ");
        List<String> list = Arrays.asList(tokens);

        return list;
    }
}