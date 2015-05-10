package sample.deidentifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;

import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class ArffBuilder
{
    FastVector attributes;
    Instances data;
    Integer windowSize;
    HashMap<String, Integer> frequencyCount;
    String[] triggerWords, bigrams, headers;
    TokenizerME tokenizer;

    public ArffBuilder(Integer windowSize, String[] triggerWords, String[] bigrams, String[] headers)
    {
        super();
        this.windowSize = windowSize;
        this.triggerWords = triggerWords;
        this.bigrams = bigrams;
        this.headers = headers;
        frequencyCount = new HashMap<String, Integer>();
        setupAttributes();
        setupData();
        setupTokenizer();
    }

    private void setupAttributes()
    {
        attributes = new FastVector();
        attributes.addElement(new Attribute("caps"));
        attributes.addElement(new Attribute("small"));
        attributes.addElement(new Attribute("cap-small"));
        attributes.addElement(new Attribute("length"));
        attributes.addElement(new Attribute("freq"));
        attributes.addElement(new Attribute("wordPosition"));

        FastVector textFormatTypes = TextFormat.getFastVector();
        attributes.addElement(new Attribute("text-format", textFormatTypes));
        FastVector numberFormatTypes = NumberFormat.getFastVector();
        attributes.addElement(new Attribute("number-format", numberFormatTypes));

        FastVector expectedWordTypes = WordType.getFastVector();
        attributes.addElement(new Attribute("expected", expectedWordTypes));
        for (int i = 0; i < windowSize; i++)
        {
            attributes.addElement(new Attribute("word-" + i + "-class", expectedWordTypes));
        }
        for (int i = 0; i < windowSize; i++)
        {
            attributes.addElement(new Attribute("word+" + i + "-class", expectedWordTypes));
        }
        for (int i = 0; i < triggerWords.length; i++)
        {
            attributes.addElement(new Attribute(triggerWords[i]));
        }
        for (int i = 0; i < bigrams.length; i++)
        {
            attributes.addElement(new Attribute(bigrams[i]));
        }
        for (int i = 0; i < headers.length; i++)
        {
            attributes.addElement(new Attribute(headers[i]));
        }
        attributes.addElement(new Attribute("class", expectedWordTypes));
    }

    private void setupData()
    {
        data = new Instances("i2b2_dump", attributes, 0);
        data.setClassIndex(attributes.size() - 1);
    }

    private void setupTokenizer()
    {
        try
        {
            String s = URLDecoder.decode(getClass().getResource("en-token.bin").getFile().toString(), "UTF-8");
            FileInputStream is = new FileInputStream(s);
            TokenizerModel model = new TokenizerModel(is);
            tokenizer = new TokenizerME(model);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private Instance getWekaData(Word word)
    {
        Instance inst = new Instance(attributes.size());
        inst.setDataset(data);
        int i = 0;
        inst.setValue(i++, word.getCapitalLetters().doubleValue());
        inst.setValue(i++, word.getSmallLetters().doubleValue());
        inst.setValue(i++, word.getCapSmallRatio());
        inst.setValue(i++, word.getLength());
        inst.setValue(i++, word.getFrequency().doubleValue());
        inst.setValue(i++, word.getWordPosition().doubleValue());
        inst.setValue(i++, word.getTextFormat().name());
        inst.setValue(i++, word.getNumberFormat().name());
        inst.setValue(i++, word.getExpectedWordType().name());
        for (int j = 0; j < windowSize; j++)
        {
            inst.setValue(i++, word.getBackwardWordType(j).name());
        }
        for (int j = 0; j < windowSize; j++)
        {
            inst.setValue(i++, word.getForwardWordType(j).name());
        }
        Integer pos;
        for (int j = 0; j < triggerWords.length; j++)
        {
            pos = word.positionTriggerWord(triggerWords[j]);
            inst.setValue(i++, pos);
        }
        for (int j = 0; j < bigrams.length; j++)
        {
            pos = word.positionTriggerNGram(bigrams[j]);
            inst.setValue(i++, pos);
        }
        for (int j = 0; j < headers.length; j++)
        {
            if (headers[j].equalsIgnoreCase(word.getLastHeader()))
            {
                pos = word.getLastHeaderPosition();
                inst.setValue(i++, pos);
            }
            else
            {
                inst.setValue(i++, Integer.MIN_VALUE);
            }
        }
        inst.setValue(i++, word.getWordType().name());
        return inst;
    }
    List<Instance> generateInstances(String xmlFile)
    {
        List<Instance> instances = new ArrayList<Instance>();
        List<Word> words = generateWords(xmlFile);
        for (Word word : words)
        {
            instances.add(getWekaData(word));
        }
        return instances;
    }

    List<Instance> generateInstances(List<String> tokens)
    {
        List<Instance> instances = new ArrayList<Instance>();
        List<Word> words = generateWords(tokens);
        /*for (Word word : words)
        {
            instances.add(getWekaData(word));
        }*/
        return instances;
    }

    List<Word> generateWords(List<String> tokens)
    {
        FixedSizeList<Word> lastNWords = new FixedSizeList<Word>(windowSize);
        FixedSizeList<Word> nextNWords = new FixedSizeList<Word>(windowSize);
        List<Word> words = new ArrayList<Word>();
        int wordCount = 0;
        for (String token : tokens)
        {
            Word currWord = new Word(token, wordCount++);
            currWord.setWordType(WordType.NA);
            frequencyCount.put(currWord.getWord(), (frequencyCount.containsKey(currWord.getWord()) ? frequencyCount.get(currWord.getWord()) : 0) + 1);
            words.add(currWord);

        }
        lastNWords.add(null);
        lastNWords.add(null);
        lastNWords.add(null);
        nextNWords.add(words.size() > 2 ? words.get(1) : null);
        nextNWords.add(words.size() > 3 ? words.get(2) : null);
        nextNWords.add(words.size() > 4 ? words.get(3) : null);
        List<Instance> instances = new ArrayList<Instance>();
        String lastHeader = "";
        Integer lastPos = Integer.MIN_VALUE;
        HeaderIdentifier headerIdentifier = new HeaderIdentifier(this.headers, words);
        for (int i = 0; i < wordCount; i++)
        {
            words.get(i).setForwardWindow((FixedSizeList<Word>) nextNWords.clone());
            words.get(i).setBackwardWindow((FixedSizeList<Word>) lastNWords.clone());
            words.get(i).setFrequency(frequencyCount.get(words.get(i).getWord()));
            if (words.get(i).getWord().equalsIgnoreCase(":"))
            {
                String header = headerIdentifier.getHeader(i);
                if (!StringUtils.isBlank(header))
                {
                    lastHeader = header;
                    lastPos = i;
                }
            }
            words.get(i).setLastHeader(lastHeader);
            words.get(i).setLastHeaderPosition(i - lastPos);
            lastNWords.add(words.get(i));
            nextNWords.add(wordCount > i + 4 ? words.get(i + 4) : null);
            // instances.add(getWekaData(words.get(i)));
        }
        return words;
    }

    List<Word> generateWords(String xmlFile)
    {
        List<Word> words = new ArrayList<Word>();
        SAXBuilder builder = new SAXBuilder();
        try
        {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List<Element> list = rootNode.getChildren("RECORD");
            for (int i = 0; i < list.size(); i++)
            {
                words.addAll(parseRecord(list.get(i)));
            }
        }
        catch (IOException io)
        {
            System.out.println(io.getMessage());
        }
        catch (JDOMException jdomex)
        {
            System.out.println(jdomex.getMessage());
        }
        return words;
    }

    public Instances getArffInstance(String xmlFile)
    {
        List<Instance> instances = generateInstances(xmlFile);
        for (Instance instance : instances)
            data.add(instance);
        return data;
    }

    private List<Word> parseRecord(Element record)
    {
        String recordId = record.getAttributeValue("ID");
        Element textNode = record.getChild("TEXT");
        List<Content> contentList = textNode.getContent();
        FixedSizeList<Word> lastNWords = new FixedSizeList<Word>(windowSize);
        FixedSizeList<Word> nextNWords = new FixedSizeList<Word>(windowSize);
        List<Word> words = new ArrayList<Word>();
        int wordCount = 0;
        for (int i = 0; i < contentList.size(); i++)
        {
            if (contentList.get(i) instanceof Text)
            {
                Text content = (Text) contentList.get(i);
                String tokens[] = tokenizer.tokenize(content.getTextNormalize());
                for (String token : tokens)
                {
                    Word currWord = new Word(token, wordCount++);
                    currWord.setWordType(WordType.NON_PHI);
                    frequencyCount.put(currWord.getWord(), (frequencyCount.containsKey(currWord.getWord()) ? frequencyCount.get(currWord.getWord()) : 0) + 1);
                    words.add(currWord);
                }
            }
            else if (contentList.get(i) instanceof Element)
            {
                Element content = (Element) contentList.get(i);
                String tokens[] = tokenizer.tokenize(content.getTextNormalize());
                for (String token : tokens)
                {
                    Word currWord = new Word(token, wordCount++);
                    currWord.setWordType(WordType.valueOf(content.getAttributeValue("TYPE")));
                    frequencyCount.put(currWord.getWord(), (frequencyCount.containsKey(currWord.getWord()) ? frequencyCount.get(currWord.getWord()) : 0) + 1);
                    words.add(currWord);
                }
            }
        }
        lastNWords.add(null);
        lastNWords.add(null);
        lastNWords.add(null);
        nextNWords.add(words.get(1));
        nextNWords.add(words.get(2));
        nextNWords.add(words.get(3));
        List<Instance> instances = new ArrayList<Instance>();
        String lastHeader = "";
        Integer lastPos = Integer.MIN_VALUE;
        HeaderIdentifier headerIdentifier = new HeaderIdentifier(this.headers, words);
        for (int i = 0; i < wordCount; i++)
        {
            words.get(i).setForwardWindow((FixedSizeList<Word>) nextNWords.clone());
            words.get(i).setBackwardWindow((FixedSizeList<Word>) lastNWords.clone());
            words.get(i).setFrequency(frequencyCount.get(words.get(i).getWord()));
            if (words.get(i).getWord().equalsIgnoreCase(":"))
            {
                String header = headerIdentifier.getHeader(i);
                if (!StringUtils.isBlank(header))
                {
                    lastHeader = header;
                    lastPos = i;
                }
            }
            words.get(i).setLastHeader(lastHeader);
            words.get(i).setLastHeaderPosition(i - lastPos);
            lastNWords.add(words.get(i));
            nextNWords.add(wordCount > i + 4 ? words.get(i + 4) : null);
            instances.add(getWekaData(words.get(i)));
        }
        return words;
    }

    public static void main(String[] args) throws Exception
    {
        String triggerWords[] = Constants.TRIGGER_WORDS;
        String bigrams[] = Constants.BIGRAMS;
        String headers[] = Constants.HEADERS;
        ArffBuilder process = new ArffBuilder(3, triggerWords, bigrams, headers);
        ArffSaver saver = new ArffSaver();
        String trainFile = URLDecoder.decode(ArffBuilder.class.getResource("train.xml").getFile().toString(), "UTF-8");
        saver.setInstances(process.getArffInstance(trainFile));
        saver.setFile(new File("train.arff"));
        saver.writeBatch();
        /*
         * process.train(); process.classify("test.xml");
         */
    }
}
