package sample.deidentifier;

import weka.core.FastVector;

public enum TextFormat {
	LOWER_CASE, UPPER_CASE, TITLE_CASE, UNKNOWN;
	
	public static FastVector getFastVector(){
		FastVector textFormatFastVector = new FastVector();
		for(TextFormat textFormat : TextFormat.values()){
			textFormatFastVector.addElement(textFormat.name());
		}
		return textFormatFastVector;
	} 
}
