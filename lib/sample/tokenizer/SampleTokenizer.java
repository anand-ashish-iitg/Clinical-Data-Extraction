package sample.tokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import com.care.exception.ComponentException;
import com.care.framework.ITokenizer;

/**
 * Created by AMIT on 7/11/14.
 */
public class SampleTokenizer implements ITokenizer
{
    @Override
    public List<String> Tokenize(String data) throws ComponentException
    {
        String tokens[] = null;
        InputStream is = null;
        try
        {
            String s =  URLDecoder.decode(getClass().getResource("en-token.bin").getFile().toString(), "UTF-8");
            File file = new File(s);
            System.out.println(file.exists());
            is = new FileInputStream(file);
            TokenizerModel model = null;
            model = new TokenizerModel(is);
            Tokenizer tokenizer = new TokenizerME(model);
            tokens = tokenizer.tokenize(data);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Arrays.asList(tokens);
    }

    @Override
    public List<String> Tokenize(List<String> data) throws ComponentException
    {
        StringBuilder inputData = new StringBuilder();
        for(String s : data){
            inputData.append(s+" ");
        }
        
        String tokens[] = null;
        InputStream is = null;
        try
        {
            is = new FileInputStream("en-token.bin");
            TokenizerModel model = null;
            model = new TokenizerModel(is);
            Tokenizer tokenizer = new TokenizerME(model);
            tokens = tokenizer.tokenize(inputData.toString());
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Arrays.asList(tokens);
    }
}
