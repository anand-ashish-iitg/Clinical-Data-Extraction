package sample.conceptExtractor;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import banner.Sentence;
import banner.tagging.CRFTagger;
import banner.tagging.Mention;

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
        try
        {
            crfLocation = URLDecoder.decode(getClass().getResource("CRF_Model.txt").getFile().toString(), "UTF-8");
            File file = new File(crfLocation);
        
            Sentence s = Sentence.loadFromPiped(null, "inguinal|O adenopathy|O was|O no|O appreciable|O cervical|O ,|O supraclavicular|O ,|O axillary|O ,|O or|O inguinal|O adenopathy|O .|O ");
            CRFTagger t = CRFTagger.load(file, null, null);
            t.tag(s);
            List<Mention> m = s.getMentions();
            for (Mention mention : m)
            {
                System.out.println(mention);
            }
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }

    public List<String> ExtractConcepts(List<String> data) throws ComponentException
    {
        return null;
    }
}
