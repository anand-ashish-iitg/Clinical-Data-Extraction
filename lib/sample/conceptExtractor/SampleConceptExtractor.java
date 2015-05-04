package sample.conceptExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private String ParsePropertiesFile(String propertiesLocation)
    {
        File file = new File(propertiesLocation);
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String path = null;
            
            while ((line = br.readLine()) != null) {
                if(line.contains("location"))
                {
                    String[] tokens = line.split("=");
                    path = tokens[1];
                    break;
                }
            }
            
            if(path != null)
            {
                byte[] encoded = Files.readAllBytes(Paths.get(path));
                return new String(encoded, Charset.defaultCharset());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<String> ExtractConcepts(String data) throws ComponentException
    {
        List<String> taggedText = new ArrayList<String>();
        try
        {
            String crfLocation = URLDecoder.decode(getClass().getResource("CRF_Model.txt").getFile().toString(), "UTF-8");
            String propertiesLocation = URLDecoder.decode(getClass().getResource("properties.txt").getFile().toString(), "UTF-8");
            File file = new File(crfLocation);
        
            Sentence s = Sentence.loadFromPiped(null, this.ParsePropertiesFile(propertiesLocation));
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
