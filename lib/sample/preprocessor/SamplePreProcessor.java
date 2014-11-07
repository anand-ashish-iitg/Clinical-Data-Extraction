package sample.preprocessor;

import com.care.exception.ComponentException;
import com.care.framework.IPreProcessor;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMIT on 7/11/14.
 */
public class SamplePreProcessor implements IPreProcessor
{
    @Override
    public List<String> PreProcess(String data) throws ComponentException
    {
        List<String> tokens = new ArrayList<String>();
        BreakIterator bi = BreakIterator.getWordInstance();
        bi.setText(data);
        int begin = bi.first();
        int end;
        for (end = bi.next(); end != BreakIterator.DONE; end = bi.next())
        {
            String t = data.substring(begin, end);
            if (t.trim().length() > 0)
            {
                tokens.add(data.substring(begin, end));
            }
            begin = end;
        }

        if (end != -1)
        {
            tokens.add(data.substring(end));
        }

        return tokens;
    }

    @Override
    public List<String> PreProcess(List<String> data) throws ComponentException
    {
        return data;
    }
}
