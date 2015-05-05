package sample.stemmer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.PorterStemmer;

import com.care.exception.ComponentException;
import com.care.framework.IStemmer;

public class SampleStemmer implements IStemmer
{
    PorterStemmer porterStemmer = new PorterStemmer();

    @Override
    public List<String> Stem(String data) throws ComponentException
    {
        List<String> result = new ArrayList<String>();
        TokenStream tokenStream = new StandardTokenizer(new StringReader(data));
        tokenStream = new PorterStemFilter(tokenStream);
        try
        {
            tokenStream.reset();
            CharTermAttribute charTermAttr = tokenStream.getAttribute(CharTermAttribute.class);
            while (tokenStream.incrementToken())
            {
                result.add(charTermAttr.toString());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> Stem(List<String> data) throws ComponentException
    {
        List<String> result = new ArrayList<String>();
        for (String s : data)
        {
            porterStemmer.setCurrent(s);
            porterStemmer.stem();
            result.add(porterStemmer.getCurrent());
        }
        return result;
    }
}
