package sample.deidentifier;

import java.util.ArrayList;
import java.util.List;

import com.care.exception.ComponentException;
import com.care.framework.IDeIdentifier;

/**
 * Created by AMIT on 7/11/14.
 */
public class SampleDeIdentifier implements IDeIdentifier
{
    @Override
    public List<String> DeIdentify(String data) throws ComponentException
    {
        throw new ComponentException("Not Implemented");
    }

    @Override
    public List<String> DeIdentify(List<String> data) throws ComponentException
    {
        List<String> newList = new ArrayList<String>();

        List<String> nameDict = new ArrayList<String>();
        nameDict.add("ross");
        nameDict.add("chandler");
        nameDict.add("joey");
        nameDict.add("monica");

        List<String> placesDict = new ArrayList<String>();
        placesDict.add("guwahati");
        placesDict.add("delhi");
        placesDict.add("hyderabad");
        placesDict.add("india");

        for (String str : data)
        {
            String newStr = str;
            if (nameDict.contains(str.toLowerCase()))
            {
                newStr = "<name>" + newStr + "</name>";
            }
            else if (placesDict.contains(str.toLowerCase()))
            {
                newStr = "<place>" + newStr + "</place>";
            }

            newList.add(newStr);
        }

        return newList;
    }
}
