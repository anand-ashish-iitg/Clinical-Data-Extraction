package sample.deidentifier;

import weka.core.FastVector;

public enum WordType {
	NON_PHI, AGE, DATE, DOCTOR, HOSPITAL, ID, LOCATION, PATIENT, PHONE, UNKONWN, NA;
	
	public static FastVector getFastVector(){
		FastVector wordTypeFastVector = new FastVector();
		for(WordType wordType : WordType.values()){
			wordTypeFastVector.addElement(wordType.name());
		}
		return wordTypeFastVector;
	} 
}
