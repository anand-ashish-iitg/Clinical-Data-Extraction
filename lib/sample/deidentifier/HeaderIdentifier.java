package sample.deidentifier;

import java.util.List;

public class HeaderIdentifier {
	String[] headers;
	List<Word> words;
	
	public HeaderIdentifier(String[] headers, List<Word> words){
		this.headers = headers;
		this.words = words;
	}
	
	public String getHeader(int pos){
		String header = "";
		for(int i=pos-1;i>=Math.max(0,pos- 8);i--){
			if(words.get(i).getWord().equalsIgnoreCase(".")){
				break;
			}
			header = words.get(i).getWord().toLowerCase()+" "+header;
			for(int j=0;j<headers.length;j++){
				if(headers[j].equalsIgnoreCase(header.trim())){
					return headers[j];
				}
			}
		}
		return "";
	}
}
