package sample.stopWordsRemover;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

import com.care.exception.ComponentException;
import com.care.framework.IStopWordRemover;

public class SampleStopWordsRemover implements IStopWordRemover
{
    @Override
    public List<String> RemoveStopWords(String data) throws ComponentException
    {
        List<String> result = new ArrayList<String>();
        CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();
        TokenStream tokenStream = new StandardTokenizer(new StringReader(data));

        tokenStream = new StopFilter(tokenStream, stopWords);
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try
        {
            tokenStream.reset();
            while (tokenStream.incrementToken())
            {
                result.add(charTermAttribute.toString());
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> RemoveStopWords(List<String> data) throws ComponentException
    {
        List<String> result = new ArrayList<String>();

        CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();
        TokenStream tokenStream = new ListTokenStream(data);

        tokenStream = new StopFilter(tokenStream, stopWords);
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try
        {
            tokenStream.reset();
            while (tokenStream.incrementToken())
            {
                result.add(charTermAttribute.toString());
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    private class ListTokenStream extends TokenStream
    {
        public ListTokenStream(List<String> data)
        {
            tokens = data;
            reset();
        }

        private List<String> tokens;
        private int index;
        private int offset;

        @Override
        public void reset()
        {
            index = 0;
            offset = 0;
        }

        @Override
        public boolean incrementToken() throws IOException
        {
            clearAttributes();
            if (index == tokens.size())
            {
                return false;
            }
            else
            {
                String tkn = tokens.get(index);
                int len = tkn.length();
                index++;
                offsetAtt.setOffset(offset, offset + len - 1);
                posIncrAtt.setPositionIncrement(1);
                termAtt.setEmpty();
                termAtt.resizeBuffer(len);
                termAtt.append(tkn);
                offset += len;
                return true;
            }
        }

        private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
        private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
        private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
    }
}
