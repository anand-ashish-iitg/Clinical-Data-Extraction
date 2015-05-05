package sample.deidentifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import com.care.exception.ComponentException;
import com.care.framework.IDeIdentifier;

/**
 * Created by ARIHANT on 7/11/14.
 */
public class DeIdentifier implements IDeIdentifier
{
    ArffBuilder arffbuilder;

    private void writeClassifier(String outputFile, String trainData) throws Exception
    {

        Instances instances = arffbuilder.getArffInstance(trainData);
        J48 classifier = new J48();
        classifier.buildClassifier(instances);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(outputFile));
        objectOutputStream.writeObject(classifier);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    List<String> classify(J48 classifier, List<String> data) throws Exception
    {
        List<Instance> instances = arffbuilder.generateInstances(data);
        List<String> newList = new ArrayList<String>();
        for (int i = 0; i < instances.size(); i++)
        {
            Instance instance = instances.get(i);
            System.out.println(classifier.classifyInstance(instance));
            WordType classifiedType = WordType.values()[(int) classifier.classifyInstance(instance)];
            if (classifiedType != WordType.NON_PHI)
            {
                newList.add("<" + classifiedType.toString().toLowerCase() + ">" + data.get(i) + "</" + classifiedType.toString().toLowerCase() + ">");
            }
            else
            {
                newList.add(data.get(i));
            }
        }
        return newList;
    }

    public DeIdentifier()
    {
        String triggerWords[] = Constants.TRIGGER_WORDS;
        String bigrams[] = Constants.BIGRAMS;
        String headers[] = Constants.HEADERS;
        arffbuilder = new ArffBuilder(3, triggerWords, bigrams, headers);
    }

    @Override
    public List<String> DeIdentify(String data) throws ComponentException
    {
        throw new ComponentException("Not Implemented");
    }

    @Override
    public List<String> DeIdentify(List<String> data) throws ComponentException
    {
        ObjectInputStream objectInputStream;
        try
        {
            objectInputStream = new ObjectInputStream(new FileInputStream("de-id-j48.bin"));
            J48 j48 = (J48) objectInputStream.readObject();
            objectInputStream.close();
            return classify(j48, data);
        }
        catch (Exception e)
        {
            throw new ComponentException("Classifer not found");
        }
        
    }
    
    public static void main(String[] args) throws Exception
    {
        DeIdentifier deIdentifier = new DeIdentifier();
//        System.out.println("Start");
//        deIdentifier.writeClassifier("de-id-j48.bin", "train_s.xml");
//        System.out.println("Done");
        List<String> data = new ArrayList<String>();
        List<String> result = deIdentifier.DeIdentify( data);
        for(String res : result)
            System.out.println(res);
    }
}
