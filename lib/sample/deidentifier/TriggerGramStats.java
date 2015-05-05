package sample.deidentifier;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.tartarus.snowball.ext.PorterStemmer;

public class TriggerGramStats {
	String file;
	Integer windowSize;
	TokenizerME tokenizer;
	PorterStemmer stemmer;

	HashMap<WordType, HashMap<String, Integer>> phraseStats;
	HashMap<WordType, HashMap<String, Integer>> tokenStats;

	public TriggerGramStats(String file, Integer windowSize) {
		this.file = file;
		this.windowSize = windowSize;
		phraseStats = new HashMap<WordType, HashMap<String, Integer>>();
		tokenStats = new HashMap<WordType, HashMap<String, Integer>>();
		stemmer = new PorterStemmer();
		for (WordType type : WordType.values()) {
			phraseStats.put(type, new HashMap<String, Integer>());
			tokenStats.put(type, new HashMap<String, Integer>());
		}
		setupTokenizer();
	}

	private void setupTokenizer() {
		try {
			FileInputStream is = new FileInputStream("en-token.bin");
			TokenizerModel model = new TokenizerModel(is);
			tokenizer = new TokenizerME(model);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateStats() {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = (Document) builder.build(file);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("RECORD");
			for (int i = 0; i < list.size(); i++) {
				generateRecordStats(list.get(i));
			}
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}

	private void generateRecordStats(Element record) {
		String recordId = record.getAttributeValue("ID");
		Element textNode = record.getChild("TEXT");
		List<Content> contentList = textNode.getContent();
		FixedSizeList<Word> lastNWords = new FixedSizeList<Word>(windowSize);
		FixedSizeList<Word> nextNWords = new FixedSizeList<Word>(windowSize);
		List<Word> words = new ArrayList<Word>();
		List<Pair<Integer, Integer>> phrases = new ArrayList<Pair<Integer, Integer>>();
		int wordCount = 0;
		for (int i = 0; i < contentList.size(); i++) {
			if (contentList.get(i) instanceof Text) {
				Text content = (Text) contentList.get(i);
				String tokens[] = tokenizer
						.tokenize(content.getTextNormalize());
				for (String token : tokens) {
					Word currWord = new Word(token.toLowerCase(), wordCount++);
					currWord.setWordType(WordType.NON_PHI);
					words.add(currWord);
				}
			} else if (contentList.get(i) instanceof Element) {
				Element content = (Element) contentList.get(i);
				String tokens[] = tokenizer
						.tokenize(content.getTextNormalize());
				phrases.add(Pair.of(wordCount, wordCount + tokens.length - 1));
				for (String token : tokens) {
					Word currWord = new Word(token.toLowerCase(), wordCount++);
					currWord.setWordType(WordType.valueOf(content
							.getAttributeValue("TYPE")));
					words.add(currWord);
				}
			}
		}
		for (int i = 0; i < wordCount; i++) {
			words.get(i).setForwardWindow(
					(FixedSizeList<Word>) nextNWords.clone());
			words.get(i).setBackwardWindow(
					(FixedSizeList<Word>) lastNWords.clone());
			lastNWords.add(words.get(i));
			nextNWords.add(wordCount > i + 4 ? words.get(i + 4) : null);
		}
		for (Pair pair : phrases) {
			WordType wordType = words.get((int) pair.getLeft()).getWordType();
			/*
			 * for (Word word : words.get((int) pair.getLeft())
			 * .getBackwardWindow()) { if(word == null) continue;
			 * //stemmer.setCurrent(word.getWord());stemmer.stem(); String wd =
			 * word.getWord(); phraseStats .get(wordType) .put(wd,
			 * (phraseStats.get(wordType).containsKey(wd) ? phraseStats
			 * .get(wordType).get(wd) : 0) + 1); } for (Word word :
			 * words.get((int) pair.getRight()) .getForwardWindow()) { if(word
			 * == null) continue; //stemmer.setCurrent();stemmer.stem(); String
			 * wd = word.getWord(); phraseStats .get(wordType) .put(wd,
			 * (phraseStats.get(wordType).containsKey(wd) ? phraseStats
			 * .get(wordType).get(wd) : 0) + 1); }
			 */
			for (int i = (int) pair.getLeft(); i <= (int) pair.getRight(); i++) {
				String backWord, frontWord;
				List<Word> backwords = words.get(i).getBackwardWindow();
				for (int j = 0; j < backwords.size() - 3; j++) {
					backWord = "";
					for (int k = 0; k < 3; k++) {
						backWord = backWord + " "
								+ backwords.get(j + k).getWord();
					}
					backWord = backWord.trim();
					if (!StringUtils.isBlank(backWord))
						tokenStats
								.get(wordType)
								.put(backWord,
										(tokenStats.get(wordType).containsKey(
												backWord) ? tokenStats.get(
												wordType).get(backWord) : 0) + 1);
				}

				List<Word> frontwords = words.get(i).getBackwardWindow();
				for (int j = 0; j < frontwords.size() - 2; j++) {
					frontWord = "";
					for (int k = 0; k < 3; k++) {
						frontWord = frontWord + " "
								+ frontwords.get(j + k).getWord();
					}
					frontWord = frontWord.trim();
					if (!StringUtils.isBlank(frontWord))
						tokenStats.get(wordType).put(
								frontWord,
								(tokenStats.get(wordType)
										.containsKey(frontWord) ? tokenStats
										.get(wordType).get(frontWord) : 0) + 1);
				}
			}
		}
	}

	private void cleanPhraseStats() {
		cleanStats(phraseStats);
	}

	private void printPhraseStats() {
		printStats(phraseStats);
	}

	private void cleanTokenStats() {
		cleanStats(tokenStats);
	}

	private void printTokenStats() {
		printStats(tokenStats);
	}

	private void cleanStats(HashMap<WordType, HashMap<String, Integer>> stats) {
		for (Map.Entry<WordType, HashMap<String, Integer>> entry : stats
				.entrySet()) {
			for (Iterator<Map.Entry<String, Integer>> it = entry.getValue()
					.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, Integer> e = it.next();
				if (e.getValue() < 10
						|| !StringUtils.containsAny(e.getKey(),
								"abcdefghijklmnopqrstuvwxyz")) {
					it.remove();
				}
			}
		}
	}

	private void printStats(HashMap<WordType, HashMap<String, Integer>> stats) {
		for (Map.Entry<WordType, HashMap<String, Integer>> entry : stats
				.entrySet()) {
			System.out.println(entry.getKey() + " : ");
			TreeMap<String, Integer> sortedMap = SortByValue(entry.getValue());
			System.out.println(sortedMap);
		}
	}

	public static TreeMap<String, Integer> SortByValue(
			HashMap<String, Integer> map) {
		ValueComparator vc = new ValueComparator(map);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(vc);
		sortedMap.putAll(map);
		return sortedMap;
	}

	public static void main(String[] args) throws Exception {
		TriggerGramStats stats = new TriggerGramStats("train.xml", 3);
		stats.generateStats();
		stats.cleanTokenStats();
		stats.printTokenStats();
	}
}
