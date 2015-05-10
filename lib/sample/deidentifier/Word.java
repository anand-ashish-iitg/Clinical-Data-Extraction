package sample.deidentifier;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Word {
	private TextFormat textFormat;
	private NumberFormat numberFormat;
	private Integer capitalLetters;
	private Integer smallLetters;
	private Integer frequency;
	private String sentence;
	private Integer wordPosition;
	private Boolean sentenceBeginning;
	private Boolean phrase;
	private List<String> punctuationMarks;
	private FixedSizeList<Word> forwardWindow;
	private FixedSizeList<Word> backwardWindow;
	private String lastHeader;
	private Integer lastHeaderPosition;
	private WordType expectedWordType;
	private WordType wordType;
	private String word;

	public Word(String word, Integer wordPosition) {
		super();
		this.word = word;
		this.wordPosition = wordPosition;
		this.sentenceBeginning = false;
		this.lastHeader = "";
		setProperties();
		setExpectedWordType();
	}

	public Word(String word, String sentence, Integer wordPosition) {
		super();
		this.word = word;
		this.sentence = sentence;
		this.wordPosition = wordPosition;
		this.sentenceBeginning = false;
		setProperties();
		setExpectedWordType();
	}

	public Word(String word, String sentence, Integer wordPosition,
			WordType expectedWordType) {
		super();
		this.word = word;
		this.sentence = sentence;
		this.wordPosition = wordPosition;
		this.expectedWordType = expectedWordType;
		this.sentenceBeginning = false;
		setProperties();
	}

	public Word(String word, String sentence, Integer wordPosition,
			WordType expectedWordType, Boolean sentenceBeginning) {
		super();
		this.word = word;
		this.sentence = sentence;
		this.wordPosition = wordPosition;
		this.expectedWordType = expectedWordType;
		this.sentenceBeginning = sentenceBeginning;
		setProperties();
	}

	private void setProperties() {
		setTextFormat();
		setNumberFormat();
		setCapitalLettersCount();
		setSmallLettersCount();
		setPhraseOrNot();
	}

	private void setTextFormat() {
		if (StringUtils.isAllLowerCase(word)) {
			textFormat = TextFormat.LOWER_CASE;
		} else if (StringUtils.isAllUpperCase(word)) {
			textFormat = TextFormat.UPPER_CASE;
		} else if (StringUtils.capitalize(word).equals(word)) {
			textFormat = TextFormat.TITLE_CASE;
		} else {
			textFormat = TextFormat.UNKNOWN;
		}

	}

	private void setNumberFormat() {
		if (RegexMatcher.isDate(word)) {
			numberFormat = NumberFormat.DATE;
		} else if (RegexMatcher.isPhoneNumber(word)) {
			numberFormat = NumberFormat.PHONE;
		} else if (RegexMatcher.isID(word)) {
			numberFormat = NumberFormat.ID;
		} else if (RegexMatcher.isAge(word)) {
			// System.out.println(word);
			numberFormat = NumberFormat.AGE;
		} else if (RegexMatcher.isArabicNumber(word)) {
			numberFormat = NumberFormat.ARABIC;
		} else if (RegexMatcher.isRomanNumber(word)) {
			numberFormat = NumberFormat.ROMAN;
		} else {
			numberFormat = NumberFormat.UNKNOWN;
		}

	}

	public void setWordType(WordType wordType) {
		this.wordType = wordType;
	}

	private void setCapitalLettersCount() {
		capitalLetters = 0;
		for (int i = 0; i < word.length(); i++) {
			if (Character.isUpperCase(word.charAt(i))) {
				capitalLetters++;
			}
		}
	}

	private void setSmallLettersCount() {
		smallLetters = 0;
		for (int i = 0; i < word.length(); i++) {
			if (Character.isLowerCase(word.charAt(i))) {
				smallLetters++;
			}
		}
	}

	private void setPhraseOrNot() {
		phrase = StringUtils.containsWhitespace(word);
	}

	private void setExpectedWordType() {
		expectedWordType = numberFormat.getWordType();
		if (expectedWordType == WordType.UNKONWN) {

		}
	}

	public WordType getWordType() {
		return wordType;
	}

	public TextFormat getTextFormat() {
		return textFormat;
	}

	public void setTextFormat(TextFormat textFormat) {
		this.textFormat = textFormat;
	}

	public NumberFormat getNumberFormat() {
		return numberFormat;
	}

	public void setNumberFormat(NumberFormat numberFormat) {
		this.numberFormat = numberFormat;
	}

	public Integer getCapitalLetters() {
		return capitalLetters;
	}

	public void setCapitalLetters(Integer capitalLetters) {
		this.capitalLetters = capitalLetters;
	}

	public Integer getSmallLetters() {
		return smallLetters;
	}

	public void setSmallLetters(Integer smallLetters) {
		this.smallLetters = smallLetters;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public Integer getWordPosition() {
		return wordPosition;
	}

	public void setWordPosition(Integer wordPosition) {
		this.wordPosition = wordPosition;
	}

	public Boolean getSentenceBeginning() {
		return sentenceBeginning;
	}

	public void setSentenceBeginning(Boolean sentenceBeginning) {
		this.sentenceBeginning = sentenceBeginning;
	}

	public Boolean getPhrase() {
		return phrase;
	}

	public void setPhrase(Boolean phrase) {
		this.phrase = phrase;
	}

	public List<String> getPunctuationMarks() {
		return punctuationMarks;
	}

	public void setPunctuationMarks(List<String> punctuationMarks) {
		this.punctuationMarks = punctuationMarks;
	}

	public FixedSizeList<Word> getForwardWindow() {
		return forwardWindow;
	}

	public void setForwardWindow(FixedSizeList<Word> forwardWindow) {
		this.forwardWindow = forwardWindow;
	}

	public FixedSizeList<Word> getBackwardWindow() {
		return backwardWindow;
	}

	public void setBackwardWindow(FixedSizeList<Word> backwardWindow) {
		this.backwardWindow = backwardWindow;
	}

	public WordType getExpectedWordType() {
		return expectedWordType;
	}

	public void setExpectedWordType(WordType expectedWordType) {
		this.expectedWordType = expectedWordType;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	public String getLastHeader() {
		return lastHeader;
	}

	public void setLastHeader(String lastHeader) {
		this.lastHeader = lastHeader;
	}

	public Integer getLastHeaderPosition() {
		return lastHeaderPosition;
	}

	public void setLastHeaderPosition(Integer lastHeaderPosition) {
		this.lastHeaderPosition = lastHeaderPosition;
	}


	public Double getCapSmallRatio() {
		if (smallLetters != 0)
			return capitalLetters / smallLetters.doubleValue();
		else
			return Double.MAX_VALUE;
	}

	public Integer getLength() {
		return word.length();
	}

	public String getBackwardWord(int j) {
		if (j < backwardWindow.size() && backwardWindow.get(j) != null) {
			return backwardWindow.get(j).getWord();
		}
		return "";
	}

	public WordType getBackwardWordType(int j) {
		if (j < backwardWindow.size() && backwardWindow.get(j) != null) {
			return backwardWindow.get(j).getExpectedWordType();
		}
		return WordType.NA;
	}

	public String getForwardWord(int j) {
		if (j < forwardWindow.size() && forwardWindow.get(j) != null) {
			return forwardWindow.get(j).getWord();
		}
		return "";
	}

	public WordType getForwardWordType(int j) {
		if (j < forwardWindow.size() && forwardWindow.get(j) != null) {
			return forwardWindow.get(j).getExpectedWordType();
		}
		return WordType.NA;
	}

	public Integer positionTriggerWord(String triggerWord) {
		for (int i = 0; i < backwardWindow.size(); i++) {
			if (backwardWindow.get(i) != null) {
				if (backwardWindow.get(i).getWord()
						.equalsIgnoreCase(triggerWord)) {
					return -1 * (i + 1);
				}
			}
		}

		for (int i = 0; i < forwardWindow.size(); i++) {
			if (forwardWindow.get(i) != null) {
				if (forwardWindow.get(i).getWord()
						.equalsIgnoreCase(triggerWord)) {
					return (i + 1);
				}
			}
		}
		return Integer.MIN_VALUE;
	}

	public Integer positionTriggerNGram(String nGram) {
		GramGenerator gen = new GramGenerator(2);
		List<String> backNgrams = gen.getNGram(backwardWindow);
		for (int i = 0; i < backNgrams.size(); i++) {
			if (backNgrams.get(i).equalsIgnoreCase(nGram)) {
				return -1 * (i + 1);
			}
		}

		List<String> frontNgrams = gen.getNGram(forwardWindow);
		for (int i = 0; i < frontNgrams.size(); i++) {
			if (frontNgrams.get(i).equalsIgnoreCase(nGram)) {
				return (i + 1);
			}
		}
		return Integer.MIN_VALUE;
	}
}
