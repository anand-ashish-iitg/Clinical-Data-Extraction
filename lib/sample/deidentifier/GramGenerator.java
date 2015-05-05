package sample.deidentifier;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class GramGenerator {
	private int ngram;
	
	public GramGenerator(int n){
		this.ngram = n;
	}
	
	public List<String> getNGram(List<Word> words){
		List<String> ngrams = new  ArrayList<String>();
		for (int j = 0; j < words.size() - (ngram-1); j++) {
			String word = "";
			for (int k = 0; k < ngram; k++) {
				if(words.get(j+k) != null)
					word = word + " " + words.get(j + k).getWord();
			}
			word = word.trim();
			if (!StringUtils.isBlank(word))
				ngrams.add(word);
		}
		return ngrams;
	}
}
