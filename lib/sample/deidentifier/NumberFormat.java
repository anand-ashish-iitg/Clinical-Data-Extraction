package sample.deidentifier;

import weka.core.FastVector;

public enum NumberFormat {
	AGE, DATE, ID, PHONE, ROMAN, ARABIC, UNKNOWN;

	private WordType wordType;

	static {
		AGE.wordType = WordType.AGE;
		DATE.wordType = WordType.DATE;
		ID.wordType = WordType.ID;
		PHONE.wordType = WordType.PHONE;
		ROMAN.wordType = WordType.UNKONWN;
		ARABIC.wordType = WordType.UNKONWN;
		UNKNOWN.wordType = WordType.UNKONWN;
	}

	public WordType getWordType() {
		return this.wordType;
	}
	
	public static FastVector getFastVector(){
		FastVector numberFormatFastVector = new FastVector();
		for(NumberFormat numberFormat : NumberFormat.values()){
			numberFormatFastVector.addElement(numberFormat.name());
		}
		return numberFormatFastVector;
	} 
}
