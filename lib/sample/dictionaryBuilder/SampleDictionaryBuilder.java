package sample.dictionaryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.care.exception.ComponentException;
import com.care.framework.IDictionaryBuilder;

public class SampleDictionaryBuilder implements IDictionaryBuilder
{
    @Override
    public Object BuildDictionary(List<String> data, Object dictionary) throws ComponentException
    {
        if (dictionary == null)
        {
            dictionary = new HashSet<String>();
        }
        System.out.println(dictionary.toString());
        ((Set<String>) dictionary).addAll(data);
        return dictionary;
    }
}
