package sample.conceptExtractor;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import banner.Sentence;
import banner.tagging.CRFTagger;
import banner.tagging.Mention;
import banner.tagging.TaggedToken;
import banner.tagging.TaggedToken.TagFormat;
import banner.tokenization.Token;

import com.care.exception.ComponentException;
import com.care.framework.IConceptExtractor;

/**
 * Created by AMIT on 2/5/15.
 */
public class SampleConceptExtractor implements IConceptExtractor
{
    public List<String> ExtractConcepts(String data) throws ComponentException
    {
        String crfLocation;
        List<String> taggedText = new ArrayList<String>();
        try
        {
            crfLocation = URLDecoder.decode(getClass().getResource("CRF_Model.txt").getFile().toString(), "UTF-8");
            File file = new File(crfLocation);
        
            Sentence s = Sentence.loadFromPiped(null,   "The|B-test review|I-test of|I-test systems|I-test is|O negative|O ,|O except|O as|O above|O .|O \n" +
                                                        "ALLERGIES|O :|O \n" +  
                                                        "The|O patient|O has|O no|O known|B-problem drug|I-problem allergies|I-problem .|O  ");
            CRFTagger t = CRFTagger.load(file, null, null);
            t.tag(s);
            
            List<Token> tokens = s.getTokens();
            int size = tokens.size();
            for(int index = 0; index < size; ++ index)
            {
                Token token = tokens.get(index);
                String text = token.getText();
                boolean ifConcept = (s.getMentions(token).size() > 0);
                
                if(ifConcept)
                {
                    Mention m = s.getMentions(token).get(0);
                    String tag = m.getType().getText();
                    taggedText.add("<"+tag+">" + m.getText() +"</"+tag+">");
                    while(m.contains(index))
                    {
                        ++index;
                    }
                    --index;
                }
                else
                {
                    taggedText.add(text);
                }
            }
        }
        catch (Exception e)
        {
            throw new ComponentException(e);
        }
        
        return taggedText;
    }

    public List<String> ExtractConcepts(List<String> data) throws ComponentException
    {
        return null;
    }
}
